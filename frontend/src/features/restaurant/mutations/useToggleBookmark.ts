import { useMutation, useQueryClient } from '@tanstack/react-query';
import api from '@/lib/axios';

interface ToggleArgs {
  restaurantId: number;
  isBookmarked: boolean;
}

export const useToggleBookmark = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: async ({ restaurantId, isBookmarked }: ToggleArgs) => {
      if (isBookmarked) {
        await api.delete(`/bookmarks/${restaurantId}`);
      } else {
        await api.post(`/bookmarks/${restaurantId}`);
      }
    },
    onSuccess: (_, variables) => {
      queryClient.invalidateQueries({
        queryKey: ['restaurant-detail', variables.restaurantId],
      });
      queryClient.invalidateQueries({ queryKey: ['recommended-restaurants'] });
    },
  });
};
