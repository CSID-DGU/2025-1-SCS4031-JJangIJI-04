import styled from 'styled-components';
import { CommunityPost } from '@/features/community/types/community';
import { EmojiReactionPanel } from '@/features/community/ui/EmojiReactionPanel';

interface CommunityCardProps {
  post: CommunityPost;
}

export const CommunityCard = ({ post }: CommunityCardProps) => {
  return (
    <CardWrapper>
      <TopRow>
        <Profile>
          <ProfileImg
            src={post.profileImage || '/icons/community/user-avatar.svg'}
            alt="프로필"
          />
          <UserInfo>
            <Nickname>{post.nickname}</Nickname>
            <DateText>{post.date}</DateText>
          </UserInfo>
        </Profile>
        <BudgetInfo>
          <span>{post.budget.used.toLocaleString()}원</span>
          <DividerText>/</DividerText>
          <span>{post.budget.total.toLocaleString()}원</span>
        </BudgetInfo>
      </TopRow>

      <RestaurantInfo>
        🍽️ {post.restaurant.name} · {post.restaurant.category} ·{' '}
        {post.restaurant.price.toLocaleString()}원
      </RestaurantInfo>

      <Content>{post.content}</Content>

      <EmojiReactionPanel reactions={post.emojiReactions} />
    </CardWrapper>
  );
};

const CardWrapper = styled.div`
  padding: 16px;
  border-radius: 12px;
  background-color: #fff;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.05);
  display: flex;
  flex-direction: column;
`;

const TopRow = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
`;

const Profile = styled.div`
  display: flex;
  align-items: center;
`;

const ProfileImg = styled.img`
  width: 36px;
  height: 36px;
  border-radius: 50%;
  margin-right: 8px;
`;

const UserInfo = styled.div`
  display: flex;
  flex-direction: column;
`;

const Nickname = styled.div`
  font-size: var(--font-size-sm);
  font-weight: 600;
`;

const DateText = styled.div`
  font-size: var(--font-size-3xs);
  color: #888;
`;

const BudgetInfo = styled.div`
  font-size: var(--font-size-xs);
  color: #666;
`;

const DividerText = styled.span`
  margin: 0 4px;
  color: #ccc;
`;

const RestaurantInfo = styled.div`
  margin: 8px 0;
  font-size: var(--font-size-xs);
  color: #999;
`;

const Content = styled.div`
  font-size: var(--font-size-sm);
  margin-bottom: 12px;
`;
