package com.jjangiji.hankkimoa.user.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class RefreshToken {
    @Id
    public String socialId;

    public String refreshToken;
    public int expiresIn;
}
