import axios, { AxiosError, AxiosHeaders, InternalAxiosRequestConfig } from 'axios';
import { useAuthStore } from '@/features/auth/store/useAuthStore';
import { requestRefreshToken } from '@/features/auth/api/requestRefreshToken';

const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL,
  withCredentials: true,
});

// 요청 시 accessToken 자동 포함
api.interceptors.request.use((config: InternalAxiosRequestConfig) => {
  const token = useAuthStore.getState().accessToken;
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

// 401 응답 시 자동 refresh + 원래 요청 재시도
api.interceptors.response.use(
  (res) => res,
  async (err: AxiosError) => {
    const originalRequest = err.config;

    if (
      err.response?.status === 401 &&
      originalRequest &&
      !(originalRequest as any)._retry &&
      !useAuthStore.getState().isRefreshFailed  // 추가: 리프레시 실패 상태 체크
    ) {
      (originalRequest as any)._retry = true;

      try {
        const newAccessToken = await requestRefreshToken();
        useAuthStore.getState().setAccessToken(newAccessToken);

        if (originalRequest.headers) {
          const headers = originalRequest.headers as AxiosHeaders;
          headers.set('Authorization', `Bearer ${newAccessToken}`);
        }

        return api(originalRequest);
      } catch (refreshError) {
        useAuthStore.getState().clearAuth();
        useAuthStore.getState().setRefreshFailed(true);  // 추가: 리프레시 실패 상태 설정
        return Promise.reject(refreshError);
      }
    }

    return Promise.reject(err);
  }
);

export default api;

