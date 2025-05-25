import { useQuery } from '@tanstack/react-query';
import { requestGetRemainingBudget } from '@/features/goals/api/requestGetRemainingBudget';

export const useGetRemainingBudget = () => {
  return useQuery({
    queryKey: ['remaining-budget'],
    queryFn: requestGetRemainingBudget,
    retry: false,
  });
};