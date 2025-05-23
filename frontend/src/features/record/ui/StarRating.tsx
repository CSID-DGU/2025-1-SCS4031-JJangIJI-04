import styled from 'styled-components';

interface StarRatingProps {
  value: number;
  onChange: (value: number) => void;
  max?: number;
}

export const StarRating = ({ value, onChange, max = 5 }: StarRatingProps) => {
  const handleClick = (index: number) => {
    if (value === index) {
      onChange(0); 
    } else {
      onChange(index);
    }
  };

  return (
    <Wrapper>
      <StarWrapper>
        {Array.from({ length: max }, (_, i) => {
          const index = i + 1;
          return (
            <Star
              key={index}
              filled={index <= value}
              onClick={() => handleClick(index)}
            >
              ★
            </Star>
          );
        })}
      </StarWrapper>
      {value > 0 && <RatingText>{value.toFixed(1)}</RatingText>}
    </Wrapper>
  );
};

const Wrapper = styled.div`
  display: flex;
  align-items: center;
  gap: 8px; // 별과 평점 텍스트 간 간격
`;

const StarWrapper = styled.div`
  display: flex;
  gap: 6px; // 별 간격 넓힘
`;

const Star = styled.span<{ filled: boolean }>`
  font-size: 24px;
  color: ${({ filled }) => (filled ? '#FFD700' : '#ccc')};
  cursor: pointer;
  transition: color 0.2s;
`;

const RatingText = styled.span`
  font-size: 16px;
  font-weight: 500;
  color: #333;
`;