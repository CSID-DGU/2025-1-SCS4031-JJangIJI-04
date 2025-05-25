import api from '@/lib/axios';
import { format } from 'date-fns';

export const requestGetRemainingBudget = async (): Promise<number> => {
  const today = format(new Date(), 'yyyy-MM-dd');
  const res = await api.get(`/saving-goals?date=${today}/remaining`);
  return res.data.remainingBudget;
};