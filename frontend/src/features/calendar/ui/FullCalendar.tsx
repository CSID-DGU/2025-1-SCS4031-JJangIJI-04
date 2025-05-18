import { useState } from 'react';
import styled from 'styled-components';
import {
  format,
  startOfMonth,
  endOfMonth,
  eachDayOfInterval,
  getDay,
  addMonths,
  subMonths,
} from 'date-fns';
import type { DailyExpenseStatus } from '@/features/calendar/types/expense';

import GoodIcon from '@/assets/icons/good-icon.svg?react';
import NormalIcon from '@/assets/icons/normal-icon.svg?react';
import DangerousIcon from '@/assets/icons/dangerous-icon.svg?react';
import BasicIcon from '@/assets/icons/basic-icon.svg?react';
import BeforeIcon from '@/assets/icons/navigate-before.svg?react';
import AfterIcon from '@/assets/icons/navigate-after.svg?react';
import { formatExpenseAmount } from '@/lib/number/formatExpenseAmount';

interface Props {
  dailyStatusList: DailyExpenseStatus[];
  onCollapse?: () => void;
  onDateSelect?: (dateStr: string) => void;
}

export const FullCalendar = ({ dailyStatusList, onCollapse, onDateSelect }: Props) => {
  const [currentDate, setCurrentDate] = useState(new Date());

  const start = startOfMonth(currentDate);
  const end = endOfMonth(currentDate);
  const days = eachDayOfInterval({ start, end });
  const firstDayIndex = getDay(start);

  const handlePrevMonth = () => setCurrentDate(prev => subMonths(prev, 1));
  const handleNextMonth = () => setCurrentDate(prev => addMonths(prev, 1));

  const today = new Date();
  const statusMap = Object.fromEntries(
    dailyStatusList.map((d) => [d.date, d])
  );

  const getIconByStatus = (status?: 'GOOD' | 'NOT_BAD' | 'BAD') => {
    switch (status) {
      case 'GOOD': return <GoodIcon />;
      case 'NOT_BAD': return <NormalIcon />;
      case 'BAD': return <DangerousIcon />;
      default: return <BasicIcon />;
    }
  };

  return (
    <Wrapper>
      <Header>
        <IconButton onClick={handlePrevMonth}>
          <BeforeIcon />
        </IconButton>
        <MonthText>{format(currentDate, 'yyyy\uB144 M\uC6D4')}</MonthText>
        <IconButton onClick={handleNextMonth}>
          <AfterIcon />
        </IconButton>
      </Header>
      <Divider />
      <Weekdays>
        {['일', '월', '화', '수', '목', '금', '토'].map((d) => (
          <Weekday key={d}>{d}</Weekday>
        ))}
      </Weekdays>
      <Grid>
        {Array(firstDayIndex).fill(null).map((_, i) => <Empty key={`empty-${i}`} />)}
        {days.map((date) => {
        const dateStr = format(date, 'yyyy-MM-dd');
        const dayData = statusMap[dateStr];
        const isFuture = date > today;

        const icon = getIconByStatus(isFuture ? undefined : dayData?.status);
        const amount = dayData?.totalExpense;
        const displayAmount =
        !isFuture && amount && amount > 0
          ? formatExpenseAmount(amount)
        : '-';

        return (
          <DayCell
            key={dateStr}
            $isToday={dateStr === format(today, 'yyyy-MM-dd')}
            onClick={() => onDateSelect?.(dateStr)}
          >
            <div className="date">{format(date, 'd')}</div>
            <div className="icon">{icon}</div>
            <div className="amount">{displayAmount}</div>
          </DayCell>
        );
    })}
      </Grid>
      <HandleWrapper>
        <Handle onClick={onCollapse} />
      </HandleWrapper>
    </Wrapper>
  );
};

const Wrapper = styled.div`
  padding: 16px 16px 24px;
  max-width: 420px;
  margin: 0 auto;
  background-color: #fff;
  position: relative;
  z-index: 10;
  overflow: hidden;
  box-sizing: border-box;
`;

const Header = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 10px;
  padding: 8px 0;
`;

const IconButton = styled.button`
  background: none;
  border: none;
  padding: 4px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;

  svg {
    width: 20px;
    height: 20px;
    fill: #808080;
  }
`;

const MonthText = styled.h2`
  font-size: 16px;
  font-weight: bold;
`;

const Divider = styled.div`
  width: calc(100% - 32px);
  height: 1px;
  background-color: #808080;
  margin: 10px auto 17px;
`;

const Weekdays = styled.div`
  width: calc(100% - 32px);
  margin: 0 auto 6px;
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  column-gap: 6px;
  font-size: 13px;
  color: #202632;
`;

const Weekday = styled.div`
  text-align: center;
  font-weight: 500;
`;

const Grid = styled.div`
  width: calc(100% - 32px);
  margin: 0 auto;
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  grid-template-rows: repeat(6, 72px);
  row-gap: 10px;
  column-gap: 6px;
`;

const Empty = styled.div`
  height: 72px;
`;

const DayCell = styled.div<{ $isToday?: boolean }>`
  height: 72px;
  padding: 6px 4px;
  background: transparent;
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  .date {
    font-size: 12px;
    font-weight: bold;
    margin-bottom: 4px;
  }
  .icon {
    display: inline-flex;
    align-items: center;
    justify-content: center;
    width: 24px;
    height: 24px;
    padding: 6px;
    box-sizing: content-box;
    margin: 2px 0;
    position: relative;  // 추가

    ${({ $isToday }) => $isToday && `
      &::after { 
        content: '';
        position: absolute;
        width: 100%;
        height: 100%;
        border: 2.5px solid #FD6918;
        border-radius: 50%;
        transform: scale(0.9);
      }
    `}

    svg {
      width: 24px;
      height: 24px;
    }
  }
  .amount {
    font-size: 10px;
    font-weight: 500;
    color: #202632;
    margin-top: 1px;
  }
`;

const HandleWrapper = styled.div`
  display: flex;
  justify-content: center;
  margin-top: 16px;
`;

const Handle = styled.div`
  width: 40px;
  height: 3px;
  background-color: #aaa;
  border-radius: 999px;
  cursor: pointer;
`;