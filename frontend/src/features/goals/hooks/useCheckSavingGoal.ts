import { useEffect, useRef } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import { useRemainingBudget } from '@/features/goals/api/useRemainingBudget';
import { AxiosError } from 'axios';
import { startOfWeek, isAfter, startOfDay } from 'date-fns';

export const useCheckSavingGoal = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const { error, data, isSuccess, isLoading } = useRemainingBudget();
  const redirectAttempted = useRef(false);

  useEffect(() => {
    if (isLoading || redirectAttempted.current) return;
  
    const excludedPaths = ['/oauth/callback/kakao', '/signup', '/landing'];
    const pathname = location.pathname;
    if (excludedPaths.includes(pathname)) return;
  
    const now = new Date();
    const weekStart = startOfDay(startOfWeek(now, { weekStartsOn: 0 }));
    const isNewWeek = isAfter(now, weekStart);
  
    // 주가 바뀌면 목표금액 초기화 되는 거 체크 해봐야 함.
    if (isSuccess && data?.remainingBudget) {
      if (isNewWeek) {
        redirectAttempted.current = true;
        navigate('/weeklygoal', { replace: true });
        return;
      }
    
      if (pathname === '/weeklygoal') {
        redirectAttempted.current = true;
        navigate('/main', { replace: true });
      }
      return;
    }
  
    if (error) {
      const axiosError = error as AxiosError<{ exceptionCode: string }>;
      const isGoalMissing = axiosError.response?.data?.exceptionCode === 'EXPENSE_SAVING_GOAL_NOT_FOUND';
  
      if (isGoalMissing && pathname !== '/weeklygoal' && !excludedPaths.includes(pathname)) {
        redirectAttempted.current = true;
        navigate('/weeklygoal', { replace: true });
      }
    }
  }, [isLoading, isSuccess, error, data, location.pathname, navigate]);
  
  useEffect(() => {
    redirectAttempted.current = false;
  }, [location.pathname]);
};