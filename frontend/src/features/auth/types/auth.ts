export interface KakaoLoginResponse {
    accessToken: string;
    nickname: string;
    imageUrl: string;
  }

export interface SignupRequest {
  nickname: string;
  category: string[];
}

export interface UserInfo {
  userId: string;
  nickname: string;
  imageUrl: string;
  category: string[] | null; 
}