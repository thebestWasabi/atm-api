package ru.wasabi.my_atm.entity.exception;

public class NotEnoughMoneyInAccount extends RuntimeException{
    public NotEnoughMoneyInAccount(String message) {
        super(message);
    }
}
