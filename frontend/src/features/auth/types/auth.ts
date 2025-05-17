export interface KakaoLoginResponse {
    accessToken: string;
    nickname: string;
    imageUrl: string;
  }

export interface SignupRequest {
  nickname: string;
  categories: string[];
}

export interface UserInfo {
  userId: string;
  nickname: string;
  imageUrl: string;
  categories: string[] | null; 
}