import { CommunityPost } from "@/features/community/types/community";

export const getCommunityPosts = async (): Promise<CommunityPost[]> => {
  return [
    {
      nickname: "한끼모아",
      userId: 1,
      profileImage: "",
      savingGoalId: 10,
      expenseId: 101,
      restaurantId: 1001,
      restaurant: "오이드킨",
      menu: "규동",
      expense: 12000,
      createdAt: "2025.03.19",
      savingGoal: 84000,
      remainingBudget: 69000,
      memo: "가성비 좋고 맛도 적당한 식당 찾아서 기분 좋다 횽",
      emojis: [
        { emojiId: 4, count: 4 },
        { emojiId: 2, count: 2 },
      ],
    },
    {
      nickname: "Heeju",
      userId: 2,
      profileImage: "https://placehold.co/48x48",
      savingGoalId: 11,
      expenseId: 102,
      restaurantId: 1002,
      restaurant: "얄촌",
      menu: "알밥",
      expense: 5600,
      createdAt: "2025.03.19",
      savingGoal: 41000,
      remainingBudget: 15400,
      memo: "오늘은 싸게 먹었다!",
      emojis: [
        { emojiId: 1, count: 1 },
      ],
    },
    {
      nickname: "두끼모아",
      userId: 3,
      profileImage: "",
      savingGoalId: 12,
      expenseId: 103,
      restaurantId: 1003,
      restaurant: "은화수식당",
      menu: "고구마치즈돈까스파게티맛있어",
      expense: 13000,
      createdAt: "2025.03.18",
      savingGoal: 45000,
      remainingBudget: 32000,
      memo: "",
      emojis: [],
    },
  ];
};
