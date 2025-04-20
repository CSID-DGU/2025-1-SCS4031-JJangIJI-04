import { ReactNode } from 'react';
import styled from 'styled-components';

interface LayoutProps {
  children: ReactNode;
  hasFooter?: boolean; // footer 유무 옵션
}

interface MainProps {
  $hasFooter: boolean;
}

export const Layout = ({ children, hasFooter = true }: LayoutProps) => {
  return (
    <Container>
      <Main $hasFooter={hasFooter}>{children}</Main>
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
