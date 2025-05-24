import { useEffect } from 'react';
import { useAuthStore } from '@/features/auth/store/useAuthStore';
import { requestRefreshToken } from '@/features/auth/api/requestRefreshToken';
import { getUserInfo } from '../api/authApi';

export const useAuthInit = () => {
  const { setAccessToken, setInitializing, clearAuth, setRefreshFailed, setNickname, setProfileImage, setCategories } = useAuthStore();

  useEffect(() => {
    const initializeAuth = async () => {
      try {
        const token = await requestRefreshToken();
        setAccessToken(token);
        setRefreshFailed(false);

        // accessToken 설정 후 유저 정보 요청
        const user = await getUserInfo();
        setNickname(user.nickname);
        setProfileImage(user.imageUrl);
        setCategories(user.categories);
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
  }, [setAccessToken, setInitializing, clearAuth, setRefreshFailed, setNickname, setProfileImage, setCategories]);
};