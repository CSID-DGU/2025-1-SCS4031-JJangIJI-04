import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuthStore } from '@/features/auth/store/useAuthStore';

const SignupPage = () => {
  const navigate = useNavigate();
  const nicknameFromStore = useAuthStore((s) => s.nickname);
  const [nickname, setNickname] = useState('');
  const [nicknameValid, setNicknameValid] = useState(true);

  useEffect(() => {
    // 카카오에서 받아온 nickname을 초기값으로 설정
    if (nicknameFromStore) {
      setNickname(nicknameFromStore);
    }
  }, [nicknameFromStore]);

  const handleNicknameChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value;
    setNickname(value);
    setNicknameValid(value.length <= 10);
  };

  const handleSubmit = () => {
    if (!nicknameValid || nickname.trim() === '') {
      alert('닉네임을 확인해주세요.');
      return;
    }

    // TODO: 카테고리 정보와 함께 API 요청할 예정
    console.log('닉네임 제출:', nickname);
  };

  return (
    <div style={{ padding: '24px' }}>
      <h2 style={{ fontSize: '18px', fontWeight: 600 }}>사용하실 닉네임을 입력해주세요</h2>

      <input
        type="text"
        value={nickname}
        onChange={handleNicknameChange}
        placeholder="닉네임"
        maxLength={10}
        style={{
          width: '100%',
          padding: '12px',
          fontSize: '16px',
          border: '1px solid #ddd',
          borderRadius: '8px',
          marginTop: '12px',
        }}
      />

      <p style={{ fontSize: '14px', marginTop: '4px', color: nicknameValid ? 'green' : 'red' }}>
        {nicknameValid ? '사용 가능한 닉네임입니다.' : '10자 이내로 입력해주세요.'}
      </p>

      {/* 이 아래에 카테고리 선택 UI */}

      <button
        onClick={handleSubmit}
        style={{
          marginTop: '40px',
          width: '100%',
          padding: '14px',
          backgroundColor: '#FF7A00',
          color: '#fff',
          fontWeight: 'bold',
          fontSize: '16px',
          border: 'none',
          borderRadius: '8px',
        }}
      >
        한끼모아 시작하기
      </button>
    </div>
  );
};

export default SignupPage;
