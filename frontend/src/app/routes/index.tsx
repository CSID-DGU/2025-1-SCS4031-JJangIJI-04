// app/routes/index.tsx
import { createBrowserRouter } from 'react-router-dom';
import { LandingPage } from '@/pages/Landing/LandingPage';
//import { Layout } from '@/shared/layouts/Layout';
//import { PrivateRoute } from '@/app/routes/PrivateRoute';

export const router = createBrowserRouter([
  // Public Routes
  {
    path: '/',
    element: <LandingPage />,
  },
  {
    path: '/landing',
    element: <LandingPage />,
  },

  // Private Routes (로그인 필요)
  /*
  {
    element: <PrivateRoute />,
    children: [
      {
        element: <Layout />,
        children: [
          {
            path: '/main',
            element: <MainPage />,
          },
        ],
      },
    ],
  },
  */
]);
