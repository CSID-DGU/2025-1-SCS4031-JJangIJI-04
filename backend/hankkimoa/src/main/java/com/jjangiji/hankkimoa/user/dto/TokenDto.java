package com.jjangiji.hankkimoa.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenDto {
    private String access_token;
    private String refresh_token;
    private String tokenType;
    private long expires_in;
}
