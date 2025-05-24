import api from '@/lib/axios';

export interface SavingGoalRequest {
  budget: number;
  startDate: string;
  endDate: string;
}

export const requestAddSavingGoal = async (data: SavingGoalRequest) => {
  await api.post(`/users/saving-goals`, data);
};
