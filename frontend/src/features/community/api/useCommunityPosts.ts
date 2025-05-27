import api from '@/lib/axios';
import { CommunityPost } from '@/features/community/types/community';

interface CommunityResponse {
  content: CommunityPost[];
  last: boolean;
  number: number;
}

export const fetchCommunityPosts = async (page: number): Promise<CommunityResponse> => {
  const res = await api.get('/community/expenses', {
    params: {
      page,
      size: 10,
    },
  });
  return res.data;
};