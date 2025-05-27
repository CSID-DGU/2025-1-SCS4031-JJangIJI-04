import { useMutation } from '@tanstack/react-query';
import api from '@/lib/axios';
import { DeleteEmojiRequest } from '@/features/community/types/emoji';

export const useDeleteEmoji = () => {
  return useMutation<void, Error, DeleteEmojiRequest>({
    mutationFn: async (body) => {
      await api.delete('/expenses/emojis', { data: body });
    },
  });
};