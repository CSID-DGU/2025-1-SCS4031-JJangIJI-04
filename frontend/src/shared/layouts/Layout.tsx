import { ReactNode } from 'react';
import styled from 'styled-components';
import { Outlet } from 'react-router-dom';
import { useAuthInit } from '@/features/auth/hooks/useAuthInit';
import { useAuthStore } from '@/features/auth/store/useAuthStore';
import { LoadingSpinner } from '@/shared/ui/LoadingSpinner/LoadingSpinner';
import { BottomNavBar } from '@/widgets/BottomNavBar';

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
    return <LoadingSpinner />;
  }

  return (
    <Container>
      <Main $hasFooter={hasFooter}>
        <Outlet />
      </Main>
      {hasFooter && (
        <Footer>
          <BottomNavBar />
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
  overflow-x: hidden; 
  padding: var(--page-padding);
  padding-top: calc(var(--safe-area-top) + var(--page-padding));
  padding-bottom: ${(props) =>
    props.$hasFooter
      ? `calc(var(--footer-height) + var(--safe-area-bottom) + var(--page-padding))`
      : `calc(var(--safe-area-bottom) + var(--page-padding))`};
  
  // 스크롤바 숨김 추가
  scrollbar-width: none;
  &::-webkit-scrollbar {
    display: none;
  }
`;

const Footer = styled.footer`
  position: fixed;
  bottom: 0;
  left: 50%;
  transform: translateX(-50%);
  width: var(--max-width);
  min-width: var(--min-width);
  height: calc(var(--footer-height) + var(--safe-area-bottom));
  padding-bottom: var(--safe-area-bottom);
  background: var(--content-background);
  z-index: 100;
`;