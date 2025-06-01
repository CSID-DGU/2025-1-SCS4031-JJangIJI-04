import { FullScreenPopup } from '@/features/myPage/ui/FullScreenPopup';
import { RestaurantListItem } from '@/features/restaurant/ui/RestaurantListItem';
import { useState } from 'react';
import styled from 'styled-components';
import { useMonthlyExpenseTotal } from '@/features/myPage/api/useMonthlyExpenseTotal';
import { useAuthStore } from '@/features/auth/store/useAuthStore';
import { useUserInfo } from '@/features/auth/api/useUserInfo';

const MyPage = () => {
  const [activeTab, setActiveTab] = useState<'bookmark' | 'likes'>('bookmark');
  const [popupType, setPopupType] = useState<
    null | 'nickname' | 'category' | 'settings'
  >(null);
  const { userId } = useAuthStore();
  const { data: monthlyTotal } = useMonthlyExpenseTotal(userId ?? null);
  const { data: userInfo, isLoading } = useUserInfo();

  return (
    <Container>
      <Header>
        <TopRight>
          <GearButton onClick={() => setPopupType('settings')}>
            <img src="/icons/settings.svg" alt="설정" />
          </GearButton>
        </TopRight>
        <ProfileSection>
          <LeftProfile>
            <Avatar
              src={
                isLoading
                  ? '/icons/community/user-avatar.svg'
                  : (userInfo?.imageUrl ?? '/icons/community/user-avatar.svg')
              }
              alt="프로필 이미지"
            />
            <UserInfo>
              {isLoading ? (
                <Nickname>불러오는 중...</Nickname>
              ) : (
                <Nickname>{userInfo?.nickname ?? '알 수 없음'}</Nickname>
              )}
              <SpentText>
                이번 달 총 지출 금액{' '}
                <strong>{monthlyTotal?.toLocaleString() ?? 0}원</strong>
              </SpentText>
            </UserInfo>
          </LeftProfile>
          <ToggleGroup>
            <ToggleButton $active={false}>달력 공개</ToggleButton>
            <ToggleButton $active>달력 비공개</ToggleButton>
          </ToggleGroup>
        </ProfileSection>
      </Header>

      <ButtonGroup>
        <ActionButton onClick={() => setPopupType('nickname')}>
          닉네임 변경
        </ActionButton>
        <ActionButton onClick={() => setPopupType('category')}>
          선호 음식 <br />
          카테고리 변경
        </ActionButton>
      </ButtonGroup>

      <TabGroup>
        <Tab
          $active={activeTab === 'bookmark'}
          onClick={() => setActiveTab('bookmark')}
        >
          내가 북마크한 식당
        </Tab>
        <Tab
          $active={activeTab === 'likes'}
          onClick={() => setActiveTab('likes')}
        >
          좋아요 누른 피드
        </Tab>
      </TabGroup>

      {activeTab === 'bookmark' ? (
        // 북마크 더미 처리
        false ? (
          <RestaurantListItem restaurants={[]} />
        ) : (
          <EmptyState>
            <EmptyIcon src="/icons/bookmarks.svg" alt="북마크 없음" />
            <Message>아직 북마크한 식당이 없어요</Message>
          </EmptyState>
        )
      ) : // 좋아요 피드도 더미 처리
      false ? (
        <PostList>
          {/* 나중에 useLikedPosts() 훅으로 받은 데이터 map */}
        </PostList>
      ) : (
        <EmptyState>
          <EmptyIcon src="/icons/insert-comment.svg" alt="이모지 피드 없음" />
          <Message>아직 이모지 누른 피드가 없어요</Message>
        </EmptyState>
      )}

      <FullScreenPopup
        visible={!!popupType}
        onClose={() => setPopupType(null)}
        type={popupType as any}
      />
    </Container>
  );
};

export default MyPage;

const Container = styled.div`
  padding: 24px 0;
`;

const Header = styled.div`
  display: flex;
  flex-direction: column;
`;

const ProfileSection = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 12px;
`;

const LeftProfile = styled.div`
  display: flex;
  gap: 12px;
`;

const Avatar = styled.img`
  width: 50px;
  height: 50px;
  border-radius: 50%;
  object-fit: cover;
`;

const UserInfo = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
`;

const Nickname = styled.div`
  font-size: var(--font-size-sm);
  font-weight: bold;
  margin-bottom: 2px;
`;

const SpentText = styled.div`
  font-size: var(--font-size-3xs);
  color: #555;
  strong {
    font-weight: bold;
    color: #111;
  }
`;

const TopRight = styled.div`
  display: flex;
  justify-content: flex-end;
  gap: 12px;
`;

const ToggleGroup = styled.div`
  display: flex;
  background-color: #ffc288;
  border-radius: 999px;
  overflow: hidden;
  width: 130px;
  height: 24px;
`;

const ToggleButton = styled.button<{ $active?: boolean }>`
  flex: 1;
  padding: ${({ $active }) => ($active ? '6px 8px' : '6px 0')};
  border: none;
  border-radius: ${({ $active }) => ($active ? '20px' : '0')};
  background-color: ${({ $active }) => ($active ? '#FF6701' : '#FFC288')};
  color: white;
  font-weight: bold;
  font-size: var(--font-size-4xs);
  cursor: pointer;
  transition: background-color 0.2s ease;
`;

const GearButton = styled.button`
  background: none;
  border: none;
  cursor: pointer;
  img {
    width: 20px;
    height: 20px;
  }
`;

const ButtonGroup = styled.div`
  display: flex;
  justify-content: center;
  gap: 16px;
  margin: 24px auto;
  width: 70%;
`;

const ActionButton = styled.button`
  flex: 1;
  padding: 7px 0;
  font-size: 14px;
  border-radius: 12px;
  border: 1px solid #999;
  background: white;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.08);
  font-size: var(--font-size-3xs);
  cursor: pointer;
  text-align: center;
  white-space: pre-line;
`;

const TabGroup = styled.div`
  display: flex;
  justify-content: center;
`;

const Tab = styled.button<{ $active?: boolean }>`
  padding: 12px 16px;
  font-size: var(--font-size-2xs);
  border: none;
  flex-grow: 1;
  background: none;
  color: ${({ $active }) => ($active ? '#f97316' : '#808080')};
  border-bottom: ${({ $active }) =>
    $active ? '1px solid #f97316' : '1px solid #808080'};
  font-weight: bold;
  cursor: pointer;
`;

const PostList = styled.div`
  display: flex;
  flex-direction: column;
  margin-top: 10px;
  gap: 24px;
`;

const EmptyState = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  min-height: 50vh;
  padding-top: 40px;
  gap: 12px;
  color: #808080;
  text-align: center;
`;

const EmptyIcon = styled.img`
  width: 48px;
  height: 48px;
`;

const Message = styled.div`
  font-size: var(--font-size-xs);
`;
