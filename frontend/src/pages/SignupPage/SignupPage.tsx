import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuthStore } from '@/features/auth/store/useAuthStore';
import { InputField } from '@/shared/ui/InputField';
import styled from 'styled-components';
import { CategorySelector } from '@/features/preferences/ui/CategorySelector';
import { FullWidthDivider } from '@/shared/ui/Divider/FullWidthDivider';

const SignupPage = () => {
  const navigate = useNavigate();
  const nicknameFromStore = useAuthStore((s) => s.nickname);
  const [nickname, setNickname] = useState('');
  const [nicknameValid, setNicknameValid] = useState(true);
  const [errorMessage, setErrorMessage] = useState('');
  const [selectedCategories, setSelectedCategories] = useState<string[]>([]);

  useEffect(() => {
    if (nicknameFromStore) {
      setNickname(nicknameFromStore);
    }
  }, [nicknameFromStore]);

  const handleNicknameChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value;
    setNickname(value);

    // 빈 값 체크
    if (value.trim() === '') {
      setNicknameValid(false);
      setErrorMessage('닉네임을 입력해주세요.');
      return;
    }

    // 공백 포함 체크
    if (/\s/.test(value)) {
      setNicknameValid(false);
      setErrorMessage('닉네임에는 공백을 포함할 수 없습니다.');
      return;
    }

    // 길이 체크 (영어, 한글 모두 동일하게)
    const length = [...value].length;
    if (length > 10) { 
      setNicknameValid(false);
      setErrorMessage('닉네임은 10자 이내여야 합니다.');
      return;
    }

    // 모든 검사 통과
    setNicknameValid(true);
    setErrorMessage('사용 가능한 닉네임입니다.');
};

  const handleSubmit = () => {
    const trimmed = nickname.trim();
    if (!nicknameValid || trimmed === '' || /\s/.test(trimmed)) {
      alert('닉네임을 확인해주세요. 공백은 사용할 수 없습니다.');
      return;
    }

    console.log('닉네임 제출:', trimmed);
  };

  return (
    <Container>
      <Title>사용하실 닉네임을 입력해주세요</Title>

      <InputField
        value={nickname}
        onChange={handleNicknameChange}
        placeholder="최대 10글자까지, 공백은 허용되지 않습니다."
        maxLength={18}
        error={errorMessage}
        isValid={nicknameValid}
      />

      <FullWidthDivider />

      <Title>선호하는 음식 카테고리를 선택해 주세요</Title>
      <CategorySelector
        selected={selectedCategories}
        onChange={setSelectedCategories}
      />  

      <SubmitButton onClick={handleSubmit}>한끼모아 시작하기</SubmitButton>
    </Container>
  );
};

export default SignupPage;

const Container = styled.div`
  padding: 24px;
  padding-top: 60px;
  min-height: 100vh;
  display: flex;
  flex-direction: column;
`;

const Title = styled.h2`
  font-size: 18px;
  font-weight: 600;
`;

const SubmitButton = styled.button`
  width: 100%;
  padding: 14px;
  background-color: #FF6701;
  color: #fff;
  font-weight: bold;
  font-size: 16px;
  border: none;
  border-radius: 8px;
  margin-top: 40px;
`;