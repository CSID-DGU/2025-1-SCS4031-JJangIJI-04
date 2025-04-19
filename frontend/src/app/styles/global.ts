import { createGlobalStyle } from 'styled-components';

export const GlobalStyle = createGlobalStyle`
  :root {
    /* 색상 */
    --background-color: #f5f5f5;
    --content-background: #ffffff;
    --shadow-color: rgba(0, 0, 0, 0.1);

    /* 화면 크기 */ 
    --max-width: 390px;
    --max-height: 844px;
    --page-padding: 16px;
    --min-width: 320px;
    --footer-height: 64px;     
    
    /* Safe 구역 */
    --safe-area-top: env(safe-area-inset-top, 0px); 
    --safe-area-bottom: env(safe-area-inset-bottom, 0px);

    /* 폰트 사이즈 */
    --font-size-xs: 12px;
    --font-size-sm: 14px;
    --font-size-md: 16px;
    --font-size-lg: 18px;
    --font-size-xl: 20px;
  }

  * {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
    -webkit-tap-highlight-color: transparent;
  }

  html {
    font-size: 16px;
    -webkit-text-size-adjust: none;
    text-size-adjust: none;
  }

  html, body {
    width: 100%;
    height: 100%;
    background-color: var(--background-color);
    overflow: hidden;
    font-family: 'Noto Sans KR', sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    word-break: keep-all;
    letter-spacing: -0.3px;
  }

  body {
    display: flex;
    justify-content: center;
    align-items: center;
  }

  #root {
    width: var(--max-width);
    height: var(--max-height);
    background-color: var(--content-background);
    position: relative;
    display: flex;
    flex-direction: column;
    overflow-y: auto;
    overflow-x: hidden;
    -webkit-overflow-scrolling: touch;
    font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
    scrollbar-width: none;

    &::-webkit-scrollbar {
      display: none;
    }
  }

     /* 메인 컨텐츠 영역 */
    .app-content {
      flex: 1;
      overflow-y: auto;
      -webkit-overflow-scrolling: touch;
      padding: var(--page-padding);
      padding-top: calc(var(--safe-area-top) + var(--page-padding));
      padding-bottom: calc(var(--footer-height) + var(--safe-area-bottom) + var(--page-padding));  // 네비게이션   영역만큼 여백
  }
  
    /* 하단 네비게이션 */
    .app-footer {
      position: fixed;  // 항상 하단에 고정
      bottom: 0;
      left: 0;
      right: 0;
      height: calc(var(--footer-height) + var(--safe-area-bottom));
      padding-bottom: var(--safe-area-bottom);
      background: var(--content-background);
      z-index: 100;
  }

  @media screen and (max-width: 390px) {
    #root {
      width: 100%;
      min-width: var(--min-width);
      height: 100%;
      box-shadow: none;
    }
  }

  @media screen and (max-height: 844px) {
    #root {
      height: 100%;
      min-height: 100%;
    }
  }
`;
