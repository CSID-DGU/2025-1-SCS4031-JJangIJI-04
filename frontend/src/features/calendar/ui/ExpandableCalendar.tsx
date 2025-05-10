import { motion, AnimatePresence } from 'framer-motion';  // 추가
import { useState, useEffect } from 'react';
import styled from 'styled-components';
import { MiniCalendar } from '@/features/calendar/ui/MiniCalendar';
import { FullCalendar } from '@/features/calendar/ui/FullCalendar';
import type { DailyExpenseStatus } from '@/features/calendar/types/expense';

interface Props {
  dailyStatusList: DailyExpenseStatus[];
}

export const ExpandableCalendar = ({ dailyStatusList }: Props) => {
    const [isExpanded, setIsExpanded] = useState(false);
  
    const handleExpand = () => {
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
        if (mainElement) {
          mainElement.style.overflow = 'auto';
        }
      };
    }, [isExpanded]);
  
    return (
      <Container>
        <motion.div
          layout  // 레이아웃 변경 자동 애니메이션
          initial={false}
          transition={{
            layout: {
              duration: 0.6,
              ease: [0.4, 0, 0.2, 1]  // 부드러운 이징
            }
          }}
        >
          <Wrapper $isMini={!isExpanded}>
            <AnimatePresence mode="wait">
              {!isExpanded ? (
                <motion.div
                  key="mini"
                  initial={{ opacity: 0 }}
                  animate={{ opacity: 1 }}
                  exit={{ opacity: 0 }}
                  transition={{ duration: 0.3 }}
                >
                  <MiniCalendar dailyStatusList={dailyStatusList} onExpand={handleExpand} />
                </motion.div>
              ) : (
                <motion.div
                  key="full"
                  initial={{ opacity: 0 }}
                  animate={{ opacity: 1 }}
                  exit={{ opacity: 0 }}
                  transition={{ duration: 0.3 }}
                >
                  <FullCalendar dailyStatusList={dailyStatusList} onCollapse={handleExpand} />
                </motion.div>
              )}
            </AnimatePresence>
          </Wrapper>
        </motion.div>
        <AnimatePresence>
          {isExpanded && (
            <motion.div
              initial={{ opacity: 0 }}
              animate={{ opacity: 1 }}
              exit={{ opacity: 0 }}
              transition={{ duration: 0.4 }}
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
  position: absolute;
  top: 420px;
  left: 0;
  right: 0;
  bottom: -100vh;
  background-color: rgba(0, 0, 0, 0.3);
  backdrop-filter: blur(1px);
  z-index: 1;
`;

const Wrapper = styled.div<{ $isMini: boolean }>`
  position: relative;
  background-color: #fff;
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.2), 0 2px 8px rgba(0, 0, 0, 0.15);
  z-index: 2;
  overflow: hidden;
`;