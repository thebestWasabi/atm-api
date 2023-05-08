package ru.wasabi.my_atm.entity.transaction;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public enum TransactionType {
    DEPOSIT,
    WITHDRAWAL,
    TRANSFER
}
