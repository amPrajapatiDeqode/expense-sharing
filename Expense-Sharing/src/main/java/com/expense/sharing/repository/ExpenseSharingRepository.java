package com.expense.sharing.repository;

import com.expense.sharing.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseSharingRepository extends JpaRepository<User, String> {
}
