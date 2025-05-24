import { useEffect } from 'react';
import { useKakaoLogin } from '@/features/auth/hooks/useKakaoLogin';
import { LoadingSpinner } from '@/shared/ui/LoadingSpinner/LoadingSpinner';
import { useCheckUserCompletion } from '@/features/auth/hooks/useCheckUserCompletion';
import { useAuthStore } from '@/features/auth/store/useAuthStore';

const OAuthCallbackPage = () => {
  const { mutate: loginWithKakao, isPending } = useKakaoLogin();
  const checkUserCompletion = useCheckUserCompletion();
  const { usedKakaoCode, setUsedKakaoCode } = useAuthStore();

  useEffect(() => {
    if (isPending) return;

    const code = new URL(window.location.href).searchParams.get('code');
    const redirectUri = window.location.hostname.startsWith('www.')
      ? import.meta.env.VITE_KAKAO_REDIRECT_URI_WWW
      : import.meta.env.VITE_KAKAO_REDIRECT_URI;

    if (!code || !redirectUri || usedKakaoCode === code) return;

    setUsedKakaoCode(code);

    loginWithKakao(
      { code, redirectUri },
      {
        onSuccess: async () => {
          await checkUserCompletion(); // 모든 정상 흐름에서 navigate
        },
        onError: () => {
          setUsedKakaoCode(null);
          checkUserCompletion(true); // 에러 강제 처리
        },
      }
    );
  }, [isPending, loginWithKakao, usedKakaoCode, setUsedKakaoCode, checkUserCompletion]);

  if (isPending) return <LoadingSpinner />;

  return null;
};

export default OAuthCallbackPage;