package com.jjangiji.hankkimoa.expense.repository;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class ExpenseSavingGoalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // todo userEntity 매핑

    private Integer budget;

    private LocalDate startDate;

    private LocalDate endDate;

    public ExpenseSavingGoalEntity(Integer budget, LocalDate startDate, LocalDate endDate) {
        this.budget = budget;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
