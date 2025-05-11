import { create } from 'zustand';

/**
 * Zustand 기반 전역 인증 상태 관리 훅
 * - accessToken 상태를 메모리에 저장
 * - 앱 초기화(isInitializing) 상태를 관리
 * - nickname, profileImage는 카카오 로그인 후 사용자 정보 초기값으로 활용됨
 */

interface AuthState {
  accessToken: string | null; // 메모리에 저장할 accessToken (요청 시 Authorization 헤더에 사용)
  nickname: string | null;  // 카카오 로그인 시 받은 사용자 닉네임 (회원가입 폼의 초기값으로 사용)
  profileImage: string | null; // 사용자 프로필 이미지 URL (커뮤니티, 마이페이지 등에 활용)
  isInitializing: boolean;  // 앱 초기 로딩 상태 (refreshToken으로 accessToken 재발급 중인지 여부)
  isRefreshFailed: boolean;  // 리프레시 실패 상태
  setAccessToken: (token: string) => void;  // accessToken을 메모리에 저장하는 함수
  setNickname: (nickname: string) => void;  // nickname을 저장하는 함수
  setProfileImage: (url: string) => void; // profileImage(URL)를 저장하는 함수
  clearAuth: () => void;  // 인증 상태 초기화 함수 (로그아웃 또는 에러 발생 시 호출)
  setInitializing: (value: boolean) => void;  // isInitializing 값을 설정하는 함수 (초기화 완료 시 false로 설정)
  setRefreshFailed: (value: boolean) => void;  // 리프레시 실패 상태를 설정하는 함수
}

export const useAuthStore = create<AuthState>((set) => ({
  accessToken: null,
  nickname: null,
  profileImage: null,
  isInitializing: true,
  setAccessToken: (token) => set({ accessToken: token }),
  setNickname: (nickname) => set({ nickname }),
  setProfileImage: (url) => set({ profileImage: url }),
  clearAuth: () =>
    set({
      accessToken: null,
      nickname: null,
      profileImage: null,
      isRefreshFailed: false,
    }),
  setInitializing: (value) => set({ isInitializing: value }),
  isRefreshFailed: false,
  setRefreshFailed: (value: boolean) => set({ isRefreshFailed: value }),
}));