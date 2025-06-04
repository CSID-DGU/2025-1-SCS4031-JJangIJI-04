import styled from 'styled-components';

export const KakaoLoginButton = () => {
  const REST_API_KEY = import.meta.env.VITE_KAKAO_REST_API_KEY;
  const REDIRECT_URI = import.meta.env.VITE_KAKAO_REDIRECT_URI;
  const REDIRECT_URI_WWW = import.meta.env.VITE_KAKAO_REDIRECT_URI_WWW;

  const KAKAO_AUTH_URL = `https://kauth.kakao.com/oauth/authorize?client_id=${REST_API_KEY}&redirect_uri=${window.location.hostname.startsWith('www.') ? REDIRECT_URI_WWW : REDIRECT_URI}&response_type=code`;

  const handleLogin = () => {
    window.location.href = KAKAO_AUTH_URL;
  };

  return (
    <ButtonWrapper onClick={handleLogin}>
      <ButtonImage
        src="/icons/kakao-login-button.svg"
        alt="카카오 계정으로 로그인"
      />
    </ButtonWrapper>
  );
};

const ButtonWrapper = styled.button`
  width: 100%;
  max-width: 300px;
  border: none;
  padding: 0;
  background: none;
  cursor: pointer;

  &:hover {
    opacity: 0.9;
  }

  &:active {
    transform: scale(0.98);
  }
`;

const ButtonImage = styled.img`
  width: 100%;
  height: auto;
  display: block;
`;
