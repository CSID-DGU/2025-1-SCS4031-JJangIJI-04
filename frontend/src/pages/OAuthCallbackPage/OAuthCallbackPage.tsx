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

    // 조건만 검사, navigate는 하지 않음
    if (!code || !redirectUri || usedKakaoCode === code) return;

    setUsedKakaoCode(code);

    loginWithKakao(
      { code, redirectUri },
      {
        onSuccess: async () => {
          await checkUserCompletion(); // 이 안에서만 navigate 실행
        },
        onError: () => {
          setUsedKakaoCode(null);
          // navigate 생략 → 모든 리다이렉트는 checkUserCompletion() 안에서 처리
        },
      }
    );
  }, [isPending, loginWithKakao, checkUserCompletion, usedKakaoCode, setUsedKakaoCode]);

  if (isPending) return <LoadingSpinner />;

  return null;
};

export default OAuthCallbackPage;