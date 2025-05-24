import { useMutation } from '@tanstack/react-query';
import api from '@/lib/axios';

interface AddExpenseRequest {
  restaurantName: string;
  menuName: string;
  expense: number;
  memo: string;
  expenseDate: string;
  rating: number;
  restaurantId?: number;
  expectSavingGoalId?: number;
}

export const useAddExpense = () => {
  return useMutation({
    mutationFn: async (data: AddExpenseRequest) => {
      const res = await api.post('/expenses', data);
      return res.data;
    },
  });
};