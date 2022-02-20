package com.pgl.services;

import com.pgl.models.Persistent;
import com.pgl.repositories.BaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class BaseService<P extends Persistent> {

    protected Logger logger;

    @Autowired
    EntityManager entityManager;

    public BaseService() {
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    /**
     * return the autowired repository for persistent object
     *
     * @return BaseRepository
     *         <P>
     */
    protected abstract BaseRepository<P> getRepository();

    /**
     * Save the persistent entity outside of a transaction
     *
     * @param persistent
     *            to save
     *
     * @return persistent saved
     * @throws Exception
     *
     * @pre persistent !=null and don't exist in database
     *
     * @post the insertion of persistent is made but not flush
     */
    public P saveWithoutCommit(P persistent) throws Exception {
        if (persistent.getId() == null) { // new entity to save
            //if (persistent.getCreationDate() == null) {
            persistent.setCreationDate(new Date());
            //}
        } else { // update entity, the last modification erased others
            P other = getRepository().findOne(persistent.getId());
            if (other != null) {
                persistent.setModificationDate(other.getModificationDate());
            }
        }
        persistent.toAddOrUpdate = false;
        persistent.toDelete = false;

        try {
            persistent = getRepository().save(persistent);
        } catch(Exception ex) {
            if(ex instanceof DataIntegrityViolationException) {
                throw new Exception("save.fail.data.integrity.violation.error");
            }
            throw ex;
        }
        return persistent;
    }
    /**
     * Save the persistent entities outside of a transaction
     *
     * @param entities to save
     *
     * @return persistent saved
     * @throws Exception
     *
     * @pre persistent !=null and don't exist in database
     *
     * @post the insertion of persistent is made but not flush
     */
    public List<P> saveWithoutCommit(List<P> entities) throws Exception {
        List<P> entitiesSaved = new ArrayList<>(0);
        for(P entity : entities){
            entitiesSaved.add(saveWithoutCommit(entity));
        }
        return entitiesSaved;
    }

    /**
     * Save the persistent entity in database
     *
     * @param persistent
     *            entity saved
     *
     * @return persistent saved
     * @throws Exception
     *
     * @pre persistent !=null and don't exist in database
     *
     * @post insert persistent is made and flush in the database
     */
    @Async
    @Transactional(readOnly = false)
    public P save(P persistent) throws Exception {
        return saveWithoutCommit(persistent);
    }

    /**
     * Save the persistent entity in database
     *
     * @param persistent
     *            entity saved
     *
     * @return persistent saved
     * @throws Exception
     *
     * @pre persistent !=null and don't exist in database
     *
     * @post insert persistent is made and flush in the database
     */
    @Transactional(readOnly = false)
    public List<P> save(List<P> persistent) throws Exception {
        return saveWithoutCommit(persistent);
    }

    /**
     * Delete persistent outside of a transaction
     *
     * @param persistent
     *            to delete
     * @throws Exception
     *
     * @pre persistent != null and exist in database
     *
     * @post persistent delete operation is performed but not commit in database
     */
    public void deleteWithoutCommit(P persistent) throws RuntimeException {
        try {
            getRepository().delete(persistent);
        } catch(Exception ex) {
            if(ex instanceof DataIntegrityViolationException ) {
                throw new RuntimeException("delete.fail.data.integrity.violation.error");
            }
            throw ex;
        }
    }

    /**
     * Delete persistent in database
     *
     * @param persistent
     *            to delete
     * @throws Exception
     *
     * @pre persistent != null and exist in database
     *
     * @post persistent definitively delete from database
     */
    @Transactional(readOnly = false, noRollbackFor = {RuntimeException.class})
    public void delete(P persistent) throws RuntimeException {
        deleteWithoutCommit(persistent);
    }

    /**
     * Delete an entity of identifier id outside of a transaction
     *
     * @param id
     *            identifier of entity to delete
     *
     * @pre id != null and entity with this id exist in database
     *
     * @post delete operation of entity with identifier id is not commit to
     *       database
     */
    public void deleteWithoutCommit(Long id)  throws RuntimeException {
        try {
            getRepository().delete(id);
        } catch(Exception ex) {
            if(ex instanceof DataIntegrityViolationException ) {
                throw new RuntimeException("delete.fail.data.integrity.violation.error");
            }
            throw ex;
        }
    }

    /**
     * Delete an entity of identifier id from the database
     *
     * @param id
     *            identifier of entity to delete
     * @throws Exception
     *
     * @pre id != null and entity with this id exist in database
     *
     * @post delete entity with identifier id from the database
     */
    @Transactional(readOnly = false)
    public void delete(Long id)  throws RuntimeException {
        deleteWithoutCommit(id);
    }


    /**
     * Get an entity by is identifier
     *
     * @param id
     *            identifier of the entity to return
     *
     * @pre id != null
     *
     * @return an entity such as entity.id = id
     */
    public P findById(Long id) {
        return getRepository().findOne(id);
    }

    /**
     * Get the list of all entities
     *
     * @return a list of all entities
     */
    public List<P> findAll() {
        return getRepository().findAll();
    }

    /**
     * Get the list of all entities
     *
     * @return a list of all entities
     */
    public Page<P> findAll(Pageable pageable) {
        return getRepository().findAll(pageable);
    }


    /**
     * Override this to return the good entity manager
     * @return
     */
    public EntityManager getEntityManager() {
        return entityManager;
    }

}
