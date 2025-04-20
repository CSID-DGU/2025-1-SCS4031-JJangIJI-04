import { router } from '@/app/routes/index.tsx';
import { RouterProvider } from 'react-router-dom';

export const App = () => {
  return <RouterProvider router={router} />;
};
