export const formatExpenseAmount = (value: number): string => {
    if (value >= 100000) {
      return `-${Math.floor(value / 10000)}만`;
    }
    return `-${value.toLocaleString()}`;
  };
  