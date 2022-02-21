package com.pgl.repositories;

import com.pgl.models.FinancialInstitution;
import org.springframework.data.repository.CrudRepository;

public interface FinancialInstitutionRepository extends CrudRepository<FinancialInstitution, String> {
}
