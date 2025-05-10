import { MiniCalendar } from '@/features/calendar/ui/MiniCalendar';
import styled from 'styled-components';
import type { DailyExpenseStatus } from '@/features/calendar/types/expense';

const MainPage = () => {
  const dummyData: DailyExpenseStatus[] = [
    { date: '2025-05-05', totalExpense: 8000, status: 'GOOD' },
    { date: '2025-05-06', totalExpense: 13000, status: 'NOT_BAD' },
    { date: '2025-05-07', totalExpense: 188000, status: 'BAD' },
  ];

  const handleExpand = () => {
    console.log('전체 달력 열기!');
  };

  return (
    <Container>
      <FullWidthWrapper>
        <MiniCalendar dailyStatusList={dummyData} onExpand={handleExpand} />
      </FullWidthWrapper>

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
  box-sizing: border-box;
`;

const FullWidthWrapper = styled.div`
  width: 100vw;
  position: relative;
  left: 50%;
  right: 50%;
  margin-left: -50vw;
  margin-right: -50vw;
  margin-top: -12px;
`;