package com.jjangiji.hankkimoa.expense.repository;

import com.jjangiji.hankkimoa.expense.domain.ExpenseSavingGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ExpenseSavingGoalRepository extends JpaRepository<ExpenseSavingGoal, Long> {

    @Query("SELECT e FROM ExpenseSavingGoal e "
            + "WHERE e.user.id = :userId ORDER BY e.createdAt DESC limit 1 ")
    Optional<ExpenseSavingGoal> findLastByUser(@Param("userId") Long userId);

    @Query("SELECT e FROM ExpenseSavingGoal e " +
            "WHERE e.user.id = :userId AND :date BETWEEN e.startDate AND e.endDate")
    Optional<ExpenseSavingGoal> findByUserAndDate(@Param("userId") Long userId,
                                                  @Param("date") LocalDate date);

    @Query(value = """ 
            SELECT esg.*
            FROM expense_saving_goal esg
            JOIN expense e ON e.expense_saving_goal_id = esg.id
            JOIN (
                    SELECT
                    esg.user_id,
                    MAX(e.created_at) as created_at
                    FROM expense e
                    JOIN expense_saving_goal esg on esg.id = e.expense_saving_goal_id
                    GROUP BY esg.user_id
            ) latest ON esg.user_id = latest.user_id and e.created_at = latest.created_at
            ORDER BY e.created_at DESC
            LIMIT :limit OFFSET :offset
            """, nativeQuery = true)
    List<ExpenseSavingGoal> findAllLastExpenseSavingGoalOrderByCreatedAtDESC(@Param("limit") Integer limit,
                                                                             @Param("offset") Integer offset);
}
