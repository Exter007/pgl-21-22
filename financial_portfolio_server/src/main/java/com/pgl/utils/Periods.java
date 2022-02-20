package com.pgl.utils;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.util.Date;

@Embeddable
public class Periods {
    /**
     * The beginning of the period.
     * The start date must be NULL or earlier than end date.
     *
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_date")
    @JsonSerialize(using = JsonDateTimeSerializer.class)
    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
    private Date start;

    /**
     * The ending of the period.
     * The end date must be NULL or after end date.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_date")
    @JsonSerialize(using = JsonDateTimeSerializer.class)
    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
    private Date end;

    @Transient
    private String comparator;
    /**
     * Default constructor.
     */
    public Periods() {
        super();
    }

    /**
     * Build new instance of <code>Period</code> with start and end dates.
     *
     * @param start the first date of the period
     * @param end the last date of the period
     */
    public Periods(Date start, Date end) {
        this();
        this.start = start;
        this.end = end;
    }

    /**
     *
     * @return the period's beginning date.
     */
    public Date getStart() {
        return start;
    }

    /**
     * Sets the period's beginning date
     *
     * @param start the start date to set
     */
    public void setStart(Date start) {
        this.start = start;
    }

    /**
     * @return the period's ending date.
     */
    public Date getEnd() {
        return end;
    }

    /**
     * Sets the period's ending date;
     *
     * @param end the end date to set
     */
    public void setEnd(Date end) {
        this.end = end;
    }


    /**
     * Is this period valid?
     * <p>
     * A period is valid if start comes earlier than end.
     *
     * @return <code>TRUE</code> the period is valid;
     * 		   <code>FALSE</code> otherwise.
     */
    public boolean isValid() {
        if(this.start != null && this.end != null) {
            return this.start.compareTo(this.end) <= 0;
        }
        return true;
    }

    @Override
    public String toString() {
        String label = this.start != null ? "[" + this.start.toString() : "]...";
        label += ", ";
        label += this.end != null ? this.end.toString() + "]" : "...[";
        return label;
    }

    public void setComparator(String comparator) {
        this.comparator = comparator;
    }

    public String getComparator() {
        return comparator;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((comparator == null) ? 0 : comparator.hashCode());
        result = prime * result + ((end == null) ? 0 : end.hashCode());
        result = prime * result + ((start == null) ? 0 : start.hashCode());
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
        Periods other = (Periods) obj;
        if (comparator == null) {
            if (other.comparator != null)
                return false;
        } else if (!comparator.equals(other.comparator))
            return false;
        if (end == null) {
            if (other.end != null)
                return false;
        } else if (!end.equals(other.end))
            return false;
        if (start == null) {
            if (other.start != null)
                return false;
        } else if (!start.equals(other.start))
            return false;
        return true;
    }
}
