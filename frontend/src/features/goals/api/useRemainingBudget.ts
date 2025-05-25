import { useQuery } from '@tanstack/react-query';
import { format } from 'date-fns';
import api from '@/lib/axios';

interface RemainingBudgetResponse {
  id: number;
  budget: number;
  remainingBudget: number;
  remainingPercentage: number;
  message: string;
}

export const useRemainingBudget = () => {
  const today = format(new Date(), 'yyyy-MM-dd');

  return useQuery<RemainingBudgetResponse>({
    queryKey: ['remainingBudget', today],
    queryFn: async () => {
      try {
        const res = await api.get('/saving-goals/remaining', {
          params: { date: today }
        });
        return res.data;
      } catch (error) {
        console.error('가용 예산 조회 실패:', error);
        return { 
          id: 0,
          budget: 100000,
          remainingBudget: 100000,
          remainingPercentage: 100,
          message: '테스트용 기본값'
        };
      }
    },
  });
}; 