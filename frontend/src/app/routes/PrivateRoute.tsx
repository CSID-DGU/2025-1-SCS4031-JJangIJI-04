import { Navigate, Outlet } from 'react-router-dom';
import { useAuthStore } from '@/features/auth/store/useAuthStore';

export const PrivateRoute = () => {
  const accessToken = useAuthStore((state) => state.accessToken);

  const isAuthenticated = !!accessToken; //accessToken이 있으면 로그인된 상태로 판단

  if (!isAuthenticated) {
    return <Navigate to="/landing" replace />; //랜딩페이지로 리다이렉트
  }

  return <Outlet />; //하위 라우트 렌더링
};

