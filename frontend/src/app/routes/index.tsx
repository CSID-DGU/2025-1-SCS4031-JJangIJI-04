import { createBrowserRouter } from 'react-router-dom';
import { Layout } from '@/shared/layouts/Layout';
import { PrivateRoute } from '@/app/routes/PrivateRoute';
import LandingPage from '@/pages/Landing/LandingPage';
import OAuthCallbackPage from '@/pages/OAuthCallbackPage/OAuthCallbackPage';
import SignupPage from '@/pages/SignupPage/SignupPage';
// import MainPage from '@/pages/MainPage/MainPage';
import { RestaurantsPage } from '@/pages/Restaurants/RestaurantsPage';
import { RestaurantDetailPage } from '@/pages/Restaurants/RestaurantDetailPage';

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

  {
    element: <PrivateRoute />,
    children: [
      {
        path: 'restaurants',
        element: <Layout hasFooter={true} />,
        children: [{ index: true, element: <RestaurantsPage /> }],
      },
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
        path: 'restaurants/:id',
        element: <Layout hasFooter={false} />,
        children: [{ index: true, element: <RestaurantDetailPage /> }],
      },
    ],
  },

  /*Footer 있는 인증 필요 페이지 (예: main)
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
  */
]);
