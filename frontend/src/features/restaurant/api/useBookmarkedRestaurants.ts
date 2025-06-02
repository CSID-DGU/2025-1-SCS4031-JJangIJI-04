import { useQuery } from '@tanstack/react-query';
import api from '@/lib/axios';

interface BookmarkedRestaurant {
  id: number;
  menuAverage: number;
  imgUrl: string;
  streetAddress: string;
  openingHour: string;
  category: string;
  bookmarked: boolean;
}

export const useBookmarkedRestaurants = () => {
  return useQuery<BookmarkedRestaurant[]>({
    queryKey: ['bookmarked-restaurants'],
    queryFn: async () => {
      const res = await api.get('/bookmarks/restaurants');
      return res.data;
    },
    staleTime: 1000 * 60 * 3, // 3분 캐싱
  });
};
