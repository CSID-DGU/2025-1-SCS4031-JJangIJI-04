import { useQuery } from '@tanstack/react-query';
import { getCommunityPosts } from '@/features/community/api/communityApi';

export const useCommunity = () => {
  const { data = [], isLoading } = useQuery({
    queryKey: ['community-posts'],
    queryFn: getCommunityPosts,
  });
  return {
    posts: data,
    isLoading,
  };
};
