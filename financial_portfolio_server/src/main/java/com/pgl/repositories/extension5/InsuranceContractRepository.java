package com.pgl.repositories.extension5;

import com.pgl.models.FinancialProduct;
import com.pgl.models.extension5.InsuranceContract;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InsuranceContractRepository extends CrudRepository<InsuranceContract, Long> {

    @Query("SELECT r FROM InsuranceContract r where r.insuranceNumber=:i and r.productType =:t")
    InsuranceContract findInsuranceContractByNumber(@Param("i")String insuranceNumber, @Param("t") FinancialProduct.PRODUCT_TYPE insuranceProductType);

    @Query("SELECT r FROM InsuranceContract r where r.financialInstitution.BIC=:f and r.productType =:t")
    List<InsuranceContract> findInsuranceContractByInstitution(@Param("f") String bic, @Param("t") FinancialProduct.PRODUCT_TYPE insuranceProductType);
}
