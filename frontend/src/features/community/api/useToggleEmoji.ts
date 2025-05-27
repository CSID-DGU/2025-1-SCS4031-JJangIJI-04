import { useMutation } from '@tanstack/react-query';
import api from '@/lib/axios';
import { ToggleEmojiRequest } from '@/features/community/types/emoji';

export const useToggleEmoji = () => {
  return useMutation<void, Error, { body: ToggleEmojiRequest; isSelected: boolean }>({
    mutationFn: async ({ body, isSelected }) => {
      if (isSelected) {

        await api.delete('/expenses/emojis', { data: body });
      } else {
        await api.post('/expenses/emojis', body);
      }
    },
  });
};