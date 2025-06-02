import { useMutation, useQueryClient } from '@tanstack/react-query';
import api from '@/lib/axios';

export const useUpdateNickname = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: async (nickname: string) => {
      await api.patch('/nickname', { nickname });
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['userInfo'] });
    },
  });
};
