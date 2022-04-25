package com.pgl.repositories;

import com.pgl.models.Address;
import org.springframework.data.repository.CrudRepository;

/** Interface that manage query that return address or list of them
 */
public interface AddressRepository extends CrudRepository<Address, String> {
}
