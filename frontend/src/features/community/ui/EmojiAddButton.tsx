import styled from 'styled-components';
import { useState } from 'react';
import { EmojiPopover } from '@/features/community/ui/EmojiPopover';
import { EmojiKey } from '@/features/community/types/community';

interface EmojiAddButtonProps {
  onSelect: (emojiKey: EmojiKey) => void;
}

export const EmojiAddButton = ({ onSelect }: EmojiAddButtonProps) => {
  const [open, setOpen] = useState(false);

  const handleSelect = (key: EmojiKey) => {
    onSelect(key);
    setOpen(false);
  };

  return (
    <Container>
      <Button onClick={() => setOpen(!open)}>
        <img src="/icons/community/emojis/addEmoji.svg" alt="Add emoji" />
      </Button>
      {open && <EmojiPopover onSelect={handleSelect} />}
    </Container>
  );
};

const Container = styled.div`
  position: relative;
`;

const Button = styled.button`
  display: flex;
  align-items: center;
  gap: 2px;
  border: 0.5px solid #808080;
  border-radius: 16px;
  padding: 1px 5px;
  font-size: 12px;
  background-color: #fff;
  cursor: pointer;

  img {
    width: 11px;
    height: 10px;
  }
`;
