export type ExpenseStatusType = 'GOOD' | 'NOT_BAD' | 'BAD';

export interface DailyExpenseStatus {
  date: string;
  totalExpense: number;
  status: ExpenseStatusType;
}
