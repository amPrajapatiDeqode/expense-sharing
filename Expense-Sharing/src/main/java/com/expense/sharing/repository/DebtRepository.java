package com.expense.sharing.repository;

import com.expense.sharing.model.Debt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DebtRepository extends JpaRepository<Debt, Long> {

    @Query("Select d from Debt d where d.lender=:lender and d.borrower=:borrower")
    Debt findByBorrowerAndLender(@Param("lender") String lender, @Param("borrower") String borrower);

    @Modifying
    @Query("Update Debt d set d.amount = :amount where d.lender=:lender and d.borrower=:borrower")
    void updateAmountByBorrowerAndLender(@Param("lender") String lender, @Param("borrower") String borrower, @Param("amount") int amount);

    @Query("Select d from Debt d where d.lender=:lender")
    List<Debt> findLendingsByLenderId(@Param("lender") String lenderId);
}
