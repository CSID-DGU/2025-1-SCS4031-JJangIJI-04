import { useMutation } from '@tanstack/react-query';
import api from '@/lib/axios';
import { ToggleEmojiRequest } from '@/features/community/types/emoji';

export const useToggleEmoji = () => {
  return useMutation<void, Error, ToggleEmojiRequest>({
    mutationFn: async (body) => {
      await api.post('/expenses/emojis', body);
    },
  });
};