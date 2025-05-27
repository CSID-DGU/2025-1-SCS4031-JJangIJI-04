import { useQuery } from '@tanstack/react-query';
import styled from 'styled-components';
import { getCommunityPosts } from '@/features/community/api/communityApi';
import { CommunityCard } from '@/features/community/ui/CommunityCard';
import FileIcon from '@/assets/icons/file.svg?react';

export const CommunityPage = () => {
  const { data = [], isLoading } = useQuery({
    queryKey: ['community-posts'],
    queryFn: getCommunityPosts,
  });

  if (isLoading) return <LoadingWrapper>불러오는 중...</LoadingWrapper>;

  const hasPosts = data.length > 0;

  return (
    <Container>
      <Header>
        <Title>한끼니티</Title>
        <Subtitle>유저들의 지출 후기와 절약 노하우를 확인해 보세요</Subtitle>
        <Divider />
      </Header>

      {hasPosts ? (
        <PostList>
          {data.map((post) => (
            <CommunityCard key={post.expenseId} post={post} />
          ))}
        </PostList>
      ) : (
        <NoDataBlock>
          <FileIconWrapper>
            <FileIcon />
          </FileIconWrapper>
          <NoDataText>작성된 커뮤니티 글이 없어요</NoDataText>
        </NoDataBlock>
      )}
    </Container>
  );
};

const Container = styled.div`
  background-color: #fff;
  min-height: 100vh;
  width: 100%;
  padding: var(--page-padding);
  padding-top: var(--safe-area-top);
  box-sizing: border-box;
`;

const Header = styled.div`
  margin: 20px 5px;
  text-align: left;
`;

const Title = styled.h1`
  font-size: var(--font-size-lg);
  font-weight: 700;
  color: #202632;
`;

const Subtitle = styled.p`
  font-size: var(--font-size-2xs);
  color: #808080;
  font-weight: 700;
  line-height: 1.5;
  margin-top: 2px;
`;

const Divider = styled.hr`
  border: none;
  border-top: 1px solid #ccc;
  margin: 16px 0 32px;
`;

const PostList = styled.div`
  display: flex;
  flex-direction: column;
  gap: 18px;
`;

const NoDataBlock = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 48px;
  gap: 12px;
`;

const FileIconWrapper = styled.div`
  margin-top: 12px;
  svg {
    width: 50px;
    height: 50px;
    opacity: 0.4;
  }
`;

const NoDataText = styled.div`
  font-size: 13px;
  color: #808080;
  font-weight: 700;
`;

const LoadingWrapper = styled.div`
  padding: 32px;
  text-align: center;
  font-size: 14px;
  color: #555;
`;