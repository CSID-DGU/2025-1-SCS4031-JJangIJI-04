import { useParams, useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import { useRestaurantDetail } from '@/features/restaurant/api/useRestaurantDetail';
import { LoadingSpinner } from '@/shared/ui/LoadingSpinner/LoadingSpinner';
import { BookmarkButton } from '@/features/restaurant/ui/bookmark/BookmarkButton';
import { IconTextRow } from '@/features/restaurant/ui/IconTextRow';
import { useState } from 'react';

export const RestaurantDetailPage = () => {
  const navigate = useNavigate();
  const { id } = useParams<{ id: string }>();
  const restaurantId = Number(id);
  const { data: restaurant, isLoading } = useRestaurantDetail(restaurantId);

  const [bookmarked, setBookmarked] = useState<boolean>(
    restaurant?.bookmarked ?? false
  );

  if (isLoading || !restaurant) {
    return <LoadingSpinner message="식당 정보를 불러오는 중입니다..." />;
  }

  const handleToggleBookmark = () => {
    setBookmarked((prev) => !prev);
    // 실제 API 연동은 나중에
  };

  return (
    <PageWrapper>
      <ImageSection>
        {Array.isArray(restaurant.imgUrl) && restaurant.imgUrl.length > 0 ? (
          restaurant.imgUrl.map((url, index) => (
            <Image
              key={index}
              src={url}
              alt="음식 이미지"
              onError={(e) => {
                e.currentTarget.src = '/images/basic-restaurant.svg';
              }}
            />
          ))
        ) : (
          <Image src="/images/basic-restaurant.svg" alt="기본 음식 이미지" />
        )}
        <BackButton onClick={() => navigate(-1)}>&lt;</BackButton>
        <BookmarkWrapper>
          <BookmarkButton
            active={bookmarked}
            onClick={handleToggleBookmark}
            size={16}
          />
        </BookmarkWrapper>
      </ImageSection>

      <ContentSection>
        <RestaurantName>{restaurant.name ?? '이름 없음'}</RestaurantName>

        <IconTextList>
          <IconTextRow
            icon="/icons/restaurants/price.svg"
            text={`평균 가격 ${
              restaurant.menuAverage !== undefined
                ? `${restaurant.menuAverage.toLocaleString()}원`
                : '정보 없음'
            }`}
            color="#FF6701"
            fontSize="var(--font-size-2xs)"
            fontWeight={600}
          />
          <IconTextRow
            icon="/icons/restaurants/place.svg"
            text={restaurant.streetAddress ?? '주소 정보 없음'}
            color="#808080"
            fontSize="var(--font-size-3xs)"
            fontWeight={400}
          />
          <IconTextRow
            icon="/icons/restaurants/time.svg"
            text={
              Array.isArray(restaurant.openingHour)
                ? restaurant.openingHour.join(', ')
                : '영업 시간 정보 없음'
            }
            color="#808080"
            fontSize="var(--font-size-3xs)"
            fontWeight={400}
          />
          <IconTextRow
            icon="/icons/restaurants/menu.svg"
            text={restaurant.category ?? '카테고리 정보 없음'}
            color="#808080"
            fontSize="var(--font-size-3xs)"
            fontWeight={400}
          />
        </IconTextList>

        <MenuTitle>메뉴</MenuTitle>
        {restaurant.menu.length > 0 ? (
          restaurant.menu.map((item, index) => (
            <MenuItem key={index}>
              <MenuInfo>
                {item.main && <Badge>대표</Badge>}
                <MenuName>{item.name}</MenuName>
                <MenuDescription>{item.introduce}</MenuDescription>
                <MenuPrice>{item.price?.toLocaleString() ?? 0}원</MenuPrice>
              </MenuInfo>
              <MenuImage
                src={item.imgUrl || '/images/basic-restaurant.svg'}
                onError={(e) => {
                  e.currentTarget.src = '/images/basic-restaurant.svg';
                }}
                alt={item.name}
              />
            </MenuItem>
          ))
        ) : (
          <MenuDescription>메뉴 정보가 없습니다.</MenuDescription>
        )}
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
  width: 20px;
  height: 20px;
  font-size: 12px;
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
  width: 20px;
  height: 20px;
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
  gap: 2px;
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
  gap: 4px;
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
  margin-left: 12px;
`;
