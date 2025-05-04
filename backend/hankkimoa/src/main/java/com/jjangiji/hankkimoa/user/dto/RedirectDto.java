package com.jjangiji.hankkimoa.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RedirectDto {
    private String code; //인가코드
}
