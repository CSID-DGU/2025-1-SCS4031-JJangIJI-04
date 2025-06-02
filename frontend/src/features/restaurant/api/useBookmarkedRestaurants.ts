import { useQuery } from '@tanstack/react-query';
import api from '@/lib/axios';

interface BookmarkedRestaurantRaw {
  id: number;
  name: string;
  menuAverage: number;
  imgUrl: string;
  streetAddress: string;
  openingHour: string;
  category: string;
  bookmarked: boolean;
}

interface RestaurantItem {
  id: number;
  name: string;
  menuAverage: number;
  imgUrl: string;
  streetAddress: string;
  openingHours: string;
  category: string;
  bookmarked: boolean;
}

export const useBookmarkedRestaurants = () => {
  return useQuery<RestaurantItem[]>({
    queryKey: ['bookmarked-restaurants'],
    queryFn: async () => {
      const res = await api.get<BookmarkedRestaurantRaw[]>(
        '/bookmarks/restaurants'
      );

      return res.data.map((r) => ({
        ...r,
        openingHours: r.openingHour ?? '정보 없음',
      }));
    },
    staleTime: 1000 * 60 * 3,
  });
};
