import styled from 'styled-components';
import { ExpandableCalendar } from '@/features/calendar/ui/ExpandableCalendar';
import type { DailyExpenseStatus } from '@/features/calendar/types/expense';
import { GaugeChart } from '@/features/spendingStatus/ui/GaugeChart';
import { FullWidthDivider } from '@/shared/ui/Divider/FullWidthDivider';

const MainPage = () => {
  const dummyData: DailyExpenseStatus[] = [
    { date: '2025-05-05', totalExpense: 8000, status: 'GOOD' },
    { date: '2025-05-06', totalExpense: 13000, status: 'NOT_BAD' },
    { date: '2025-05-07', totalExpense: 188000, status: 'BAD' },
  ];

  return (
    <Container>
      <ExpandableCalendar dailyStatusList={dummyData} />
      <GaugeChart total={84000} spent={28000} />
      <FullWidthDivider />
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