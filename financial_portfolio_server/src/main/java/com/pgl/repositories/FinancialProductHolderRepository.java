package com.pgl.repositories;

import com.pgl.models.FinancialProductHolder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/** Interface that manage query that return financial product holder or list of them
 */
public interface FinancialProductHolderRepository extends CrudRepository<FinancialProductHolder, Long> {

    /** Find financial product holders related to a financial institution in the database
     * by using the institution BIC code
     *
     * @param BIC the BIC code
     * @return the list of financial product holders found or an empty list
     */
    @Query("SELECT r FROM FinancialProductHolder r where r.financialInstitution.BIC=:b ")
    List<FinancialProductHolder> findHoldersByInstitution(@Param("b")String BIC);

    /** Find a financial product holder related to a financial institution in the database
     * by using the institution BIC code and his national register number
     *
     * @param BIC the BIC code
     * @param nationalRegister the national register number
     * @return the financial product holder found or null
     */
    @Query("SELECT r FROM FinancialProductHolder r where r.financialInstitution.BIC=:b and r.nationalRegister=:n ")
    FinancialProductHolder findHolderByInstitutionAndClient(@Param("b")String BIC, @Param("n") String nationalRegister);
}
