package com.pgl.models;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "classe")
public class Persistent extends PersistentWithoutId {

    /**
     * Unique ID of this persistent object.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;

    /**
     * The object ID. This attribute is null if the object is not yet persisted.
     *
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @param id
     *            the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "[" + getClass().getSimpleName() + " : " + getId() + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((classe == null) ? 0 : classe.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Persistent other = (Persistent) obj;
        if (classe == null) {
            if (other.classe != null)
                return false;
        } else if (!classe.equals(other.classe))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
