package com.jjangiji.hankkimoa.expense.domain;

import jakarta.persistence.Column;
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
public class ExpenseSavingGoal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // todo userEntity 매핑

    @Column(nullable = false)
    private Integer budget;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
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
