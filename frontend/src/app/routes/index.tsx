// app/routes/index.tsx
import { createBrowserRouter } from 'react-router-dom';
import { Layout } from '@/shared/layouts/Layout';
import { PrivateRoute } from '@/app/routes/PrivateRoute';

// pages로 이동시킬 것
const LandingPage = () => <div>Landing Page</div>;

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
