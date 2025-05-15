import { useQuery } from '@tanstack/react-query';
import styled from 'styled-components';
import { getCommunityPosts } from '@/features/community/api/communityApi';
import { CommunityCard } from '@/features/community/ui/CommunityCard';

export const CommunityPage = () => {
  const { data = [], isLoading } = useQuery({
    queryKey: ['community-posts'],
    queryFn: getCommunityPosts,
  });

  if (isLoading) return <div>불러오는 중...</div>;

  return (
    <PageWrapper>
      <Title>한끼니티</Title>
      <Divider />
      <PostList>
        {data.map((post) => (
          <CommunityCard key={post.id} post={post} />
        ))}
      </PostList>
    </PageWrapper>
  );
};

const PageWrapper = styled.div`
  /* padding: var(--page-padding); */
`;

const Title = styled.h2`
  font-size: var(--font-size-lg);
  font-weight: 700;
`;

const Divider = styled.hr`
  border: none;
  border-top: 1px solid #ccc;
  margin: 16px 0;
`;

const PostList = styled.div`
  display: flex;
  flex-direction: column;
  gap: 24px;
`;
