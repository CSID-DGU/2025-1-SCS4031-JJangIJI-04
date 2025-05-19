package com.jjangiji.hankkimoa.expense.repository;

import com.jjangiji.hankkimoa.expense.domain.ExpenseSavingGoal;
import com.jjangiji.hankkimoa.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDate;
import java.util.Optional;

public interface ExpenseSavingGoalRepository extends JpaRepository<ExpenseSavingGoal, Long> {

    @Query("SELECT e FROM ExpenseSavingGoal e "
            + "WHERE e.user = :user ORDER BY e.createdAt DESC limit 1 ")
    Optional<ExpenseSavingGoal> findLastByUser(User user);

    @Query("SELECT e FROM ExpenseSavingGoal e " +
            "WHERE :date BETWEEN e.startDate AND e.endDate")
    Optional<ExpenseSavingGoal> findByDate(LocalDate date);
}
