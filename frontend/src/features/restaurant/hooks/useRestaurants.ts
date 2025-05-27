import { useState, useEffect, useCallback } from 'react';
import { Restaurant } from '@/features/restaurant/types/restaurant';
import { getRestaurants } from '@/features/restaurant/api/restaurantApi';

export const useRestaurants = () => {
  const [restaurants, setRestaurants] = useState<Restaurant[]>([]);

  useEffect(() => {
    getRestaurants().then(setRestaurants);
  }, []);

  const toggleBookmark = useCallback((index: number) => {
    setRestaurants((prev) =>
      prev.map((restaurant, idx) =>
        idx === index
          ? { ...restaurant, bookmarked: !restaurant.bookmarked }
          : restaurant
      )
    );
  }, []);

  return {
    restaurants,
    toggleBookmark,
  };
};
