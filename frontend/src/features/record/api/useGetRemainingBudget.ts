import { useQuery } from '@tanstack/react-query';
import api from '@/lib/axios';
import { format } from 'date-fns';

export const useGetRemainingBudget = () => {
  const today = format(new Date(), 'yyyy-MM-dd');

  return useQuery({
    queryKey: ['remaining-budget', today],
    queryFn: async () => {
      const res = await api.get(`/api/saving-goals?date=${today}/remaining`);
      return res.data.remainingBudget as number;
    },
  });
};