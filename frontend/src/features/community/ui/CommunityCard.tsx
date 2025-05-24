import styled from 'styled-components';
import { useState } from 'react';
import { CommunityPost } from '@/features/community/types/community';
import { EmojiReactionPanel } from '@/features/community/ui/EmojiReactionPanel';
import { BudgetGauge } from '@/features/community/ui/BudgetGauge';
import { EmojiAddButton } from '@/features/community/ui/EmojiAddButton';

interface CommunityCardProps {
  post: CommunityPost;
}

export const CommunityCard = ({ post }: CommunityCardProps) => {
  const [reactions, setReactions] = useState<Partial<Record<number, number>>>(
    post.emojiReactions || {}
  );
  const [selectedEmoji, setSelectedEmoji] = useState<number | null>(null);

  const handleAddReaction = (emoji: number) => {
    // if (selectedEmoji === emoji) return; // 이미 선택한 이모지 클릭 시 무시
    setReactions((prev) => {
      const updated = { ...prev };

      // 이미 선택한 이모지를 다시 누른 경우 → 취소
      if (selectedEmoji === emoji) {
        const prevCount = updated[emoji] || 0;
        if (prevCount > 1) {
          updated[emoji] = prevCount - 1;
        } else {
          delete updated[emoji];
        }
        setSelectedEmoji(null); // 선택 해제
        return updated;
      }

      // 이전 이모지가 있었다면 제거
      if (selectedEmoji !== null && selectedEmoji !== undefined) {
        const prevCount = updated[selectedEmoji] || 0;
        if (prevCount > 1) {
          updated[selectedEmoji] = prevCount - 1;
        } else {
          delete updated[selectedEmoji];
        }
      }

      // 새 이모지를 선택
      updated[emoji] = (updated[emoji] || 0) + 1;
      setSelectedEmoji(emoji);
      return updated;
    });

    // 현재 선택한 이모지 갱신
    setSelectedEmoji(emoji);
  };

  const restaurantInfoItems = [
    {
      icon: '/icons/community/emojis/store.svg',
      label: post.restaurant.name,
      alt: '식당',
    },
    {
      icon: '/icons/community/emojis/menu.svg',
      label: post.restaurant.category,
      alt: '카테고리',
    },
    {
      icon: '/icons/community/emojis/wallet.svg',
      label: `${post.restaurant.price.toLocaleString()}원`,
      alt: '가격',
    },
  ];

  return (
    <CardWrapper>
      <LeftSection>
        <ProfileImg
          src={post.profileImage || '/icons/community/user-avatar.svg'}
          alt="프로필"
        />
      </LeftSection>

      <RightSection>
        <TopInfoRow>
          <TextGroup>
            <TopRow>
              <Nickname>{post.nickname}</Nickname>
              <DateText>{post.date}</DateText>
            </TopRow>
            <RestaurantInfo>
              {restaurantInfoItems.map((item, index) => (
                <InfoItem key={index}>
                  <img src={item.icon} alt={item.alt} />
                  <span>{item.label}</span>
                </InfoItem>
              ))}
            </RestaurantInfo>
          </TextGroup>
          <FixedGaugeWrapper>
            <BudgetGauge used={post.budget.used} total={post.budget.total} />
          </FixedGaugeWrapper>
        </TopInfoRow>

        <Content>{post.content}</Content>
        <EmojiReactionPanelWrapper>
          <EmojiAddButton onSelect={handleAddReaction} />
          <EmojiReactionPanel
            reactions={reactions}
            selected={selectedEmoji}
            onClickEmoji={handleAddReaction}
          />
        </EmojiReactionPanelWrapper>
      </RightSection>
    </CardWrapper>
  );
};

const CardWrapper = styled.div`
  display: flex;
  padding: 20px 0;

  /* border-bottom: 1px solid #eee; */
`;

const LeftSection = styled.div`
  flex-shrink: 0;
  margin-right: 8px;
`;

const ProfileImg = styled.img`
  width: 40px;
  height: 40px;
  border-radius: 50%;
`;

const RightSection = styled.div`
  flex: 1;
  display: flex;
  flex-direction: column;
`;

const TopInfoRow = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  gap: 16px;
`;

const TextGroup = styled.div`
  display: flex;
  flex-direction: column;
  flex: 1; // 🟢 TextGroup이 너비를 꽉 채우게
`;

const TopRow = styled.div`
  display: flex;
  align-items: center;
  gap: 8px;
`;

const Nickname = styled.div`
  font-size: var(--font-size-2xs);
  font-weight: bold;
`;

const DateText = styled.div`
  font-size: var(--font-size-4xs);
  color: #999;
`;

const InfoItem = styled.div`
  display: flex;
  align-items: center;
  gap: 2px;
  img {
    width: 12px;
    height: 12px;
  }

  span {
    font-size: var(--font-size-4xs);
  }
`;

const FixedGaugeWrapper = styled.div`
  flex-shrink: 0;
  width: 119px; // 🟠 Gauge의 정확한 width에 맞춰 고정
`;

const RestaurantInfo = styled.div`
  display: flex;
  gap: 4px;
  margin-top: 4px;
`;

const Content = styled.div`
  margin-top: 12px;
  margin-bottom: 6px;
  font-size: var(--font-size-4xs);
`;
const EmojiReactionPanelWrapper = styled.div`
  display: flex;
  position: relative;
  gap: 4px;
`;
