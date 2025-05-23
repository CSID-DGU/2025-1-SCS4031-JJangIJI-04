import { useState } from 'react';
import styled from 'styled-components';
import { InputField } from '@/shared/ui/InputField';
import { FullWidthDivider } from '@/shared/ui/Divider/FullWidthDivider';
import { useNavigate } from 'react-router-dom';
import NavigateBeforeIcon from '@/assets/icons/navigate-before.svg?react';
import { StarRating } from '@/features/record/ui/StarRating';
import { useGetRemainingBudget } from '@/features/record/api/useGetRemainingBudget';
import DatabaseIcon from '@/assets/icons/database.svg?react';
import ArrowIcon from '@/assets/icons/circle-point.svg?react';

const RecordPage = () => {
  const [menuName, setMenuName] = useState('');
  const [restaurantName, setRestaurantName] = useState('');
  const [amount, setAmount] = useState('');
  const [rating, setRating] = useState(0);
  const navigate = useNavigate();
  const { data: remainingBudget = 0 } = useGetRemainingBudget();

  const handleAmountChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const raw = e.target.value.replace(/,/g, '');
    if (raw === '') {
      setAmount('');
      return;
    }
    if (!isNaN(Number(raw))) {
      const formatted = Number(raw).toLocaleString();
      setAmount(formatted);
    }
  };

  const numericAmount = Number(amount.replace(/,/g, '') || 0);
  const remainingAfterExpense = remainingBudget - numericAmount;

  return (
    <Container>
      <BackButtonWrapper>
        <BackButton onClick={() => navigate('/main')}>
          <NavigateBeforeIcon />
        </BackButton>
      </BackButtonWrapper>

      <LabeledInputWrapper>
        <Label>어떤 식당을 방문하셨나요? <Asterisk>*</Asterisk></Label>
        <InputField
          placeholder="식당명을 입력해주세요"
          value={restaurantName}
          onChange={(e) => setRestaurantName(e.target.value)}
          maxLength={30}
        />
      </LabeledInputWrapper>

      <LabeledInputWrapper>
        <Label>어떤 메뉴를 드셨나요? <Asterisk>*</Asterisk></Label>
        <InputField
          placeholder="메뉴명을 입력해주세요"
          value={menuName}
          onChange={(e) => setMenuName(e.target.value)}
          maxLength={30}
        />
      </LabeledInputWrapper>

      <LabeledInputWrapper>
        <Label>식당의 만족도를 평가해주세요 <Asterisk>*</Asterisk></Label>
        <StarRating value={rating} onChange={setRating} />
      </LabeledInputWrapper>

      <FullWidthDivider />

      <LabeledInputWrapper>
        <Label>외식비 총액을 입력해주세요 <Asterisk>*</Asterisk></Label>
        <InputField
          placeholder="금액을 입력해주세요"
          value={amount}
          onChange={handleAmountChange}
          maxLength={15}
        />
      </LabeledInputWrapper>

      {amount && (
        <BudgetResultSection>
          <NoticeBox>
            <StyledIcon />
            이번주 가용 금액이 다음과 같이 남게 돼요
          </NoticeBox>

          <BudgetSummary>
            <BudgetBox>
              <BudgetLabel>현재 가용 금액</BudgetLabel>
              <BudgetValue>{remainingBudget.toLocaleString()}원</BudgetValue>
            </BudgetBox>

            <StyledArrow />

            <BudgetBox>
              <BudgetLabel>남은 가용 금액</BudgetLabel>
              <BudgetValue $negative={remainingAfterExpense < 0}>
                {remainingAfterExpense.toLocaleString()}원
              </BudgetValue>
            </BudgetBox>
          </BudgetSummary>
        </BudgetResultSection>
      )}
    </Container>
  );
};

export default RecordPage;

const Container = styled.div`
  padding: 24px;
  min-height: 100vh;
  display: flex;
  flex-direction: column;
`;

const BackButtonWrapper = styled.div`
  display: flex;
  justify-content: flex-start;
  margin: -8px 0 16px -8px;
`;

const BackButton = styled.button`
  background: none;
  border: none;
  padding: 4px;
  display: flex;
  align-items: center;
  cursor: pointer;
  svg {
    width: 24px;
    height: 24px;
  }
`;

const LabeledInputWrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 4px;
  margin-bottom: 24px;
`;

const Label = styled.label`
  font-size: 14px;
  font-weight: 500;
`;

const Asterisk = styled.span`
  color: red;
  margin-left: 2px;
`;

const BudgetResultSection = styled.div`
  margin-top: 10px;
  margin-bottom: 32px;
`;

const NoticeBox = styled.div`
  background-color: #FF6701;
  color: #fff;
  font-weight: 700;
  padding: 12px;
  border-radius: 8px;
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 14px;
  justify-content: center;
  margin-bottom: 16px;
`;

const StyledIcon = styled(DatabaseIcon)`
  width: 18px;
  height: 18px;
  flex-shrink: 0;
`;

const BudgetSummary = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 24px;
`;

const BudgetBox = styled.div`
  width: 130px;
  height: 72px;
  flex-shrink: 0;               
  background-color: #fff;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.08);
  border-radius: 12px;
  padding: 12px 16px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  box-sizing: border-box;
  overflow: hidden; 
`;

const BudgetLabel = styled.div`
  font-size: 12px;
  color: #666;
  margin-bottom: 4px;
  white-space: nowrap;
  text-align: center;
  max-width: 100%;
  overflow: hidden;
  text-overflow: ellipsis;
`;

const BudgetValue = styled.div<{ $negative?: boolean }>`
  font-size: 18px;
  font-weight: bold;
  color: ${({ $negative }) => ($negative ? '#E74C3C' : '#333')};
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  max-width: 100%;  
  flex-shrink: 1;          
`;

const StyledArrow = styled(ArrowIcon)`
  width: 20px;
  height: 20px;
  color: #999;
  flex-shrink: 0;
`;