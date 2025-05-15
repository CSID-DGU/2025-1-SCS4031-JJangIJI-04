import styled from 'styled-components';
import { emojiMap } from '@/features/community/constants/emojiMap';
import { EmojiKey } from '@/features/community/types/community';

interface EmojiReactionPanelProps {
  reactions: Partial<Record<EmojiKey, number>>;
  selected?: EmojiKey | null;
  onClickEmoji?: (emoji: EmojiKey) => void; // 👈 클릭 이벤트 추가
}

export const EmojiReactionPanel = ({
  reactions,
  selected,
  onClickEmoji,
}: EmojiReactionPanelProps) => {
  return (
    <Wrapper>
      {Object.entries(reactions).map(([key, count]) => {
        const emojiKey = Number(key) as EmojiKey;
        const selectedKey = Number(selected); // ✅ selected도 숫자로 변환
        const emoji = emojiMap[emojiKey];
        if (!count || !emoji) return null;

        return (
          <EmojiItem
            key={key}
            $selected={emojiKey === selectedKey}
            onClick={() => onClickEmoji?.(emojiKey)}
          >
            <EmojiImg src={emoji.src} alt={emoji.label} />
            <Count $selected={emojiKey === selectedKey}>{count}</Count>
          </EmojiItem>
        );
      })}
    </Wrapper>
  );
};

const Wrapper = styled.div`
  display: flex;
  flex-wrap: wrap;
  gap: 5px;
`;

const EmojiItem = styled.div<{ $selected: boolean }>`
  display: flex;
  align-items: center;
  gap: 2px;
  padding: 1px 4px;
  border: ${({ $selected }) => !$selected && '0.5px solid #808080'};
  border-radius: 12px;
  background-color: ${({ $selected }) => ($selected ? '#f97316' : '#fff')};
`;

const EmojiImg = styled.img`
  width: 10px;
  height: 10px;
`;

const Count = styled.span<{ $selected: boolean }>`
  font-size: var(--font-size-3xs);
  color: ${({ $selected }) => ($selected ? '#fff' : '#444')};
`;
