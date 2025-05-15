import styled from 'styled-components';

interface BudgetGaugeProps {
  used: number;
  total: number;
}

export const BudgetGauge = ({ used, total }: BudgetGaugeProps) => {
  const ratio = Math.min(used / total, 1);

  return (
    <Wrapper>
      <TopRow>
        <BudgetText>0원</BudgetText>
        <BudgetText>{total.toLocaleString()}원</BudgetText>
      </TopRow>
      <BarWrapper>
        <Track>
          <Fill style={{ width: `${ratio * 100}%` }} />
        </Track>
      </BarWrapper>
    </Wrapper>
  );
};

const Wrapper = styled.div`
  width: 119px;
  height: 29px;
  padding: 1px 8px;
  border-radius: 5px;
  background-color: white;
  border: 0.5px solid #808080;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
`;

const TopRow = styled.div`
  display: flex;
  justify-content: space-between;
  font-size: var(--font-size-5xs);
  font-weight: 600;
  color: #222;
  margin-top: 8px;
  margin-bottom: 4px;
`;

const BudgetText = styled.span``;

const BarWrapper = styled.div`
  position: relative;
  height: 4px;
`;

const Track = styled.div`
  background-color: #f9ece1;
  height: 4px;
  border-radius: 4px;
  position: relative;
  overflow: hidden;
`;

const Fill = styled.div`
  background-color: #f97316;
  height: 100%;
  border-radius: 4px;
  position: absolute;
  top: 0;
  left: 0;
`;

// const Marker = styled.div<{ passedHalf: boolean }>`
//   position: absolute;
//   left: 50%;
//   transform: translateX(-50%);
//   width: 4px;
//   height: 4px;
//   border-radius: 50%;
//   background-color: ${({ passedHalf }) => (passedHalf ? 'white' : '#f97316')};
//   z-index: 1;
// `;
