import api from '@/lib/axios';
import { KakaoLoginResponse } from '@/features/auth/types/auth';
  
  export const requestKakaoLogin = async (code: string): Promise<KakaoLoginResponse> => {
    const res = await api.post('/auth/kakao', { code });
    return res.data;
  };

