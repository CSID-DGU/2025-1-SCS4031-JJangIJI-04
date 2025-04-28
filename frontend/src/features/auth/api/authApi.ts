import api from '@/lib/axios';

interface KakaoLoginResponse {
    accessToken: string;
    isSignedUp: boolean;
  }
  
  export const requestKakaoLogin = async (code: string): Promise<KakaoLoginResponse> => {
    const res = await api.post('/auth/kakao', { code });
    return res.data;
  };

