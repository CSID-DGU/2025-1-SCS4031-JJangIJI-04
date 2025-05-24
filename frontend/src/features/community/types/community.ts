export interface Reaction {
  key: string;
  count: number;
}

export type EmojiKey = number;

export interface CommunityPost {
  id: number;
  nickname: string;
  profileImage: string;
  date: string; // ISO 문자열 또는 yyyy.mm.dd 포맷
  restaurant: {
    name: string;
    category: string;
    price: number;
  };
  content: string;
  emojiReactions: Partial<Record<EmojiKey, number>>;
  budget: {
    total: number;
    used: number;
  };
}
