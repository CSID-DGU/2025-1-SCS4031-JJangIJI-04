import { router } from '@/app/routes/index.tsx';
import { RouterProvider } from 'react-router-dom';
import { ReactQueryProvider } from '@/app/providers/ReactQueryProvider';
import { ScrollToTop } from '@/shared/ui/ScrollToTop/ScrollToTop';

export const App = () => {
  return (
    <ReactQueryProvider>
      <ScrollToTop />
      <RouterProvider router={router} />
    </ReactQueryProvider>
  );
};
