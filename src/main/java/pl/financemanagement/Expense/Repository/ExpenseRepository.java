package pl.financemanagement.Expense.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.financemanagement.Expense.Model.Entity.Expense;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    @Query(nativeQuery = true, value = """
            SELECT *
            FROM app.expense
            WHERE external_id = :externalId AND user_id = :userId""")
    Optional<Expense> findExpenseByExternalIdAndUserId(@Param("externalId") UUID externalId,
                                                       @Param("userId") long userId);


    @Query(nativeQuery = true, value = """
            SELECT *
            FROM app.expense
            WHERE user_id = :userId""")
    List<Expense> findExpensesByUserId(@Param("userId") long userId);

}
