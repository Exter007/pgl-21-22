package com.pgl.repositories;

import com.pgl.models.PersistentWithoutId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public abstract interface BaseRepository<P extends PersistentWithoutId> extends JpaRepository<P, Long>, JpaSpecificationExecutor<P> {
    @Override
    @Query("SELECT t FROM #{#entityName} t ")
    public List<P> findAll();

    @Override
    @Query("SELECT t FROM #{#entityName} t ")
    public Page<P> findAll(Pageable page);

    @Query("SELECT t FROM #{#entityName} t WHERE t.id = ?1 ")
    public P findOne(Long id);

    @Override
    @Query("SELECT t FROM #{#entityName} t WHERE t.id = ?1 ")
    public  P getOne(Long id);

    @Modifying
    @Query("DELETE FROM #{#entityName}  WHERE id = ?1 ")
    public  void delete(Long id);
}
