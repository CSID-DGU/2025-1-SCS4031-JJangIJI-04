import { useMutation } from '@tanstack/react-query';
import { AxiosError } from 'axios';
import api from '@/lib/axios';
import {
  ToggleEmojiRequest,
  EmojiApiErrorResponse,
} from '@/features/community/types/emoji';

export const useToggleEmoji = () => {
  return useMutation<
    void,
    AxiosError<EmojiApiErrorResponse>,
    { body: ToggleEmojiRequest; isSelected: boolean }
  >({
    mutationFn: async ({ body, isSelected }) => {
      try {
        if (isSelected) {
          await api.delete('/expenses/emojis', { data: body });
        } else {
          await api.post('/expenses/emojis', body);
        }
      } catch (error: any) {
        const axiosError = error as AxiosError<EmojiApiErrorResponse>;
        const code = axiosError.response?.data?.exceptionCode;

        if (
          (!isSelected && code === 'EMOJI_ALREADY_EXIST') ||
          (isSelected && code === 'EMOJI_NOT_FOUND')
        ) {
          return;
        }

        throw error;
      }
    },
  });
};
