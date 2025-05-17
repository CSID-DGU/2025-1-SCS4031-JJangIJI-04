import { useNavigate } from 'react-router-dom';
import { getUserInfo } from '@/features/auth/api/authApi';
import { useAuthStore } from '@/features/auth/store/useAuthStore';

export const useCheckUserCompletion = () => {
  const navigate = useNavigate();

  return async (forcedError?: boolean) => {
    if (forcedError) {
      console.error('로그인 실패 → /landing');
      useAuthStore.getState().clearAuth();
      navigate('/landing');
      return;
    }

    try {
      console.log('checkUserCompletion 시작');
      const userInfo = await getUserInfo();
      console.log('받은 유저 정보:', userInfo);

      if (!Array.isArray(userInfo.categories) || userInfo.categories.length === 0) {
        console.log('categories는 빈 배열 → /signup');
        navigate('/signup');
        return;
      }

      console.log('categories 있음 → /main');
      navigate('/main');
    } catch (error) {
      console.error('유저 정보 확인 실패 → /landing');
      useAuthStore.getState().clearAuth();
      navigate('/landing');
    }
  };
};