package com.jjangiji.hankkimoa.common;

import com.jjangiji.hankkimoa.common.JpaAuditingTest.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends JpaRepository<TestEntity, Long> {
}
