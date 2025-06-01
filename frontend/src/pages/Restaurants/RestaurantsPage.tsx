import styled from 'styled-components';
import { RestaurantListItem } from '@/features/restaurant/ui/RestaurantListItem';

export const RestaurantsPage = () => {
  return (
    <Container>
      <Header>
        <Title>한끼추천</Title>
        <Subtitle>
          선호하는 음식 종류와 목표 절약 금액을 바탕으로
          <br />
          적절한 식당을 추천해 드려요!
        </Subtitle>
        <Divider />
      </Header>

      {/* 추천 리스트 컴포넌트 */}
      <RestaurantListItem />
    </Container>
  );
};

const Container = styled.div`
  background-color: #fff;
  min-height: 100vh;
  width: 100%;
  padding: var(--page-padding);
  padding-top: var(--safe-area-top);
  box-sizing: border-box;
`;

const Header = styled.div`
  margin: 20px 5px;
  text-align: left;
`;

const Title = styled.h1`
  font-size: var(--font-size-lg);
  font-weight: 700;
  color: #202632;
`;

const Subtitle = styled.p`
  font-size: var(--font-size-2xs);
  color: #808080;
  font-weight: 700;
  line-height: 1.5;
  margin-top: 2px;
`;

const Divider = styled.hr`
  border: none;
  border-top: 1px solid #ccc;
  margin: 16px 0 32px;
`;
