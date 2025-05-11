import { useEffect } from 'react';
import { useAuthStore } from '@/features/auth/store/useAuthStore';
import { requestRefreshToken } from '@/features/auth/api/requestRefreshToken';

export const useAuthInit = () => {
  const { setAccessToken, setInitializing, clearAuth } = useAuthStore();

  useEffect(() => {
    const initializeAuth = async () => {
      try {
        const token = await requestRefreshToken();
        setAccessToken(token);
      } catch {
        clearAuth();
      } finally {
        setInitializing(false);
      }
    };

    initializeAuth();
  }, [setAccessToken, setInitializing, clearAuth]);
};