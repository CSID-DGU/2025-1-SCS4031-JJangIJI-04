import { useEffect } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import { useRemainingBudget } from '@/features/goals/api/useRemainingBudget';
import { AxiosError } from 'axios';
import { startOfWeek, isAfter } from 'date-fns';

export const useCheckSavingGoal = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const { error } = useRemainingBudget();

  useEffect(() => {
    // 현재 시간이 이번 주의 시작점(일요일 0시)보다 이후인지 체크
    const now = new Date();
    const weekStart = startOfWeek(now, { weekStartsOn: 0 });
    const isNewWeek = isAfter(now, weekStart);

    if (isNewWeek && location.pathname !== '/weeklygoal') {
      navigate('/weeklygoal', { replace: true });
      return;
    }

    // 절약 목표 없음 에러 체크
    if (!error) return;

    const axiosError = error as AxiosError<{ exceptionCode: string }>;
    const isGoalMissing = 
      axiosError.response?.data?.exceptionCode === 'EXPENSE_SAVING_GOAL_NOT_FOUND';

    if (isGoalMissing && location.pathname !== '/weeklygoal') {
      navigate('/weeklygoal', { replace: true });
    }
  }, [error, location.pathname, navigate]);
};