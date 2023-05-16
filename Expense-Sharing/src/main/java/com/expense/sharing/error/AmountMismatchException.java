package com.expense.sharing.error;

public class AmountMismatchException extends RuntimeException{

    public AmountMismatchException(String msg) {
        super(msg);
    }
}
