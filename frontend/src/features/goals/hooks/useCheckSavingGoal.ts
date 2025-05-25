import { useEffect } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import { useRemainingBudget } from '@/features/goals/api/useRemainingBudget';
import { AxiosError } from 'axios';
import { startOfWeek, isAfter } from 'date-fns';

export const useCheckSavingGoal = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const { error, data } = useRemainingBudget();

  useEffect(() => {
    // 현재 시간이 이번 주의 시작점(일요일 0시)보다 이후인지 체크
    const now = new Date();
    const weekStart = startOfWeek(now, { weekStartsOn: 0 });
    const isNewWeek = isAfter(now, weekStart);

    // 새로운 주가 시작되었고 weeklygoal 페이지가 아니면 weeklygoal로 이동
    if (isNewWeek && location.pathname !== '/weeklygoal') {
      navigate('/weeklygoal', { replace: true });
      return;
    }

    // 에러가 있고(절약 목표가 없고) weeklygoal 페이지가 아니면 weeklygoal로 이동
    if (error) {
      const axiosError = error as AxiosError<{ exceptionCode: string }>;
      const isGoalMissing = 
        axiosError.response?.data?.exceptionCode === 'EXPENSE_SAVING_GOAL_NOT_FOUND';

      if (isGoalMissing && location.pathname !== '/weeklygoal') {
        navigate('/weeklygoal', { replace: true });
        return;
      }
    }

    // 절약 목표가 있고 weeklygoal 페이지에 있으면 main으로 이동
    if (data?.remainingBudget !== undefined && location.pathname === '/weeklygoal') {
      navigate('/main', { replace: true });
    }
  }, [error, data, location.pathname, navigate]);
};