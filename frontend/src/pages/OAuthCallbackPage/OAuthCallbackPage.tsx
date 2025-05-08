import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useKakaoLogin } from '@/features/auth/hooks/useKakaoLogin';
import { LoadingSpinner } from '@/shared/ui/LoadingSpinner/LoadingSpinner';
import { useCheckUserCompletion } from '@/features/auth/hooks/useCheckUserCompletion';

const OAuthCallbackPage = () => {
  const navigate = useNavigate();
  const { mutate: loginWithKakao, isPending, isError } = useKakaoLogin();
  const checkCompletion = useCheckUserCompletion();

  useEffect(() => {
    const code = new URL(window.location.href).searchParams.get('code');
    const redirectUri = 'http://localhost:5173/oauth/callback/kakao';
    
    if (code) {
      loginWithKakao({ 
        code,
        redirectUri
      }, {
        onSuccess: () => {
          checkCompletion();
        },
        onError: () => {
          alert('로그인에 실패하였습니다. 다시 시도해주세요.');
          navigate('/landing');
        },
      });
    }
  }, [loginWithKakao, navigate, checkCompletion]);

  if (isPending) return <LoadingSpinner />;
  if (isError) return <p>로그인 에러가 발생했습니다.</p>;

  return null;
};

export default OAuthCallbackPage;

