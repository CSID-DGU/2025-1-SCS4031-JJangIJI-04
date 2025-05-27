// FullScreenPopup.tsx
import { useEffect } from 'react';
import { createPortal } from 'react-dom';
import styled from 'styled-components';
import { CategorySelector } from '@/features/preferences/ui/CategorySelector';
import { InputField } from '@/shared/ui/InputField';

interface FullScreenPopupProps {
  visible: boolean;
  onClose: () => void;
  type: 'nickname' | 'category' | 'settings';
}

const settingData = [
  { label: '공지사항', link: '/' },
  { label: '고객센터', link: '/' },
  { label: '라이선스 정보', link: '/' },
];

export const FullScreenPopup = ({
  visible,
  onClose,
  type,
}: FullScreenPopupProps) => {
  useEffect(() => {
    if (visible) {
      document.body.style.overflow = 'hidden';
    } else {
      document.body.style.overflow = '';
    }
    return () => {
      document.body.style.overflow = '';
    };
  }, [visible]);

  if (!visible) return null;

  return createPortal(
    <Overlay>
      <Popup>
        <Header>
          <BackButton onClick={onClose}>
            <img src="/icons/arrow-left.svg" alt="back" />
          </BackButton>
          {type === 'settings' && <HeaderTitle>설정</HeaderTitle>}
        </Header>

        <Body>
          {type === 'nickname' && (
            <>
              <NicknameForm />
              <BottomButton>변경하기</BottomButton>
            </>
          )}
          {type === 'category' && (
            <>
              <Title>선호하는 음식 카테고리를 선택해 주세요</Title>
              <CategorySelector selected={[]} onChange={() => {}} />
              <BottomButton>변경하기</BottomButton>
            </>
          )}
          {type === 'settings' && <SettingsList />}
        </Body>
      </Popup>
    </Overlay>,
    document.body
  );
};

const NicknameForm = () => (
  <NicknameFormContainer>
    <Instruction>변경하실 닉네임을 입력해주세요</Instruction>
    <InputField
      value={''}
      onChange={() => {}}
      placeholder="최대 10자까지 가능하며, 공백은 허용되지 않습니다"
      maxLength={10}
      error={''}
      isValid={true}
    />
  </NicknameFormContainer>
);

const SettingsList = () => (
  <SettingsContainer>
    {settingData.map((item) => (
      <SettingItem key={item.label}>
        <span>{item.label}</span>
        <img src="/icons/arrow-right.svg" alt="arrow" />
      </SettingItem>
    ))}
    <Divider />
    <SettingItem className="logout">로그아웃</SettingItem>
    <SettingItem className="withdraw">회원탈퇴</SettingItem>
  </SettingsContainer>
);

// Styles
const Overlay = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  z-index: 9999;
  background-color: white;
  display: flex;
  justify-content: center;
  align-items: flex-start;
  padding-top: 10px;
`;

const Popup = styled.div`
  width: 100%;
  max-width: 480px;
  height: 100%;
  background-color: #fff;
  position: relative;
  padding: 12px;
  display: flex;
  flex-direction: column;
`;

const Header = styled.div`
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  height: 48px;
`;

const BackButton = styled.button`
  position: absolute;
  top: 50%;
  left: 0;
  transform: translateY(-50%);
  background: none;
  border: none;
  cursor: pointer;
`;

const HeaderTitle = styled.h1`
  font-size: 16px;
  font-weight: bold;
  margin: 0;
`;

const Title = styled.h2`
  font-size: 18px;
  font-weight: 600;
`;

const Body = styled.div`
  flex: 1;
  padding: 24px;
  display: flex;
  flex-direction: column;
`;

const BottomButton = styled.button`
  margin-top: auto;
  width: 100%;
  padding: 14px;
  font-weight: bold;
  font-size: 16px;
  border: none;
  border-radius: 8px;
  background-color: #ff6701;
  color: #fff;
  cursor: pointer;
`;

const NicknameFormContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 16px;
`;

const Instruction = styled.div`
  font-size: 16px;
  font-weight: 600;
`;

const SettingsContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 24px;
  margin-top: 16px;

  .logout {
    color: #e57373;
    margin-top: 24px;
  }

  .withdraw {
    color: #e57373;
  }
`;

const SettingItem = styled.div`
  font-size: 16px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  cursor: pointer;
`;

const Divider = styled.div`
  height: 4px;
  background-color: #fcdcb4;
  margin: 12px 0;
`;
