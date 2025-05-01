import styled from 'styled-components';

interface InputFieldProps {
  value: string;
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
  placeholder?: string;
  maxLength?: number;
  showClear?: boolean;
  error?: string;
  isValid?: boolean;
}

export const InputField = ({
  value,
  onChange,
  placeholder = '',
  maxLength = 100,
  showClear = true,
  error,
  isValid = true,
}: InputFieldProps) => {
  return (
    <InputWrapper>
      <StyledInput
        value={value}
        onChange={onChange}
        placeholder={placeholder}
        maxLength={maxLength}
      />
      {showClear && value && (
        <ClearButton onClick={() => onChange({ target: { value: '' } } as any)}>
          <img src="/icons/close-outline.svg" alt="입력 지우기" />
        </ClearButton>
      )}
      {error && <Message valid={isValid}>{error}</Message>}
    </InputWrapper>
  );
};

const InputWrapper = styled.div`
  position: relative;
  width: 100%;
  margin-top: 15px;
`;

const StyledInput = styled.input`
  width: 100%;
  padding: 8px 0;
  font-size: 16px;
  border: none;
  border-bottom: 1px solid #808080;
  background: transparent;
  outline: none;

  &::placeholder {
    font-size: 12px;
    color: #aaa;
  }
`;

const ClearButton = styled.button`
  position: absolute;
  right: 0;
  top: 50%;
  transform: translateY(-50%);
  background: transparent;
  border: none;
  font-size: 18px;
  color: #808080;
  cursor: pointer;
  padding: 4px;
  line-height: 1;
  img {
    width: 20px;
    height: 20px;
    vertical-align: middle;
    transform: translateY(-6px);
  }

  &:hover {
    color: #333;
  }
`;

const Message = styled.p<{ valid: boolean }>`
  font-size: 12px;
  margin-top: 8px;
  color: ${(props) => (props.valid ? 'green' : 'red')};
`;