import styled from 'styled-components';
import { RestaurantListItem } from '@/features/restaurant/ui/RestaurantListItem';

export const RestaurantsPage = () => {
  return (
    <PageWrapper>
      <Header>
        <Title>한끼추천</Title>
        <Subtitle>
          선호하는 음식 종류와 목표 절약 금액을 바탕으로
          <br />
          적절한 식당을 추천해 드려요!
        </Subtitle>
      </Header>
      <Divider />
<<<<<<< HEAD
=======
      {/* <ListWrapper>
        {restaurants.map((restaurant, index) => (
          <RestaurantListItem
            key={restaurant.id}
            restaurant={restaurant}
            index={index}
            onToggleBookmark={toggleBookmark}
          />
        ))}
      </ListWrapper> */}
>>>>>>> dd0d93a (feat: 식당 페이지 구현)
      <RestaurantListItem />
    </PageWrapper>
  );
};

// styled-components (기존과 동일)
const PageWrapper = styled.div`
  /* padding: 20px; */
  /* background-color: #fef9f2; */
`;

const Header = styled.div`
  margin: 20px 5px;
`;

const Title = styled.h1`
  font-size: var(--font-size-lg);
  font-weight: 600;
`;

const Subtitle = styled.p`
  font-size: var(--font-size-2xs);
  color: #808080;
  font-weight: 700;
  line-height: 1.5;
`;

const Divider = styled.hr`
  border: none;
  border-top: 1px solid #808080;
  margin: 20px px;
`;
<<<<<<< HEAD
=======

const ListWrapper = styled.div`
  display: flex;
  flex-direction: column;
  margin-top: 36px;
  gap: 28px;
`;
>>>>>>> dd0d93a (feat: 식당 페이지 구현)
