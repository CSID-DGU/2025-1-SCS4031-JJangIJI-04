package com.jjangiji.hankkimoa.expense.domain;

import com.jjangiji.hankkimoa.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class ExpenseSavingGoal extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // todo userEntity 매핑

    @NotNull(message = "예산이 NULL일 수 없습니다.")
    private Integer budget;

    @NotNull(message = "시작일이 NULL일 수 없습니다.")
    private LocalDate startDate;

    @NotNull(message = "종료일이 NULL일 수 없습니다.")
    private LocalDate endDate;

    public ExpenseSavingGoal(Long id, Integer budget, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.budget = budget;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public ExpenseSavingGoal(Integer budget, LocalDate startDate, LocalDate endDate) {
        this.budget = budget;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
