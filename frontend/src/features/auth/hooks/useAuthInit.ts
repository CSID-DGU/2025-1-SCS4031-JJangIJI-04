import { useEffect } from 'react';
import { useAuthStore } from '@/features/auth/store/useAuthStore';
import { requestRefreshToken } from '@/features/auth/api/requestRefreshToken';

export const useAuthInit = () => {
  const { setAccessToken, setInitializing, clearAuth } = useAuthStore();
  const accessToken = useAuthStore((state) => state.accessToken);

  useEffect(() => {
    const initializeAuth = async () => {
      try {
        // 이미 토큰이 있으면 refresh 스킵
        if (accessToken) {
          setInitializing(false);
          return;
        }
        
        const token = await requestRefreshToken();
        setAccessToken(token);
      } catch {
        clearAuth();
      } finally {
        setInitializing(false);
      }
    };

    initializeAuth();
  }, [accessToken, setAccessToken, setInitializing, clearAuth]);
};
