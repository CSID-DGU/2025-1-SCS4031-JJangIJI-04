import { useEffect } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import { useRemainingBudget } from '@/features/goals/api/useRemainingBudget';
import { AxiosError } from 'axios';

export const useCheckSavingGoal = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const { error } = useRemainingBudget();

  useEffect(() => {
    if (!error) return;

    const axiosError = error as AxiosError<{ exceptionCode: string }>;
    const isGoalMissing = 
      axiosError.response?.data?.exceptionCode === 'EXPENSE_SAVING_GOAL_NOT_FOUND';

    if (isGoalMissing && location.pathname !== '/weeklygoal') {
      navigate('/weeklygoal', { replace: true });
    }
  }, [error, location.pathname, navigate]);
};