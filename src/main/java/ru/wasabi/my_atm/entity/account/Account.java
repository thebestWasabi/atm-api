package ru.wasabi.my_atm.entity.account;

import lombok.*;

import jakarta.persistence.*;
import ru.wasabi.my_atm.entity.transaction.OperationList;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "balance")
    private BigDecimal balance;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<OperationList> operationListList;

//    public Account(String email) {
//        this.email = email;
//    }
//
//    public Account(Long id, String email) {
//        this.id = id;
//        this.email = email;
//    }
//
//    public Account(String email, BigDecimal balance) {
//        this.email = email;
//        this.balance = balance;
//    }
}
