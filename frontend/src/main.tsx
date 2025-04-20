import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import { GlobalStyle } from '@/app/styles/global';
import { App } from '@/app/App';

const root = createRoot(document.getElementById('root')!);
root.render(
  <StrictMode>
    <GlobalStyle />
    <App />
  </StrictMode>
);
