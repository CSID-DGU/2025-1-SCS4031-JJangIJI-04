import { useInfiniteQuery } from '@tanstack/react-query';
import api from '@/lib/axios';
import { CommunityPost } from '@/features/community/types/community';

interface CommunityResponse {
  content: CommunityPost[];
  last: boolean;
  number: number;
}

export const useCommunityPosts = () => {
  return useInfiniteQuery<CommunityResponse>({
    queryKey: ['community-posts'],
    queryFn: async ({ pageParam = 0 }) => {
      const res = await api.get('/community/expenses', {
        params: {
          page: pageParam,
          size: 10,
        },
      });
      return res.data;
    },
    getNextPageParam: (lastPage) => {
        if (!lastPage || typeof lastPage.last === 'undefined') return undefined;
        return lastPage.last ? undefined : lastPage.number + 1;
      },
    initialPageParam: 0,
  });
};