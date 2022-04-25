package com.pgl.repositories;

import com.pgl.models.BankAccount;
import com.pgl.models.FinancialProduct;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/** Interface that manage query that return bank account or list of them
 */
public interface BankAccountRepository extends CrudRepository<BankAccount, Long> {
}
