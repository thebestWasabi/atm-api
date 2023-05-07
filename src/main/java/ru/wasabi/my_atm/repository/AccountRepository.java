package ru.wasabi.my_atm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.wasabi.my_atm.entity.Account;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByEmail(String email);

//    @Query(nativeQuery = true, value = "update account set email = :email where id = :id")
//    void update(String email, Long id);

    @Modifying
    @Query(value = "update account set balance = :balance where id = :id", nativeQuery = true)
    void changeBalance(Long id, BigDecimal balance);

}
