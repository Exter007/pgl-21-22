package com.pgl.models;


import javax.persistence.*;
import java.util.Date;

/** Class that represent a transaction with a bank account available in this application
 *
 */

@Entity
public class ScheduledTransaction extends Transaction{

    @Column(name = "every")
    private String every;

    @Column(name = "inferior_at")
    private float inferiorAt;

    @Column(name = "superior_at")
    private float superiorAt;

    /** Default constructor
     * (persistent classes requirements)
     */
    public ScheduledTransaction() {
    }

    /** Constructor with parameters
     *
     * @param transaction the transaction linked to this scheduled transaction
     * @param every a String object
     * @param inferiorAt a float
     * @param superiorAt a float
     */
    public ScheduledTransaction(Transaction transaction, String every, float inferiorAt, float superiorAt) {
        super(transaction.getTransactionNumber(), transaction.getType(), transaction.getBankAccount(), transaction.getDestinationIBAN(), transaction.getDestinationName(), transaction.getAmount(), transaction.getCommunication_type(), transaction.getCommunication(), transaction.getDate(), transaction.getStatus(), transaction.getFormulation());
        this.every = every;
        this.inferiorAt = inferiorAt;
        this.superiorAt = superiorAt;
    }

    public String getEvery() {
        return every;
    }

    public void setEvery(String every) {
        this.every = every;
    }

    public float getInferiorAt() {
        return inferiorAt;
    }

    public void setInferiorAt(float inferiorAt) {
        this.inferiorAt = inferiorAt;
    }

    public float getSuperiorAt() {
        return superiorAt;
    }

    public void setSuperiorAt(float superiorAt) {
        this.superiorAt = superiorAt;
    }
}
