import styled from 'styled-components';
import { EmojiReactionPanel } from '@/features/community/ui/EmojiReactionPanel';
import type { EmojiKey } from '@/features/community/types/community';
import StoreIcon from '@/assets/icons/store.svg?react';
import MenuIcon from '@/assets/icons/menu.svg?react';
import WalletIcon from '@/assets/icons/wallet.svg?react';

interface ExpenseCardProps {
  storeName: string;
  category: string;
  amount: number;
  memo: string;
  reactions: Partial<Record<EmojiKey, number>>;
}

export const ExpenseCard = ({
  storeName,
  category,
  amount,
  memo,
  reactions,
}: ExpenseCardProps) => {
  return (
    <Card>
      <Content>
        <InfoRow>
          <StoreIcon />
          <StoreName>{storeName}</StoreName>
        </InfoRow>

        <InfoRow>
          <MenuIcon />
          <Category>{category}</Category>
        </InfoRow>

        <InfoRow>
          <WalletIcon />
          <Amount>{amount.toLocaleString()}원</Amount>
        </InfoRow>

        <Memo>{memo}</Memo>

        <EmojiReactionPanel reactions={reactions} selected={null} />
      </Content>
    </Card>
  );
};

const Card = styled.div`
  margin-top: 16px;
  padding: 16px;
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
  background-color: #fff;
`;

const Content = styled.div`
  display: flex;
  flex-direction: column;
  gap: 6px;
`;

const InfoRow = styled.div`
  display: flex;
  align-items: center;
  gap: 8px;
  margin-top: 4px;

  svg {
    width: 16px;
    height: 16px;
    flex-shrink: 0;
  }
`;

const StoreName = styled.div`
  font-weight: bold;
  font-size: 16px;
`;

const Amount = styled.div`
  font-size: 14px;
  color: #444;
`;

const Category = styled.div`
  font-size: 12px;
  color: #888;
`;

const Memo = styled.div`
  font-size: 13px;
  color: #444;
  margin-top: 8px;
  white-space: pre-line;
`;
