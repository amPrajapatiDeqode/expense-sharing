package com.expense.sharing.service;

import com.expense.sharing.error.AmountMismatchException;
import com.expense.sharing.error.InvalidSplitType;
import com.expense.sharing.model.Debt;
import com.expense.sharing.repository.DebtRepository;
import com.expense.sharing.repository.ExpenseSharingRepository;
import com.expense.sharing.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ExpenseSharingService {

    @Autowired
    private ExpenseSharingRepository expenseSharingRepository;
    @Autowired
    private DebtRepository debtRepository;

    public User addUser(User user) {
        expenseSharingRepository.save(user);
        return user;
    }


    @Transactional
    public void addExpense(String command) {
        command.trim();
        String[] values = command.split(" ");

        int index=0;
        String payeeId = values[index++];
        int payedAmount = Integer.valueOf(values[index++]);
        int numberOfSplits = Integer.valueOf(values[index++]);

        String[][] splitArray = new String[numberOfSplits][2];
        for(int i=0; i<numberOfSplits; i++)
            splitArray[i][0] = values[index++];

        String splitType = values[index++];
        if(splitType.equals("EQUAL")) {
            for(int i=0; i<numberOfSplits; i++) {
                splitArray[i][1] = String.valueOf(payedAmount/numberOfSplits);
            }
            addBorrowingToBorrowersOperation(payeeId, payedAmount, splitArray);
        } else if(splitType.equals("EXACT")) {
            int totalSplitAmount = 0;
            for(int i=0; i<numberOfSplits; i++) {
                totalSplitAmount += Integer.valueOf(values[index]);
                splitArray[i][1] = values[index++];
            }

            if(totalSplitAmount != payedAmount) {
                throw new AmountMismatchException("Total amount and split amount does not match...");
            }

            addBorrowingToBorrowersOperation(payeeId, payedAmount, splitArray);
        } else {
            throw new InvalidSplitType("Invalid split type...");
        }
    }

    private void addBorrowingToBorrowersOperation(String payeeId, int payedAmount, String[][] splitArray) {
        for(int i=0; i< splitArray.length; i++) {
            if(payeeId.equals(splitArray[i][0]))
                continue;
            Debt debt = findByBorrowerAndLender(payeeId, splitArray[i][0]);
            if(debt == null) {
                debtRepository.save(Debt.
                        builder().
                        lender(payeeId).
                        borrower(splitArray[i][0]).
                        amount(Integer.valueOf(splitArray[i][1])).
                        build()
                );
            } else {
                int amount = Integer.valueOf(splitArray[i][1]) + debt.getAmount();
                debtRepository.updateAmountByBorrowerAndLender(payeeId, splitArray[i][0], amount);
            }
        }
    }

    public Debt findByBorrowerAndLender(String lender, String borrower) {
        Debt debt = debtRepository.findByBorrowerAndLender(lender, borrower);
        return debt;
    }

    public List<Debt> findAllDebts() {
        return debtRepository.findAll();
    }

    public List<Debt> findAllDebtsByLenderId(String lenderId) {
        return debtRepository.findLendingsByLenderId(lenderId);
    }

}
