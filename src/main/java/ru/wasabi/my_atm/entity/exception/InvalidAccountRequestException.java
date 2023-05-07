package ru.wasabi.my_atm.entity.exception;

public class InvalidAccountRequestException extends RuntimeException{
    public InvalidAccountRequestException(String message) {
        super(message);
    }
}
