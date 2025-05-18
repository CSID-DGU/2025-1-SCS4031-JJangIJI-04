import styled from 'styled-components';
import { useState } from 'react';
import { format, parseISO } from 'date-fns';
import { useAuthStore } from '@/features/auth/store/useAuthStore';
import { ExpandableCalendar } from '@/features/calendar/ui/ExpandableCalendar';
import type { DailyExpenseStatus } from '@/features/calendar/types/expense';
import { GaugeChart } from '@/features/spendingStatus/ui/GaugeChart';
import { FullWidthDivider } from '@/shared/ui/Divider/FullWidthDivider';
import { ExpenseCard } from '@/features/spendingStatus/ui/ExpenseCard';
import FileIcon from '@/assets/icons/file.svg?react';

const MainPage = () => {
  const getToday = () => format(new Date(), 'yyyy-MM-dd');
  const [selectedDate, setSelectedDate] = useState(getToday());

  const dummyData: DailyExpenseStatus[] = [
    { date: '2025-05-05', totalExpense: 8000, status: 'GOOD' },
    { date: '2025-05-06', totalExpense: 13000, status: 'NOT_BAD' },
    { date: '2025-05-07', totalExpense: 188000, status: 'BAD' },
  ];

  const dummyExpenses = [
    {
      date: '2025-05-20',
      records: [
        {
          id: 1,
          storeName: '아비꼬',
          category: '돈까스 카레',
          amount: 8900,
          memo: '돈까스카레 맛있었음',
          reactions: { 1: 2, 3: 1 },
        },
      ],
    },
    {
      date: '2025-05-21',
      records: [
        {
          id: 2,
          storeName: '이삭토스트',
          category: '햄치즈토스트',
          amount: 4500,
          memo: '맛있당',
          reactions: { 2: 1 },
        },
        {
          id: 3,
          storeName: '필동면옥',
          category: '냉면',
          amount: 15000,
          memo: '그냥저냥 평냉',
          reactions: { 2: 1 },
        },
      ],
    },
  ];

  const nickname = useAuthStore((s) => s.nickname ?? '한끼모아');
  const selectedExpense = dummyExpenses.find((e) => e.date === selectedDate);
  const records = selectedExpense?.records ?? [];

  return (
    <Container>
      <ExpandableCalendar dailyStatusList={dummyData} onDateSelect={setSelectedDate} selectedDate={selectedDate} />
      <GaugeChart total={84000} spent={28000} />
      <FullWidthDivider />

      <CenteredTextBlock>
        <DateText>{format(parseISO(selectedDate), 'yyyy년 M월 d일')}</DateText>
        <TitleText>{nickname}님의 외식비 지출 내역 {records.length}건</TitleText>
          {records.length === 0 && (
          <NoDataBlock>
            <FileIconWrapper>
              <FileIcon />
            </FileIconWrapper>
          <NoDataText>아직 지출 기록이 없어요</NoDataText>
        </NoDataBlock>
        )}
      </CenteredTextBlock>

      {records.map((record) => (
        <ExpenseCard key={record.id} {...record} />
      ))}
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

const CenteredTextBlock = styled.div`
  text-align: center;
  margin-top: 32px;
`;

const DateText = styled.h2`
  font-size: 12px;
  font-weight: 700;
  margin-top: 32px;
  margin-bottom: 4px;
  color: #808080;
`;

const TitleText = styled.div`
  font-size: 14px;
  font-weight: 700;
  color: #202632;
  margin-bottom: 12px;
`;

const NoDataBlock = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 24px;  // 텍스트와의 여백
  gap: 8px;
`;

const FileIconWrapper = styled.div`
  margin-top: 12px;
  svg {
    width: 50px;
    height: 50px;
    opacity: 0.4;
  }
`;

const NoDataText = styled.div`
  font-size: 13px;
  color: #808080;
  font-weight: 700;
`;