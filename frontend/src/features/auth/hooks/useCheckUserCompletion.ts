import { useNavigate } from 'react-router-dom';
import { getUserInfo } from '@/features/auth/api/authApi';
import { useAuthStore } from '../store/useAuthStore';

export const useCheckUserCompletion = () => {
  const navigate = useNavigate();
  
  const checkCompletion = async () => {
    try {
      const userInfo = await getUserInfo();
      
      if (!userInfo.category || userInfo.category.length === 0) {
        // 카테고리가 없으면 회원가입 페이지로
        navigate('/signup');
      } else {
        // 카테고리가 있으면 메인 페이지로
        navigate('/main');
      }
    } catch (error) {
      console.error('유저 정보 확인 중 에러 발생:', error);
      // 에러 발생 시 랜딩 페이지로
      useAuthStore.getState().clearAuth();
      navigate('/landing');
    }
  };

  return checkCompletion;
};