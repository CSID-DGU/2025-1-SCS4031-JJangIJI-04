import { startOfWeek, endOfWeek, format } from 'date-fns';

export const getCurrentWeek = () => {
  const today = new Date();

  const start = startOfWeek(today, { weekStartsOn: 0 });    // 일요일
  const end = endOfWeek(today, { weekStartsOn: 0 });    // 토요일

  return {
    startDate: format(start, 'yyyy-MM-dd'),
    endDate: format(end, 'yyyy-MM-dd'),
  };
};

