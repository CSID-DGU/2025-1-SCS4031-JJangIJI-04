import { NavLink } from 'react-router-dom';
import styled from 'styled-components';

import home from '@/assets/icons/home.svg?react';
import insert_comment from '@/assets/icons/insert-comment.svg?react';
import thumb_up from '@/assets/icons/thumb-up.svg?react';
import person_outline from '@/assets/icons/person-outline.svg?react';

const navItems = [
  { to: '/main', icon: home, label: '메인페이지' },
  { to: '/community', icon: insert_comment, label: '피드페이지' },
  { to: '/restaurants', icon: thumb_up, label: '추천페이지' },
  { to: '/mypage', icon: person_outline, label: '마이페이지' },
];

export const BottomNavBar = () => {
  return (
    <NavWrapper className="app-footer">
      {navItems.map(({ to, icon: Icon, label }) => (
        <NavItem to={to} key={to} end>
          {({ isActive }) => (
            <IconLabelWrapper>
              <StyledIcon as={Icon} $active={isActive} />
              <Label $active={isActive}>{label}</Label>
            </IconLabelWrapper>
          )}
        </NavItem>
      ))}
    </NavWrapper>
  );
};

const NavWrapper = styled.nav`
  display: flex;
  width: 100%;
  max-width: var(--max-width);
  min-width: var(--min-width);
  margin: 0 auto;
  height: var(--footer-height);
  background-color: var(--content-background);
  padding-bottom: env(safe-area-inset-bottom, 0px);
  overflow: hidden;
`;

const NavItem = styled(NavLink)`
  flex: 1;
  text-decoration: none;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 8px 0;
`;

const IconLabelWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const StyledIcon = styled.svg<{ $active: boolean }>`
  width: 20px;
  height: 20px;
  transform: ${({ $active }) => ($active ? 'scale(1.15)' : 'scale(1)')};
  transition: transform 0.25s ease, fill 0.25s ease;

  path {
    fill: ${({ $active }) => ($active ? '#FF6F0F' : '#ABB7C2')};
  }
`;

const Label = styled.span<{ $active: boolean }>`
  font-size: var(--font-size-3xs);
  margin-top: 2px;
  color: ${({ $active }) => ($active ? '#FF6F0F' : '#ABB7C2')};
  transform: ${({ $active }) => ($active ? 'scale(1.05)' : 'scale(1)')};
  opacity: ${({ $active }) => ($active ? 1 : 0.8)};
  transition: transform 0.25s ease, color 0.25s ease, opacity 0.25s ease;
`;
