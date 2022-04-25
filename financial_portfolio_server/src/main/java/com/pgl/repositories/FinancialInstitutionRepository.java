package com.pgl.repositories;

import com.pgl.models.FinancialInstitution;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/** Interface that manage query that return financial institution or list of them
 */
public interface FinancialInstitutionRepository extends CrudRepository<FinancialInstitution, String> {

    /** Find a financial institution in the database
     * by using its login
     *
     * @param login the login
     * @return the financial institution found or null
     */
    @Query("SELECT r FROM FinancialInstitution r where r.login=:l")
    FinancialInstitution findByLogin(@Param("l") String login);

    /** Find a financial institution in the database
     * by using its BIC code and email
     *
     * @param bic the BIC code
     * @param email the email
     * @return the financial institution found or null
     */
    @Query("SELECT r FROM FinancialInstitution r where r.BIC=:b or r.email=:e")
    FinancialInstitution findByBicOrEmail(@Param("b") String bic, @Param("e") String email);
}
