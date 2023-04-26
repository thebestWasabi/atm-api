package ru.wasabi.my_atm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.wasabi.my_atm.entity.Account;

import java.math.BigDecimal;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Modifying
    @Query(value = "update account set amount = :amount where id = :id", nativeQuery = true)
    void changeBalance(Long id, BigDecimal amount);
}
