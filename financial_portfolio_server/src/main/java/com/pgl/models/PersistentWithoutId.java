package com.pgl.models;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@MappedSuperclass
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "classe")
public class PersistentWithoutId implements Serializable {
    private static final long serialVersionUID = 1L;

    @Transient
    public String classe = getClass().getSimpleName();


    /**
     * The creation date.
     * Date when this object is created.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date", nullable = false , updatable = false)
    protected Date creationDate;

    /**
     * The last modification date.
     * Date when the last modification of this object occurred.
     */
    @Version
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modification_date", nullable = false)
    protected Date modificationDate;

    /**
     * This field is used to indicate that the object must be create or update in DB.
     */
    @Transient
    public boolean toAddOrUpdate = false;

    /**
     * This field is used to indicate that the object must be deleted from DB.
     */
    @Transient
    public boolean toDelete = false;


    /**
     * Default constructor.
     */
    public PersistentWithoutId() {
        Calendar c = Calendar.getInstance();
        creationDate = new Timestamp(c.getTimeInMillis());
        modificationDate = new Timestamp(c.getTimeInMillis());
    }


    /**
     * The date of creation
     *
     * @return the creationDate
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * @param creationDate
     *            the creationDate to set
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setCreationDates(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * The date of last modification
     *
     * @return the modificationDate
     */
    public Date getModificationDate() {
        return modificationDate;
    }

    /**
     * @param modificationDate
     *            the modificationDate to set
     */
    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

}
