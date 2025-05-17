import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuthStore } from '@/features/auth/store/useAuthStore';
import { InputField } from '@/shared/ui/InputField';
import styled from 'styled-components';
import { CategorySelector } from '@/features/preferences/ui/CategorySelector';
import { FullWidthDivider } from '@/shared/ui/Divider/FullWidthDivider';
import { useSignup } from '@/features/auth/mutations/useSignup';
import { CATEGORY_LIST } from '@/features/preferences/ui/CategorySelector';

const SignupPage = () => {
  const navigate = useNavigate();

  
  const nicknameFromStore = useAuthStore((s) => s.nickname);
  const categoriesFromStore = useAuthStore((s) => s.categories);
  const [nickname, setNickname] = useState('');
  const [nicknameValid, setNicknameValid] = useState(true);
  const [errorMessage, setErrorMessage] = useState('');
  const [selectedCategories, setSelectedCategories] = useState<number[]>([]);
  const { mutate: signup } = useSignup();
  const { setNickname: setNicknameToStore, setCategories } = useAuthStore();
  

  // 이미 가입된 유저면 /main 으로 강제 이동
  useEffect(() => {
    if (Array.isArray(categoriesFromStore) && categoriesFromStore.length > 0) {
      navigate('/main');
    }
  }, [categoriesFromStore, navigate]);

  useEffect(() => {
    if (nicknameFromStore) {
      setNickname(nicknameFromStore);
    }
  }, [nicknameFromStore]);

  const isFormValid = nicknameValid && nickname.trim() !== '' && selectedCategories.length > 0;

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

  if (selectedCategories.length === 0) {
    alert('최소 한 개 이상의 카테고리를 선택해주세요.');
    return;
  }

    signup(
      {
        nickname: trimmed,
        categories: selectedCategories,
      },
      {
        onSuccess: () => {
          setNicknameToStore(trimmed);
          setCategories(
            CATEGORY_LIST.filter((cat) => selectedCategories.includes(cat.value)).map((cat) => ({
              categoryId: cat.value,
              name: cat.label,
            }))
          );
          navigate('/main');
        },
      }
    );
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

      <SubmitButton onClick={handleSubmit} disabled={!isFormValid}>한끼모아 시작하기</SubmitButton>
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

const SubmitButton = styled.button<{ disabled?: boolean }>`
  width: 100%;
  padding: 14px;
  font-weight: bold;
  font-size: 16px;
  border: none;
  border-radius: 8px;
  margin-top: 40px;
  transition: transform 0.2s ease, box-shadow 0.2s ease;
  cursor: ${({ disabled }) => (disabled ? 'not-allowed' : 'pointer')};
  background-color: ${({ disabled }) => (disabled ? '#ccc' : '#FF6701')};
  color: #fff;

  &:hover {
    ${({ disabled }) =>
      !disabled &&
      `
      transform: scale(1.02);
      box-shadow: 0 6px 12px rgba(255, 122, 1, 0.3);
    `}
  }

  &:active {
    ${({ disabled }) =>
      !disabled &&
      `
      transform: scale(0.98);
      box-shadow: 0 2px 6px rgba(255, 122, 1, 0.2);
    `}
  }
`;

