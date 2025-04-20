import { Navigate, Outlet } from 'react-router-dom';

export const PrivateRoute = () => {
  // TODO: 실제 인증 상태 체크 로직 추후 추가 필요
  const isAuthenticated = false; // 임시로 false로 설정 ( 인증 불가 상태면 랜딩페이지로 리다이렉트) useKakaoAuth();  // 커스텀 훅으로 구현예정

  if (!isAuthenticated) {
    return <Navigate to="/landing" replace />;
  }

  return <Outlet />;
};
