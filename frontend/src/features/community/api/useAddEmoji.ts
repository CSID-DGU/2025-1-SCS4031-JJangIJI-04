import { useMutation } from '@tanstack/react-query';
import api from '@/lib/axios';
import { AddEmojiRequest, AddEmojiResponse } from '@/features/community/types/emoji';

export const useAddEmoji = () => {
  return useMutation<AddEmojiResponse, Error, AddEmojiRequest>({
    mutationFn: async (body) => {
      const res = await api.post('/expenses/emojis', body);
      return res.data;
    },
  });
};
