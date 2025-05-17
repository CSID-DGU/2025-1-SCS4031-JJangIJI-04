import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useKakaoLogin } from '@/features/auth/hooks/useKakaoLogin';
import { LoadingSpinner } from '@/shared/ui/LoadingSpinner/LoadingSpinner';
import { useCheckUserCompletion } from '@/features/auth/hooks/useCheckUserCompletion';
import { AxiosError } from 'axios';
import { useAuthStore } from '@/features/auth/store/useAuthStore';

const OAuthCallbackPage = () => {
  const navigate = useNavigate();
  const { mutate: loginWithKakao, isPending, isError } = useKakaoLogin();
  const checkUserCompletion = useCheckUserCompletion();
  const { usedKakaoCode, setUsedKakaoCode } = useAuthStore();

  useEffect(() => {
    if (isPending) return;

    const code = new URL(window.location.href).searchParams.get('code');
    const redirectUri = window.location.hostname.startsWith('www.')
      ? import.meta.env.VITE_KAKAO_REDIRECT_URI_WWW
      : import.meta.env.VITE_KAKAO_REDIRECT_URI;

    if (!code || !redirectUri) {
      navigate('/landing');
      return;
    }

    if (usedKakaoCode === code) {
      console.warn('이미 사용한 카카오 인가코드로 로그인 시도 방지');
      navigate('/landing');
      return;
    }

    setUsedKakaoCode(code);

    loginWithKakao(
      { code, redirectUri },
      {
        onSuccess: () => {
          window.history.replaceState({}, document.title, '/');
          checkUserCompletion();
        },
        onError: (error: Error) => {
          console.error('로그인 실패', error);
          const axiosError = error as AxiosError<any>;
          if (axiosError.response?.data?.exceptionCode === 'INVALID_PARAMETER') {
            alert(axiosError.response.data.message);
          } else {
            alert('로그인에 실패하였습니다. 다시 시도해주세요.');
          }
          setUsedKakaoCode(null);
          window.history.replaceState({}, document.title, '/landing');
          navigate('/landing');
        },
      }
    );
  }, [navigate, loginWithKakao, checkUserCompletion, isPending, usedKakaoCode, setUsedKakaoCode]);

  if (isPending) return <LoadingSpinner />;
  if (isError) return <p>로그인 에러가 발생했습니다.</p>;

  return null;
};

export default OAuthCallbackPage;