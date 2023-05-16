package com.expense.sharing.controller;

import com.expense.sharing.model.Debt;
import com.expense.sharing.service.ExpenseSharingService;
import com.expense.sharing.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expense")
public class ExpenseSharingController {

    @Autowired
    private ExpenseSharingService expenseSharingService;

    @GetMapping("/hello")
    public String hello() {
        return "Hello from expense sharing app...";
    }

    @PostMapping("/add/user")
    public User addUser(@RequestBody User user) {
        return expenseSharingService.addUser(user);
    }

    @PostMapping("/add/expense")
    public void addExpense(@RequestBody String command) {
        expenseSharingService.addExpense(command);
    }

    @GetMapping("/show")
    public List<Debt> findAllDebts() {
        return expenseSharingService.findAllDebts();
    };

    @GetMapping("/show/{lenderId}")
    public List<Debt> findAllDebts(@PathVariable String lenderId) {
        return expenseSharingService.findAllDebtsByLenderId(lenderId);
    };
}
