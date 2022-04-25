package com.pgl.repositories;

import com.pgl.models.ScheduledTransaction;
import org.springframework.data.repository.CrudRepository;

/** Interface that manage query that return ScheduledTransaction or list of them
 */
public interface ScheduledTransactionRepository  extends CrudRepository<ScheduledTransaction, String> {
}
