import { Navigate } from 'react-router-dom';
import { useAuthStore } from '@/features/auth/store/useAuthStore';
import { ReactNode } from 'react';

interface PrivateRouteProps {
  children: ReactNode;  // 추가
}

export const PrivateRoute = ({ children }: PrivateRouteProps) => {
  const accessToken = useAuthStore((state) => state.accessToken);
  const isInitializing = useAuthStore((state) => state.isInitializing);

  if (isInitializing) {
    return null; // 초기화 중에는 아무것도 렌더링하지 않음
  }

  if (!accessToken) {
    return <Navigate to="/landing" replace />;
  }

  return children; 
};

