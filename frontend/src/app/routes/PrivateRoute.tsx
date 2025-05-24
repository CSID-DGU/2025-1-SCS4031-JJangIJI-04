import { Navigate } from 'react-router-dom';
import { useAuthStore } from '@/features/auth/store/useAuthStore';
import { ReactNode } from 'react';

interface PrivateRouteProps {
  children: ReactNode;
}

export const PrivateRoute = ({ children }: PrivateRouteProps) => {
  const accessToken = useAuthStore((state) => state.accessToken);
  const isInitializing = useAuthStore((state) => state.isInitializing);

  console.log('PrivateRoute 체크:', { accessToken, isInitializing });

  //로컬에서 UI 테스트 할 때 return 부분 주석 처리
  if (isInitializing) {
    return null; // 초기화 중에는 아무것도 렌더링하지 않음
  }

  if (!accessToken) {
    console.log('토큰 없음, 랜딩으로 리다이렉트'); 
    return <Navigate to="/landing" replace />;
  }

  console.log('인증 성공, 컴포넌트 렌더링'); 
  return children; 
};

