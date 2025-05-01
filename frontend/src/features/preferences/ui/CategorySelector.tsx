import styled from 'styled-components';

interface Category {
  label: string;
  value: string;
  icon: string;
}

interface Props {
  selected: string[];
  onChange: (next: string[]) => void;
}

const CATEGORY_LIST: Category[] = [
  { label: '한식', value: '한식', icon: 'korean-food.svg' },
  { label: '중식', value: '중식', icon: 'chinese-food.svg' },
  { label: '일식', value: '일식', icon: 'japanese-food.svg' },
  { label: '양식', value: '양식', icon: 'western-food.svg' },
  { label: '분식', value: '분식', icon: 'korean-street-food.svg' },
  { label: '아시안', value: '아시안', icon: 'asian-food.svg' },
  { label: '멕시칸', value: '멕시칸', icon: 'mexican-food.svg' },
  { label: '기타', value: '기타', icon: 'other-food.svg' },
];

export const CategorySelector = ({ selected, onChange }: Props) => {
  const toggle = (value: string) => {
    if (selected.includes(value)) {
      onChange(selected.filter((v) => v !== value));
    } else {
      onChange([...selected, value]);
    }
  };

  return (
    <Grid>
      {CATEGORY_LIST.map((cat) => (
        <Button
            key={cat.value}
            selected={selected.includes(cat.value)}
            onClick={() => toggle(cat.value)}
        >
        <div className="image-container">
            <img src={`/icons/categories/${cat.icon}`} alt={cat.label} />
        </div>
        {cat.label}
        </Button>
      ))}
    </Grid>
  );
};

const Grid = styled.div`
  display: grid;
  grid-template-columns: repeat(2, 1fr); // 2열 정렬
  gap: 16px;
  margin-top: 32px;
`;

const Button = styled.button<{ selected: boolean }>`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  padding: 12px;
  height: 75px;  
  width: 100%;           
  aspect-ratio: 3 / 2;   

  background-color: ${({ selected }) => (selected ? '#FF6701' : '#fff')};
  border: 1px solid ${({ selected }) => (selected ? '#FF6701' : '#ccc')};
  border-radius: 12px;
  color: ${({ selected }) => (selected ? '#fff' : '#333')};

  transition: 0.2s;
  cursor: pointer;

  &:hover {
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  }

  .image-container {
    width: 60px;
    height: 60px;
    display: flex;
    align-items: center;
    justify-content: center;
    margin-bottom: 4px;
  }

  img {
    width: 35px;    
    height: 35px;    
    object-fit: contain;
    filter: ${({ selected }) =>
      selected
        ? 'invert(1)'
        : 'brightness(0) saturate(100%) invert(40%)'};
  }

  font-size: 11px;
  font-weight: 500;
`;