import { create } from 'zustand';

/**
 * Zustand 기반 전역 인증 상태 관리 훅
 * - accessToken 상태를 메모리에 저장
 * - 앱 초기화(isInitializing) 상태를 관리
 * - 로그인/로그아웃 흐름 제어에 사용
 */

interface AuthState {
  accessToken: string | null;   // 메모리에 저장할 accessToken
  isInitializing: boolean;  // 앱 초기 로딩 상태 (refresh 중인지 여부)
  setAccessToken: (token: string) => void;  // accessToken 저장 함수
  clearAuth: () => void;    // accessToken 초기화 (로그아웃/실패 등)
  setInitializing: (value: boolean) => void;    // 초기화 완료 여부 설정
}

export const useAuthStore = create<AuthState>((set) => ({
  accessToken: null,
  isInitializing: true,
  setAccessToken: (token) => set({ accessToken: token }),
  clearAuth: () => set({ accessToken: null }),
  setInitializing: (value) => set({ isInitializing: value }),
}));
