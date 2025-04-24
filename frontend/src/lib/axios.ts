import axios from 'axios';

const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL, // env 기반 경로 분기
  withCredentials: true,
});

export default api;
