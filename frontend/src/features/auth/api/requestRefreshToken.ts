import api from '@/lib/axios';

export const requestRefreshToken = async (): Promise<string> => {
  const res = await api.post('/auth/refresh', {});
  return res.data.accessToken;
};
