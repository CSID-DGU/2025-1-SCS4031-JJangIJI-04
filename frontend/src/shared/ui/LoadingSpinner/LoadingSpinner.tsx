import { MoonLoader } from 'react-spinners';
import styled from 'styled-components';

interface LoadingSpinnerProps {
  message?: string;
  size?: number;
  color?: string;
}

export const LoadingSpinner = ({
  message = '잠시만 기다려주세요',
  size = 60,
  color = '#FF6701',
}: LoadingSpinnerProps) => {
  return (
    <Wrapper>
      <Message>{message}</Message>
      <MoonLoader size={size} color={color} speedMultiplier={0.5} />
    </Wrapper>
  );
};

const Wrapper = styled.div`
  width: 100%;
  height: 100vh;
  min-height: 400px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background: #fff;
`;

const Message = styled.p`
  font-size: 16px;
  font-weight: bold;
  color: #ff6701;
  margin-bottom: 16px;
`;
