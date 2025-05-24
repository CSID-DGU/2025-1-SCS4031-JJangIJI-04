import { useEffect } from 'react';
import { useAuthStore } from '@/features/auth/store/useAuthStore';
import { requestRefreshToken } from '@/features/auth/api/requestRefreshToken';
import { getUserInfo } from '../api/authApi';

export const useAuthInit = () => {
  const { setAccessToken, setInitializing, clearAuth, setRefreshFailed, setNickname, setProfileImage, setCategories } = useAuthStore();

  useEffect(() => {
    const initializeAuth = async () => {
      try {
        //쿠키에서 accessToken 찾기
        const cookieToken = document.cookie
          .split('; ')
          .find(row => row.startsWith('accessToken='))
          ?.split('=')[1];

        if (cookieToken) {
          //쿠키에 토큰이 있으면 그걸 사용
          setAccessToken(cookieToken);
          setRefreshFailed(false);
        } else {
          //쿠키에 토큰이 없으면 refresh 시도
          const token = await requestRefreshToken();
          setAccessToken(token);
          setRefreshFailed(false);
        }

        //accessToken 설정 후 유저 정보 요청
        const user = await getUserInfo();
        setNickname(user.nickname);
        setProfileImage(user.imageUrl);
        setCategories(user.categories);
      } catch (error) {
        //에러 발생 시 모든 상태를 초기화
        clearAuth();
        setRefreshFailed(true);
      } finally {
        setInitializing(false);
      }
    };

    initializeAuth();
  }, [setAccessToken, setInitializing, clearAuth, setRefreshFailed, setNickname, setProfileImage, setCategories]);
};