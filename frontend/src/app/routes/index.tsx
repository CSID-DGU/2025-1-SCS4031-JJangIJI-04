import { createBrowserRouter } from 'react-router-dom';
import { Layout } from '@/shared/layouts/Layout';
import { PrivateRoute } from '@/app/routes/PrivateRoute';

import LandingPage from '@/pages/Landing/LandingPage';
import OAuthCallbackPage from '@/pages/OAuthCallbackPage/OAuthCallbackPage';
import SignupPage from '@/pages/SignupPage/SignupPage';
import MainPage from '@/pages/MainPage/MainPage';
import WeeklyGoalPage from '@/pages/WeeklyGoalPage/WeeklyGoalPage';

export const router = createBrowserRouter([
  //Footer 없는 공개 페이지
  {
    path: '/',
    element: <Layout hasFooter={false} />,
    children: [
      { index: true, element: <LandingPage /> },
      { path: 'landing', element: <LandingPage /> },
      { path: 'oauth/callback/kakao', element: <OAuthCallbackPage /> },
    ],
  },

  //Footer 없는 인증 필요 페이지
  {
    element: <PrivateRoute />,
    children: [
      {
        path: 'signup',
        element: <Layout hasFooter={false} />,
        children: [{ index: true, element: <SignupPage /> }],
      },
      {
        path: 'weeklygoal',
        element: <Layout hasFooter={false} />,
        children: [{ index: true, element: <WeeklyGoalPage /> }],
      },
    ],
  },

  //Footer 있는 인증 필요 페이지 (예: main)
  {
    element: <PrivateRoute />,
    children: [
      {
        path: 'main',
        element: <Layout hasFooter={true} />,
        children: [{ index: true, element: <MainPage /> }],
      },
    ],
  },
]);
