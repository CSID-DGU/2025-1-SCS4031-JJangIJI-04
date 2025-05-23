import styled from 'styled-components';

export const FullWidthDivider = () => {
  return <Divider />;
};

const Divider = styled.hr`
  width: 100%;
  max-width: 100vw;
  background-color: #FFC288;
  height: 3px;
  margin: 32px 0;
  border: none;
`;
