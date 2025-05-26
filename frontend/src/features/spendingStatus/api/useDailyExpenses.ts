import { useQuery } from '@tanstack/react-query';
import api from '@/lib/axios';

export interface SavingGoalStatus {
  budget: number;
  remainingBudget: number;
  remainingPercentage: number;
  message: string;
}

export interface ExpenseRecord {
  id: number;
  storeName: string;
  category: string;
  expense: number;
  memo: string;
  reactions: Record<number, number>;
}

export interface DailyExpensesResponse {
  savingGoalStatus: SavingGoalStatus;
  expenses: ExpenseRecord[];
}

export const useDailyExpenses = (userId: number | null, date: string) => {
  return useQuery({
    queryKey: ['dailyExpenses', userId, date],
    queryFn: async () => {
      if (!userId) throw new Error('User ID is required');
      const res = await api.get(`/users/${userId}/expenses`, {
        params: { date }
      });
      return res.data;
    },
    enabled: !!userId,
  });
};