import { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import { getRestaurantById } from '@/features/restaurant/api/restaurantApi';
import { Restaurant } from '@/features/restaurant/types/restaurant';
import { BookmarkButton } from '@/features/restaurant/ui/bookmark/BookmarkButton';
import { IconTextRow } from '@/features/restaurant/ui/IconTextRow';

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
    text: (r: Restaurant) => r.location,
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

export const RestaurantDetailPage = () => {
  const navigate = useNavigate(); // ✅ 추가!
  const { id } = useParams<{ id: string }>();
  const [restaurant, setRestaurant] = useState<Restaurant | null>(null);
  const [bookmarked, setBookmarked] = useState(false);

  useEffect(() => {
    if (id) {
      getRestaurantById(id).then((res) => {
        setRestaurant(res);
        setBookmarked(res.bookmarked); // 데이터 받아온 후 설정
      });
    }
  }, [id]);

  if (!restaurant) return <div>Loading...</div>; // 이건 그냥 놔둬
  const toggleBookmark = () => {
    setBookmarked((prev) => !prev);
  };

  return (
    <PageWrapper>
      <ImageSection>
        {restaurant.imageUrls.map((url, index) => (
          <Image key={index} src={url} alt="음식 이미지" />
        ))}
        <BackButton onClick={() => navigate(-1)}> &lt;</BackButton>
        <BookmarkWrapper>
          <BookmarkButton
            active={bookmarked}
            onClick={toggleBookmark}
            size={16}
          />
        </BookmarkWrapper>
      </ImageSection>

      <ContentSection>
        <RestaurantName>{restaurant!.name}</RestaurantName>

        <IconTextList>
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
        </IconTextList>

        <MenuTitle>메뉴</MenuTitle>
        {restaurant.menu.map((item, index) => (
          <MenuItem key={index}>
            <MenuInfo>
              {index === 0 && <Badge>대표</Badge>}
              <MenuName>{item.name}</MenuName>
              <MenuDescription>{item.description}</MenuDescription>
              <MenuPrice>{item.price}</MenuPrice>
            </MenuInfo>
            <MenuImage src={item.imageUrl} alt={item.name} />
          </MenuItem>
        ))}
      </ContentSection>
    </PageWrapper>
  );
};

const PageWrapper = styled.div`
  padding: 0px 0;
`;

const ImageSection = styled.div`
  position: relative;
  display: flex;
  overflow-x: auto;
`;

const Image = styled.img`
  width: 195px;
  height: 160px;
  object-fit: cover;
  flex-shrink: 0;
`;

const BackButton = styled.button`
  position: absolute;
  top: 12px;
  left: 12px;
  background-color: white;
  border: none;
  border-radius: 50%;
  width: 20px; /* 수정 */
  height: 20px; /* 수정 */
  font-size: 12px; /* 수정 */
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.15);

  &:hover {
    background-color: #f0f0f0;
  }
`;

const BookmarkWrapper = styled.div`
  position: absolute;
  top: 12px;
  right: 12px;
  background-color: white;
  border-radius: 50%;
  width: 20px; /* 수정 */
  height: 20px; /* 수정 */
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.15);

  &:hover {
    background-color: #f0f0f0;
  }
`;

const ContentSection = styled.div`
  padding: 20px;
`;

const RestaurantName = styled.h2`
  font-size: var(--font-size-2lg);
  font-weight: 500;
`;

const IconTextList = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2px; /* 아이템 간 간격 */
  margin-top: 6px;
`;

const MenuTitle = styled.h3`
  margin-top: 48px;
  font-size: var(--font-size-2lg);
  font-weight: 500;
`;

const MenuItem = styled.div`
  display: flex;
  align-items: center;
  margin-top: 16px;
  justify-content: space-between;
`;

const MenuInfo = styled.div`
  display: flex;
  flex-direction: column;
  gap: 4px; // 각 텍스트 간 간격
`;

const MenuName = styled.div`
  font-size: var(--font-size-sm);
  font-weight: 700;
`;

const MenuPrice = styled.div`
  font-size: var(--font-size-sm);
  font-weight: 700;
  margin-top: 12px;
`;

const MenuDescription = styled.div`
  font-size: var(--font-size-3xs);
  color: #808080;
`;

const Badge = styled.span`
  display: inline-block;
  background-color: #f97316;
  color: white;
  font-size: 10px;
  border-radius: 4px;
  padding: 2px 6px;
  margin-bottom: 4px;
  width: fit-content;
  text-align: center;
`;

const MenuImage = styled.img`
  width: 106.67px;
  height: 80px;
  object-fit: cover;
  border-radius: 10px;
  margin-left: 12px; // 간격 조정
`;
