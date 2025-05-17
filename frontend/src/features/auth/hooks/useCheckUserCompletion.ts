import { useNavigate } from 'react-router-dom';
import { getUserInfo } from '@/features/auth/api/authApi';
import { useAuthStore } from '../store/useAuthStore';

export const useCheckUserCompletion = () => {
  const navigate = useNavigate();
  
  const checkCompletion = async () => {
    try {
      const userInfo = await getUserInfo();
      console.log('받은 유저 정보:', userInfo);
      console.log('categories 확인:', userInfo.categories); 
      
      if (!userInfo.categories || userInfo.categories.length === 0) {
        console.log('카테고리 없음, 회원가입 페이지로 이동');  
        navigate('/signup');
        return;
      } else {
        console.log('카테고리 있음, 메인 페이지로 이동');  
        navigate('/main');
      }
    } catch (error) {
      console.error('유저 정보 확인 중 에러 발생:', error);
      useAuthStore.getState().clearAuth();
      navigate('/landing');
    }
  };

  return checkCompletion;
};