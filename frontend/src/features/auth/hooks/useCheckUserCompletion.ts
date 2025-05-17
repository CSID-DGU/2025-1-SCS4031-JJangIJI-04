import { useNavigate } from 'react-router-dom';
import { getUserInfo } from '@/features/auth/api/authApi';
import { useAuthStore } from '@/features/auth/store/useAuthStore';

export const useCheckUserCompletion = () => {
  const navigate = useNavigate();

  return async () => {
    try {
      const userInfo = await getUserInfo();

      if (!Array.isArray(userInfo.categories) || userInfo.categories.length === 0) {
        navigate('/signup'); // 여기서만 라우팅
        return;
      }

      navigate('/main');
    } catch (error) {
      console.error('유저 정보 확인 실패:', error);
      useAuthStore.getState().clearAuth();
      navigate('/landing');
    }
  };
};