import { useMutation } from '@tanstack/react-query';
import api from '@/lib/axios';

interface AddExpenseRequest {
  expectSavingGoalId: number;
  restaurantId?: number;
  restaurantName: string;
  menuName: string;
  expense: number;
  memo: string;
  expenseDate: string;
  rating: number;
}

export const useAddExpense = () => {
  return useMutation({
    mutationFn: (payload: AddExpenseRequest) =>
      api.post('/api/expenses', payload),
  });
};