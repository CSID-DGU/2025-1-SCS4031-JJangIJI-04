import { useQuery } from '@tanstack/react-query';
import api from '@/lib/axios';
import { format } from 'date-fns';

export const useRemainingBudget = () => {
  const today = format(new Date(), 'yyyy-MM-dd');
  
  return useQuery({
    queryKey: ['remainingBudget', today],
    queryFn: async () => {
      const res = await api.get('/saving-goals/remaining', {
        params: { date: today }
      });
      return res.data.remainingBudget;
    }
  });
}; 