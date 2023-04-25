package ru.wasabi.my_atm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.wasabi.my_atm.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
}
