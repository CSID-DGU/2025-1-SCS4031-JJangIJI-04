export const formatExpenseAmount = (value: number): string => {
  if (value >= 100000) {
    const floored = Math.floor(value / 1000) / 10;
    return `-${floored.toFixed(1)}만`; 
  }
  return `-${value.toLocaleString()}`;
};