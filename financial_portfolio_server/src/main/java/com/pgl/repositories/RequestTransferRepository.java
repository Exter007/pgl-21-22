package com.pgl.repositories;

import com.pgl.models.RequestTransfer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface RequestTransferRepository extends CrudRepository<RequestTransfer, String> {
    @Modifying
    @Query("UPDATE RequestTransfer r set r.status=:s where r.id=:id")
    void updateRequestedTransfer(@Param("id") Long id, @Param("s") int status);
}
