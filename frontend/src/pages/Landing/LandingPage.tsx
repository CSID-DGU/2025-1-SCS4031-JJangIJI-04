import styled from 'styled-components';
import { KakaoLoginButton } from '@/features/auth/ui/KakaoLoginButton/KakaoLoginButton';

export const LandingPage = () => {
  return (
    <Container>
      <TopSection>
        <LogoText>당신의 외식비 절약 목표 달성을 도와줄,</LogoText>
        <Title>한끼모아</Title>
      </TopSection>

      <MiddleSection>
        <LogoImage>
          <img src="/icons/logo.png" alt="한끼모아 로고" />
        </LogoImage>
      </MiddleSection>

      <BottomSection>
        <Divider>
          <DividerLine />
          <DividerText>SNS 간편 로그인</DividerText>
          <DividerLine />
        </Divider>
        <KakaoLoginButton />
      </BottomSection>
    </Container>
  );
};

const Container = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
  padding: var(--page-padding);
`;

const TopSection = styled.div`
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start;
  padding-top: 70px;
`;

const LogoText = styled.p`
  font-size: var(--font-size-sm);
  color: #ff6701;
  margin-bottom: 8px;
`;

const Title = styled.h1`
  color: #ff6701;
  font-size: var(--font-size-xl);
  font-weight: 700;
  margin-bottom: 24px;
`;

const MiddleSection = styled.div`
  flex: 2;
  display: flex;
  align-items: center;
  justify-content: center;
`;

const LogoImage = styled.div`
  margin: 32px 0;
  width: 200px;
  height: 200px;

  img {
    width: 100%;
    height: 100%;
    object-fit: contain;
  }
`;

const BottomSection = styled.div`
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-end;
  padding-bottom: 64px;
`;

const Divider = styled.div`
  max-width: 250px;
  width: 100%;
  display: flex;
  align-items: center;
  margin: 0 auto 16px;
`;

const DividerLine = styled.div`
  flex: 1;
  height: 1.5px;
  background-color: #808080;
`;

const DividerText = styled.span`
  color: #808080;
  font-size: var(--font-size-xs);
  font-weight: 600;
  margin: 0 8px;
`;
