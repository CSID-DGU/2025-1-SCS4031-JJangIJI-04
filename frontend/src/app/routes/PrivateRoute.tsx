import { Navigate, Outlet } from 'react-router-dom';
import { useAuthStore } from '@/features/auth/store/useAuthStore';
import { useCheckUserCompletion } from '@/features/auth/hooks/useCheckUserCompletion';
import { useEffect } from 'react';

export const PrivateRoute = () => {
  const accessToken = useAuthStore((state) => state.accessToken);
  const checkCompletion = useCheckUserCompletion();

  //로컬 테스트 시에는 주석 달고 진행
  /*useEffect(() => {
    if (accessToken) {
      checkCompletion();
    }
  }, [accessToken, checkCompletion]);

  if (!accessToken) {
    return <Navigate to="/landing" replace />;
  }*/

  return <Outlet />; 
};

