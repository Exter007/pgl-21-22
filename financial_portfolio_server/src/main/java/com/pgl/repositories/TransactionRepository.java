package com.pgl.repositories;

import com.pgl.models.Transaction;
import org.springframework.data.repository.CrudRepository;

/** Interface that manage query that return Transaction or list of them
 */
public interface TransactionRepository extends CrudRepository<Transaction, String> {
}
