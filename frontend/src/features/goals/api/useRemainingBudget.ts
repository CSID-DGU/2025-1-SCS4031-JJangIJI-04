import { useQuery } from '@tanstack/react-query';
import { format } from 'date-fns';
import api from '@/lib/axios';

export const useRemainingBudget = () => {
  const today = format(new Date(), 'yyyy-MM-dd');

  return useQuery({
    queryKey: ['remainingBudget', today],
    queryFn: async () => {
      try {
        const res = await api.get('/saving-goals/remaining', {
          params: { date: today }
        });
        return res.data.remainingBudget;
      } catch (error) {
        console.error('가용 예산 조회 실패:', error);
        return 100000; // 로컬 테스트용 기본값
      }
    },
  });
}; 