import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useKakaoLogin } from '@/features/auth/hooks/useKakaoLogin';
import { LoadingSpinner } from '@/shared/ui/LoadingSpinner/LoadingSpinner';
import { useCheckUserCompletion } from '@/features/auth/hooks/useCheckUserCompletion';
import { AxiosError } from 'axios';

const OAuthCallbackPage = () => {
  const navigate = useNavigate();
  const { mutate: loginWithKakao, isPending, isError } = useKakaoLogin();
  const checkUserCompletion = useCheckUserCompletion();

  useEffect(() => {
  const code = new URL(window.location.href).searchParams.get('code');
  const redirectUri = window.location.hostname.startsWith('www.') 
    ? import.meta.env.VITE_KAKAO_REDIRECT_URI_WWW 
    : import.meta.env.VITE_KAKAO_REDIRECT_URI;
  
  // code와 redirectUri 모두 존재하는지 확인
  if (code && redirectUri) {
    loginWithKakao({ 
      code,
      redirectUri
    }, {
      onSuccess: () => {
        checkUserCompletion();
      },
      onError: (error: Error) => {
        console.error('로그인 실패', error);
        // 에러 메시지를 서버 응답에 따라 다르게 표시
        const axiosError = error as AxiosError<any>;
        if (axiosError.response?.data?.exceptionCode === 'INVALID_PARAMETER') {
          alert(axiosError.response.data.message);
        } else {
          alert('로그인에 실패하였습니다. 다시 시도해주세요.');
        }
        // URL에서 코드 제거하고 이동
        window.history.replaceState({}, '', '/landing');
        navigate('/landing');
      },
    });
  } else {
    // code나 redirectUri가 없으면 바로 landing으로
    navigate('/landing');
  }
}, [navigate, loginWithKakao, checkUserCompletion]);

  if (isPending) return <LoadingSpinner />;
  if (isError) return <p>로그인 에러가 발생했습니다.</p>;

  return null;
};

export default OAuthCallbackPage;