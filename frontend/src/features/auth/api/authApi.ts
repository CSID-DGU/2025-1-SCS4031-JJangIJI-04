import api from '@/lib/axios';

export const requestKakaoLogin = async (code: string): Promise<string> => {
  const res = await api.post('/auth/kakao', { code });
  return res.data.accessToken;
};

