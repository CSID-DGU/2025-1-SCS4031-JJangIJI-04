import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import '@/app/styles'; // 전역 스타일 import
import { App } from '@/app';

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <App />
  </StrictMode>
);
