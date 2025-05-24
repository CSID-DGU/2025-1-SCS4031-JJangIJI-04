import { CommunityPost, EmojiKey } from '@/features/community/types/community';

export const getCommunityPosts = async (): Promise<CommunityPost[]> => {
  return [
    {
      id: 1,
      nickname: '한끼모아',
      profileImage: '',
      date: '2025.03.19',
      restaurant: {
        name: '오이드킨',
        category: '규동',
        price: 12000,
      },
      content: '가성비 좋고 맛도 적당한 식당 찾아서 기분 좋다 횽',
      emojiReactions: {
        4: 4,
        2: 2,
      } as Record<EmojiKey, number>,
      budget: {
        total: 84000,
        used: 15000,
      },
    },
    {
      id: 2,
      nickname: 'Heeju',
      profileImage: 'https://placehold.co/48x48',
      date: '2025.03.19',
      restaurant: {
        name: '얄촌',
        category: '알밥',
        price: 5600,
      },
      content: '오늘은 싸게 먹었다!',
      emojiReactions: {
        1: 1,
      } as Record<EmojiKey, number>,
      budget: {
        total: 41000,
        used: 25000,
      },
    },
    {
      id: 3,
      nickname: '두끼모아',
      profileImage: '',
      date: '2025.03.18',
      restaurant: {
        name: '은화수식당',
        category: '고구마치즈돈까스',
        price: 12500,
      },
      content: '',
      emojiReactions: {} as Record<EmojiKey, number>,
      budget: {
        total: 45000,
        used: 30000,
      },
    },
  ];
};
