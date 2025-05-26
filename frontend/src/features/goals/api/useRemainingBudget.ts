import { useQuery } from '@tanstack/react-query';
import { format } from 'date-fns';
import api from '@/lib/axios';

interface RemainingBudgetResponse {
  remainingBudget: number;
  startDate?: string;
  endDate?: string;
}

export const useRemainingBudget = () => {
  const today = format(new Date(), 'yyyy-MM-dd');

  return useQuery<RemainingBudgetResponse>({
    queryKey: ['remainingBudget', today],
    queryFn: async () => {
      try {
        const res = await api.get('/saving-goals/remaining', {
          params: { date: today },
        });
        console.log('절약 목표 조회 응답:', res.data);
        return res.data;
      } catch (error) {
        console.error('가용 예산 조회 실패:', error);
        throw error;
      }
    },
    refetchOnMount: true,
    staleTime: 0,
  });
};