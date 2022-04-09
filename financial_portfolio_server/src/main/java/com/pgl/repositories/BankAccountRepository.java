package com.pgl.repositories;

import com.pgl.models.BankAccount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BankAccountRepository extends CrudRepository<BankAccount, Long> {
}
