import { motion, AnimatePresence } from 'framer-motion';
import { useState, useEffect } from 'react';
import styled from 'styled-components';
import { MiniCalendar } from '@/features/calendar/ui/MiniCalendar';
import { FullCalendar } from '@/features/calendar/ui/FullCalendar';
import { useMonthlyExpenseStatus } from '@/features/calendar/api/useMonthlyExpenseStatus';
import { parseISO } from 'date-fns';

export const ExpandableCalendar = ({
  userId,
  onDateSelect,
  selectedDate,
}: {
  userId: number;
  onDateSelect?: (date: string) => void;
  selectedDate?: string;
}) => {
  const [isExpanded, setIsExpanded] = useState(false);
  const [currentDate, setCurrentDate] = useState(new Date());

  // selectedDate가 변경될 때 currentDate를 동기화
  useEffect(() => {
    if (selectedDate) {
      setCurrentDate(parseISO(selectedDate));
    }
  }, [selectedDate]);

  const { data: dailyStatusList = [] } = useMonthlyExpenseStatus(
    userId,
    currentDate
  );

  const handleExpand = () => {
    // 닫힐 때 선택된 날짜로 currentDate 복원
    if (isExpanded && selectedDate) {
      setCurrentDate(parseISO(selectedDate));
    }
    setIsExpanded((prev) => !prev);
  };

  useEffect(() => {
    const mainElement = document.querySelector('main');
    if (isExpanded && mainElement) {
      mainElement.style.overflow = 'hidden';
    } else if (mainElement) {
      mainElement.style.overflow = 'auto';
    }
    return () => {
      if (mainElement) mainElement.style.overflow = 'auto';
    };
  }, [isExpanded]);

  return (
    <Container>
      <motion.div
        animate={{ height: isExpanded ? '80vh' : '150px' }}
        initial={false}
        transition={{ duration: 0.5, ease: [0.4, 0, 0.2, 1] }}
        style={{
          overflowY: isExpanded && window.innerWidth < 768 ? 'auto' : 'hidden',
          WebkitOverflowScrolling: 'touch', // iOS 부드러운 스크롤
        }}
      >
        <Wrapper $isMini={!isExpanded}>
          {!isExpanded ? (
            <MiniCalendar
              dailyStatusList={dailyStatusList}
              onExpand={handleExpand}
              onDateSelect={onDateSelect}
              selectedDate={selectedDate}
            />
          ) : (
            <FullCalendar
              currentDate={currentDate}
              onDateChange={setCurrentDate}
              dailyStatusList={dailyStatusList}
              onCollapse={handleExpand}
              onDateSelect={onDateSelect}
              selectedDate={selectedDate}
            />
          )}
        </Wrapper>
      </motion.div>

      <AnimatePresence>
        {isExpanded && (
          <motion.div
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            exit={{ opacity: 0 }}
            transition={{ duration: 0.5 }}
          >
            <ModalBackground onClick={handleExpand} />
          </motion.div>
        )}
      </AnimatePresence>
    </Container>
  );
};

const Container = styled.div`
  width: 100vw;
  position: relative;
  left: 50%;
  right: 50%;
  margin-left: -50vw;
  margin-right: -50vw;
`;

const ModalBackground = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 70px; // 하단 고정 네비게이션 영역 침범 방지
  background-color: rgba(0, 0, 0, 0.3);
  backdrop-filter: blur(1px);
  z-index: 5;
`;

const Wrapper = styled.div<{ $isMini: boolean }>`
  position: relative;
  background-color: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
  z-index: 10;
  padding-bottom: ${({ $isMini }) => ($isMini ? '0px' : '18px')};
`;
