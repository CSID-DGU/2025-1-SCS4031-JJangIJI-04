import styled from 'styled-components';

export const KakaoLoginButton = () => {
  return (
    <ButtonWrapper>
      <ButtonImage
        src="/public/images/kakao-login-large.png"
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
