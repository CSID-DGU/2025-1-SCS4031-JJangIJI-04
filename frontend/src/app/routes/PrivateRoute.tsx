// GET API 완료 되면 주석 해제
import { Navigate, Outlet } from 'react-router-dom';
import { useAuthStore } from '@/features/auth/store/useAuthStore';
//import { useCheckUserCompletion } from '@/features/auth/hooks/useCheckUserCompletion';
//import { useEffect } from 'react';

export const PrivateRoute = () => {
  const accessToken = useAuthStore((state) => state.accessToken);
  const isInitializing = useAuthStore((state) => state.isInitializing);
  //const checkCompletion = useCheckUserCompletion();

  //로컬 테스트 시에는 주석 달고 진행
  //useEffect(() => {
  //  if (accessToken) {
  //    checkCompletion();
  //  }
  //}, [accessToken, checkCompletion]);

  if (isInitializing) {
    return null; // 초기화 중에는 아무것도 렌더링하지 않음
  }

  if (!accessToken) {
    return <Navigate to="/landing" replace />;
  }

  return <Outlet />; 
};

