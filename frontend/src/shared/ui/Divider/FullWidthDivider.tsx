import styled from 'styled-components';

/*
화면 전체 너비로 뻗는 Divider 컴포넌트
중앙 정렬 레이아웃에서도 화면 끝까지 선을 표시할 수 있도록 함
글로벌 스타일의 max-width 제약을 피함
 */

export const FullWidthDivider = () => {
  return <Divider />;
};

const Divider = styled.hr`
  width: 100vw;
  height: 3px;
  background-color: #FFC288;
  margin: 32px 0;
  margin-left: calc(-50vw + 50%);
  border: none;
`;
