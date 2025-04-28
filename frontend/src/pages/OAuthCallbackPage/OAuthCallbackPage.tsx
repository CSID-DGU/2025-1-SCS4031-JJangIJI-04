import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useKakaoLogin } from '@/features/auth/hooks/useKakaoLogin';
import { useAuthStore } from '@/features/auth/store/useAuthStore';

const OAuthCallbackPage = () => {
  const navigate = useNavigate();
  const { mutate: loginWithKakao, isPending, isError } = useKakaoLogin();
  const setAccessToken = useAuthStore((s) => s.setAccessToken);

  useEffect(() => {
    const code = new URL(window.location.href).searchParams.get('code');
    if (code) {
      loginWithKakao(code, {
        onSuccess: ({ accessToken, isSignedUp }) => {
          setAccessToken(accessToken);
          if (isSignedUp) {
            navigate('/main'); //로그인 처리 되었을 경우 이동 경로
          } else {
            navigate('/signup/extra'); //첫 로그인 시, 추가 정보 입력 페이지로 이동
          }
        },
        onError: () => alert('로그인 실패'),
      });
    }
  }, [loginWithKakao, navigate, setAccessToken]);

  if (isPending) return <p>로그인 처리 중입니다...</p>; //추후 로딩스피너로 변경
  if (isError) return <p>로그인 에러가 발생했습니다.</p>;

  return null;
};

export default OAuthCallbackPage;

