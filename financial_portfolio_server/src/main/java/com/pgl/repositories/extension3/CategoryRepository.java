package com.pgl.repositories.extension3;

import com.pgl.models.ApplicationClient;
import com.pgl.models.extension3.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    @Query("SELECT c FROM Category c WHERE c.name=:name")
    Category findByName(@Param("name") String name);

    @Query("SELECT c FROM Category c WHERE c.applicationClient.nationalRegister=:applicationClient")
    List<Category> findByApplicationClient(@Param("applicationClient") String applicationClient);
}