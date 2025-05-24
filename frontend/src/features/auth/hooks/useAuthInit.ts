import { useEffect } from 'react';
import { useAuthStore } from '@/features/auth/store/useAuthStore';
import { requestRefreshToken } from '@/features/auth/api/requestRefreshToken';
import { getUserInfo } from '../api/authApi';

export const useAuthInit = () => {
  const { setAccessToken, setInitializing, clearAuth, setRefreshFailed, setNickname, setProfileImage, setCategories } = useAuthStore();

  useEffect(() => {
    const initializeAuth = async () => {
      try {
        console.log('전체 쿠키:', document.cookie);  // 전체 쿠키 확인
        
        //쿠키에서 accessToken 찾기
        const cookieToken = document.cookie
          .split('; ')
          .find(row => row.startsWith('accessToken='))
          ?.split('=')[1];

        console.log('쿠키에서 찾은 토큰:', cookieToken); 

        if (cookieToken) {
          //쿠키에 토큰이 있으면 그걸 사용
          setAccessToken(cookieToken);
          console.log('토큰 저장 시도:', cookieToken);
          setRefreshFailed(false);
        } else {
          //쿠키에 토큰이 없으면 refresh 시도
          console.log('refresh 토큰 시도');
          const token = await requestRefreshToken();
          setAccessToken(token);
          setRefreshFailed(false);
        }

        //accessToken 설정 후 유저 정보 요청
        const user = await getUserInfo();
        console.log('유저 정보 받아옴:', user);
        setNickname(user.nickname);
        setProfileImage(user.imageUrl);
        setCategories(user.categories);
      } catch (error) {
        console.error('초기화 중 에러:', error);
        clearAuth();
        setRefreshFailed(true);
      } finally {
        setInitializing(false);
      }
    };

    initializeAuth();
  }, [setAccessToken, setInitializing, clearAuth, setRefreshFailed, setNickname, setProfileImage, setCategories]);
};