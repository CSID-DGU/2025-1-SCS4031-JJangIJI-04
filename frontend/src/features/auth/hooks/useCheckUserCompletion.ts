import { useNavigate } from 'react-router-dom';
import { getUserInfo } from '@/features/auth/api/authApi';
import { useAuthStore } from '@/features/auth/store/useAuthStore';

export const useCheckUserCompletion = () => {
  const navigate = useNavigate();

  return async () => {
    console.log('checkUserCompletion 시작');

    try {
      const userInfo = await getUserInfo();
      console.log('O 받은 유저 정보:', userInfo);

      if (!Array.isArray(userInfo.categories)) {
        console.log('! categories가 배열이 아님');
        navigate('/signup');
        return;
      }

      if (userInfo.categories.length === 0) {
        console.log('categories는 빈 배열 → /signup');
        navigate('/signup');
        return;
      }

      console.log('categories 있음 → /main');
      navigate('/main');
    } catch (error) {
      console.log('catch 실행됨 → /landing');
      useAuthStore.getState().clearAuth();
      navigate('/landing');
    }
  };
};