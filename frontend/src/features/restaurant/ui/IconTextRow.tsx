import styled from 'styled-components';

interface IconTextRowProps {
  icon: string;
  text: string;
  color?: string;
  fontSize?: string;
  fontWeight?: string | number;
  ellipsis?: boolean;
}

export const IconTextRow = ({
  icon,
  text,
  color = '#808080',
  fontSize = '13px',
  fontWeight = '600',
  ellipsis = true, // 기본값은 잘림 허용
}: IconTextRowProps) => {
  return (
    <Row>
      <Icon src={icon} alt="" />
      <Text $ellipsis={ellipsis} style={{ color, fontSize, fontWeight }}>
        {text}
      </Text>
    </Row>
  );
};

const Row = styled.div`
  display: flex;
  align-items: center;
  gap: 4px;
  min-width: 0;
`;

const Icon = styled.img`
  width: 12px;
  height: 16px;
  flex-shrink: 0;
`;

const Text = styled.span<{ $ellipsis: boolean }>`
  ${({ $ellipsis }) =>
    $ellipsis
      ? `
    flex: 1;
    min-width: 0;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    display: block;
    max-width: 100%;
  `
      : `
    white-space: normal;
    overflow: visible;
    text-overflow: unset;
  `}
`;
