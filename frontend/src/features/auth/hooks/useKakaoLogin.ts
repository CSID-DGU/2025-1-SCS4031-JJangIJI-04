import { useMutation } from '@tanstack/react-query';
import { requestKakaoLogin } from '@/features/auth/api/authApi';
import { useAuthStore } from '@/features/auth/store/useAuthStore';

interface KakaoLoginResponse {
  accessToken: string;
  isSignedUp: boolean;
}

export const useKakaoLogin = () => {
  const setAccessToken = useAuthStore((state) => state.setAccessToken);

  return useMutation<KakaoLoginResponse, Error, string>({
    mutationFn: requestKakaoLogin,
    onSuccess: ({ accessToken }) => {
      setAccessToken(accessToken);
    },
  });
};

