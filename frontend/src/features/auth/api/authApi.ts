import api from '@/lib/axios';
import { SignupRequest, KakaoLoginResponse } from '@/features/auth/types/auth';

export const requestKakaoLogin = async (code: string): Promise<KakaoLoginResponse> => {
  const res = await api.post('/auth/kakao', { code });

  const { accessToken, isSignedUp, nickname, image_url } = res.data;

  return {
    accessToken,
    isSignedUp,
    nickname,
    imageUrl: image_url, // snake_case → camelCase로 변환
  };
};

export const requestSignup = async (data: SignupRequest): Promise<void> => {
  await api.post('/auth/signup', data);
};