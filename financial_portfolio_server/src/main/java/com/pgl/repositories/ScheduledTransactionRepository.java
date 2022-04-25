package com.pgl.repositories;

import com.pgl.models.ScheduledTransaction;
import org.springframework.data.repository.CrudRepository;

public interface ScheduledTransactionRepository  extends CrudRepository<ScheduledTransaction, String> {
}
