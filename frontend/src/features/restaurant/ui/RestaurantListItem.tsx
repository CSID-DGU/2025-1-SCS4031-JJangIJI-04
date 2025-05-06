import styled from 'styled-components';
import { Restaurant } from '@/features/restaurant/types/restaurant';
import { Link } from 'react-router-dom';
import { Crown } from '@/features/restaurant/ui/crown/Crown';
import { BookmarkButton } from '@/features/restaurant/ui/bookmark/BookmarkButton';
import { IconTextRow } from '@/features/restaurant/ui/IconTextRow';
import { getRestaurants } from '@/features/restaurant/api/restaurantApi';
import { useEffect, useState } from 'react';

const detailItems = [
  {
    icon: '/icons/restaurants/price.svg',
    text: (r: Restaurant) => `평균 가격 ${r.averagePrice.toLocaleString()}원`,
    color: '#FF6701',
    fontSize: 'var(--font-size-2xs)',
    fontWeight: 600,
  },
  {
    icon: '/icons/restaurants/place.svg',
    text: () => '음식점 주소',
    color: '#808080',
    fontSize: 'var(--font-size-3xs)',
    fontWeight: 400,
  },
  {
    icon: '/icons/restaurants/time.svg',
    text: () => '영업 시간',
    color: '#808080',
    fontSize: 'var(--font-size-3xs)',
    fontWeight: 400,
  },
  {
    icon: '/icons/restaurants/menu.svg',
    text: () => '음식 종류',
    color: '#808080',
    fontSize: 'var(--font-size-3xs)',
    fontWeight: 400,
  },
];

export const RestaurantListItem = () => {
  const [restaurants, setRestaurants] = useState<Restaurant[]>([]);

  useEffect(() => {
    getRestaurants().then(setRestaurants);
  }, []);

  // 북마크 토글하는 함수 (index 기반)
  const toggleBookmark = (index: number) => {
    setRestaurants((prev) =>
      prev.map((restaurant, idx) =>
        idx === index
          ? { ...restaurant, bookmarked: !restaurant.bookmarked }
          : restaurant
      )
    );
  };
  return (
    <ListWrapper>
      {restaurants.map((restaurant, index) => (
        <Card>
          <ThumbnailLink to={`/restaurants/${restaurant.id}`}>
            <Thumbnail src={restaurant.imageUrls[0]} alt={restaurant.name} />
          </ThumbnailLink>
          <Info>
            <TopRow>
              <NameWrapper>
                <Crown rank={index} />
                <Name>{restaurant.name}</Name>
              </NameWrapper>
              <BookmarkButton
                active={restaurant.bookmarked}
                onClick={() => toggleBookmark(index)}
              />
            </TopRow>
            {detailItems.map(({ icon, text, color, fontSize, fontWeight }) => (
              <IconTextRow
                key={icon}
                icon={icon}
                text={typeof text === 'function' ? text(restaurant) : text}
                color={color}
                fontSize={fontSize}
                fontWeight={fontWeight}
              />
            ))}
          </Info>
        </Card>
      ))}
    </ListWrapper>
  );
};

const Card = styled.div`
  display: flex;
  align-items: stretch; /* stretch로 자식 height 맞춤 */
  background-color: #fff;
  overflow: hidden;
  height: 99px; /* Thumbnail과 동일하게 고정 */
`;

const ThumbnailLink = styled(Link)`
  display: block;
  height: 100%; /* Card 기준 */
`;

const Thumbnail = styled.img`
  width: 132px;
  height: 99px;
  object-fit: cover;
`;

const Info = styled.div`
  flex: 1;
  padding: 0 15px;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
`;

const TopRow = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: var(--font-size-lg);
  font-weight: bold;
`;

const NameWrapper = styled.div`
  display: flex;
  align-items: center;
  gap: 4px;
`;

const Name = styled.span`
  font-size: var(--font-size-md);
  font-weight: 500;
`;
const ListWrapper = styled.div`
  display: flex;
  flex-direction: column;
  margin-top: 36px;
  gap: 28px;
`;
