import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useKakaoLogin } from '@/features/auth/hooks/useKakaoLogin';
import { LoadingSpinner } from '@/shared/ui/LoadingSpinner/LoadingSpinner';

const OAuthCallbackPage = () => {
  const navigate = useNavigate();
  const { mutate: loginWithKakao, isPending, isError } = useKakaoLogin();

  useEffect(() => {
    const code = new URL(window.location.href).searchParams.get('code');
    const redirectUri = window.location.hostname.startsWith('www.') 
      ? import.meta.env.VITE_KAKAO_REDIRECT_URI_WWW 
      : import.meta.env.VITE_KAKAO_REDIRECT_URI;
    
    if (code) {
      loginWithKakao({ 
        code,
        redirectUri
      }, {
        onSuccess: () => {
        },
        onError: (error) => {
          console.error('로그인 실패', error);
          alert('로그인에 실패하였습니다. 다시 시도해주세요.');
          navigate('/landing');
        },
      });
    }
  }, [loginWithKakao, navigate]);

  if (isPending) return <LoadingSpinner />;
  if (isError) return <p>로그인 에러가 발생했습니다.</p>;

  return null;
};

export default OAuthCallbackPage;

