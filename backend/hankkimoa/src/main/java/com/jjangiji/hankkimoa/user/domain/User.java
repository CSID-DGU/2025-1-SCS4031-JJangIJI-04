package com.jjangiji.hankkimoa.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@Getter
@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nickname;

    private String socialId;

    public User(Long id, String nickname, String socialId) {
        this.id = id;
        this.nickname = nickname;
        this.socialId = socialId;
    }

    public User(String nickname, String socialId) {
        this.nickname = nickname;
        this.socialId = socialId;
    }
}
