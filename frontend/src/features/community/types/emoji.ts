export interface AddEmojiRequest {
  expenseId: number;
  emojiId: number;
}

export interface AddEmojiResponse {
  expenseEmojiId: number;
}
  
export interface DeleteEmojiRequest {
  emojiId: number;
  expenseId: number;
}