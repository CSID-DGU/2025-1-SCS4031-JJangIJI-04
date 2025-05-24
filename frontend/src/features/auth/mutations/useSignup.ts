import { useMutation } from '@tanstack/react-query';
import { requestSignup } from '@/features/auth/api/authApi';
import { useNavigate } from 'react-router-dom';

export const useSignup = () => {
  const navigate = useNavigate();

  return useMutation({
    mutationFn: requestSignup,
    onSuccess: () => {
      navigate('/main');
    },
    onError: () => {
      alert('회원가입에 실패했습니다. 다시 시도해주세요.');
    },
  });
};
