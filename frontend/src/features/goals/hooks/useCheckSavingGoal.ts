import { useEffect } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import { useRemainingBudget } from '@/features/goals/api/useRemainingBudget';
import { AxiosError } from 'axios';
import { startOfDay, isSunday, isAfter, parseISO } from 'date-fns';

export const useCheckSavingGoal = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const { error, data } = useRemainingBudget();

  useEffect(() => {
    const now = new Date();
    const today = startOfDay(now);
    
    // 일요일 00시가 지났고, endDate가 지났다면 새로운 목표 설정 필요
    if (data?.endDate) {
      const endDate = parseISO(data.endDate);
      const isSundayMidnight = isSunday(today) && now.getHours() === 0;
      
      if (isSundayMidnight || isAfter(today, endDate)) {
        if (location.pathname !== '/weeklygoal') {
          navigate('/weeklygoal', { replace: true });
        }
        return;
      }
    }

    if (!error) return;

    const axiosError = error as AxiosError<{ exceptionCode: string }>;
    const isGoalMissing = 
      axiosError.response?.data?.exceptionCode === 'EXPENSE_SAVING_GOAL_NOT_FOUND';

    if (isGoalMissing && location.pathname !== '/weeklygoal') {
      navigate('/weeklygoal', { replace: true });
    }
  }, [error, data, location.pathname, navigate]);
};