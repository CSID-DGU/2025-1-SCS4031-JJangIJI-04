import { useMutation } from '@tanstack/react-query';
import { requestKakaoLogin } from '@/features/auth/api/authApi';
import { useAuthStore } from '@/features/auth/store/useAuthStore';
import { KakaoLoginResponse } from '@/features/auth/types/auth';

interface KakaoLoginParams {
  code: string;
  redirectUri: string;
}

export const useKakaoLogin = () => {
  const setAccessToken = useAuthStore((state) => state.setAccessToken);
  const setNickname = useAuthStore((state) => state.setNickname);
  const setProfileImage = useAuthStore((state) => state.setProfileImage);

  return useMutation<KakaoLoginResponse, Error, KakaoLoginParams>({
    mutationFn: ({ code, redirectUri }) => requestKakaoLogin(code, redirectUri),
    onSuccess: ({ accessToken, nickname, imageUrl }) => {
      console.log('카카오 로그인 성공, 토큰:', accessToken);
      setAccessToken(accessToken);
      console.log('토큰 저장 후 store 상태:', useAuthStore.getState()); 
      setNickname(nickname);
      setProfileImage(imageUrl);
    },
  });
};

