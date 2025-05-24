import api from '@/lib/axios';

export const requestRefreshToken = async (): Promise<string> => {
  try {
    const res = await api.post('/auth/refresh', {});
    return res.data.accessToken;
  } catch (error) {
    // 401이나 다른 에러 발생 시 명시적으로 에러를 던져서
    // useAuthInit에서 확실하게 catch할 수 있도록 함
    throw error;
  }
};