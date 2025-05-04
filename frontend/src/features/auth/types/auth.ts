export interface KakaoLoginResponse {
    accessToken: string;
    isSignedUp: boolean;
    nickname: string;
    imageUrl: string;
  }

export interface SignupRequest {
  nickname: string;
  category: string[];
}