import styled from 'styled-components';
import { ExpandableCalendar } from '@/features/calendar/ui/ExpandableCalendar';
import type { DailyExpenseStatus } from '@/features/calendar/types/expense';
import { GaugeChart } from '@/features/spendingStatus/ui/GaugeChart';
import { FullWidthDivider } from '@/shared/ui/Divider/FullWidthDivider';
import { DailyExpenseSection } from '@/features/spendingStatus/ui/DailyExpenseSection';

const MainPage = () => {
  const dummyData: DailyExpenseStatus[] = [
    { date: '2025-05-05', totalExpense: 8000, status: 'GOOD' },
    { date: '2025-05-06', totalExpense: 13000, status: 'NOT_BAD' },
    { date: '2025-05-07', totalExpense: 188000, status: 'BAD' },
  ];

  const dummyExpenses = [
    {
      date: '2025-05-19',
      records: [
        {
          storeName: '오이드킨',
          category: '규동',
          amount: 12000,
          memo: '가성비 좋고 맛도 적당한 식당 찾아서 기분 좋다 뱅',
          reactions: { 1: 4, 2: 2 },
        },
      ],
    },
    {
      date: '2025-05-18',
      records: [
        {
          storeName: '맘스터치',
          category: '버거',
          amount: 8400,
          memo: '간단히 때움',
          reactions: { 3: 1 },
        },
        {
          storeName: '스타벅스',
          category: '카페',
          amount: 6200,
          memo: '기분전환하러 커피',
          reactions: { 1: 1, 4: 1 },
        },
      ],
    },
  ];

  return (
    <Container>
      <ExpandableCalendar dailyStatusList={dummyData} />
      <GaugeChart total={84000} spent={28000} />
      <FullWidthDivider />
      
      {dummyExpenses.map((day) => (
      <DailyExpenseSection key={day.date} {...day} />
      ))}

      {/* 이후: 게이지 영역, 지출 목록 등 붙일 자리 */}
    </Container>
  );
};

export default MainPage;

const Container = styled.div`
  background-color: #fff;
  min-height: 100vh;
  width: 100%;
  padding: 24px;
  padding-top: var(--safe-area-top);
  box-sizing: border-box;
`;