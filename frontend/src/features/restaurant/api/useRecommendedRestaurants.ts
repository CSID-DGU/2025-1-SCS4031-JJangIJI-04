import { useQuery } from '@tanstack/react-query';
import api from '@/lib/axios';

export interface RecommendedRestaurant {
  id: number;
  menuAverage: number;
  imgUrl: string;
  streetAddress: string;
  openingHour: string;
  category: string;
  bookmarked: boolean;
}

export const useRecommendedRestaurants = () => {
  return useQuery<RecommendedRestaurant[]>({
    queryKey: ['recommendedRestaurants'],
    queryFn: async () => {
      const res = await api.get('/recommendation/restaurants');
      return res.data;
    },
  });
};
