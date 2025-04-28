import { ReactNode } from 'react';
import styled from 'styled-components';
import { Outlet } from 'react-router-dom';
import { useAuthInit } from '@/features/auth/hooks/useAuthInit';
import { useAuthStore } from '@/features/auth/store/useAuthStore';

interface LayoutProps {
  children?: ReactNode;
  hasFooter?: boolean;
}

interface MainProps {
  $hasFooter: boolean;
}

export const Layout = ({ hasFooter = true }: LayoutProps) => {
  useAuthInit(); // 앱 시작 시 로그인 복구 시도
  const isInitializing = useAuthStore((s) => s.isInitializing);

  if (isInitializing) {
    return <LoadingScreen> 로그인 상태 확인 중...</LoadingScreen>;  //추후 로딩스피너 적용 예정
  }

  return (
    <Container>
      <Main $hasFooter={hasFooter}>
        <Outlet />
      </Main>
      {hasFooter && (
        <Footer>
          <nav>{/* 추후 footer import */}</nav>
        </Footer>
      )}
    </Container>
  );
};

const Container = styled.div`
  width: var(--max-width);
  height: var(--max-height);
  overflow: hidden;
  background-color: var(--content-background);
  position: relative;
  display: flex;
  flex-direction: column;
`;

const Main = styled.main<MainProps>`
  flex: 1;
  overflow-y: auto;
  padding: var(--page-padding);
  padding-top: calc(var(--safe-area-top) + var(--page-padding));
  padding-bottom: ${(props) =>
    props.$hasFooter
      ? `calc(var(--footer-height) + var(--safe-area-bottom) + var(--page-padding))`
      : `calc(var(--safe-area-bottom) + var(--page-padding))`};
`;

const Footer = styled.footer`
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  height: calc(var(--footer-height) + var(--safe-area-bottom));
  padding-bottom: var(--safe-area-bottom);
  background: var(--content-background);
  z-index: 100;
`;

const LoadingScreen = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.2rem;
  background: var(--content-background);
`;
