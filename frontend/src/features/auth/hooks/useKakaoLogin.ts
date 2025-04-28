import { useMutation } from '@tanstack/react-query';
import { requestKakaoLogin } from '@/features/auth/api/authApi';
import { useAuthStore } from '@/features/auth/store/useAuthStore';  

export const useKakaoLogin = () => {
  const setAccessToken = useAuthStore((state) => state.setAccessToken);

  return useMutation({
    mutationFn: requestKakaoLogin,
    onSuccess: (accessToken) => {
      setAccessToken(accessToken);
    },
  });
};
