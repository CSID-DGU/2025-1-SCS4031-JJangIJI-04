import { useEffect } from 'react';
import { useAuthStore } from '@/features/auth/store/useAuthStore';
import { requestRefreshToken } from '@/features/auth/api/requestRefreshToken';

export const useAuthInit = () => {
  const { setAccessToken, setInitializing, clearAuth, setRefreshFailed } = useAuthStore();

  useEffect(() => {
    const initializeAuth = async () => {
      try {
        const token = await requestRefreshToken();
        setAccessToken(token);
        setInitializing(false);
        setRefreshFailed(false);  // 추가: 성공 시 실패 상태 초기화
      } catch {
        clearAuth();
        setInitializing(false);
        setRefreshFailed(true);  // 추가: 실패 상태 설정
      }
    };

    initializeAuth();
  }, [setAccessToken, setInitializing, clearAuth, setRefreshFailed]);
};