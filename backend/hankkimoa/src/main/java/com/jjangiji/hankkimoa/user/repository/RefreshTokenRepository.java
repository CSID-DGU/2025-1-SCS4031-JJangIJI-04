package com.jjangiji.hankkimoa.user.repository;

import com.jjangiji.hankkimoa.user.domain.RefreshToken;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
    @Transactional
    void deleteBySocialId(String socialId);
}
