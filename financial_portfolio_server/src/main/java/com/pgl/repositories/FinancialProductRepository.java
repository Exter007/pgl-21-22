package com.pgl.repositories;

import com.pgl.models.FinancialProduct;
import org.springframework.data.repository.CrudRepository;

public interface FinancialProductRepository extends CrudRepository<FinancialProduct, Long> {
}
