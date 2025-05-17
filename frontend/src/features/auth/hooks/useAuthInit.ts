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
        setRefreshFailed(false);
      } catch (error) {
        // 에러 발생 시 모든 상태를 초기화
        clearAuth();
        setRefreshFailed(true);
      } finally {
        // 성공/실패 여부와 관계없이 초기화 완료 처리
        setInitializing(false);
      }
    };

    initializeAuth();
  }, [setAccessToken, setInitializing, clearAuth, setRefreshFailed]);
};