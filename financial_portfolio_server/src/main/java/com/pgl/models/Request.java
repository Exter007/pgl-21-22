package com.pgl.models;


import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/** Class that represent a request
 *
 */
@MappedSuperclass
public class Request extends Persistent{

    @Column(name = "status", nullable=false)
    private REQUEST_STATUS status;

    /** Default constructor
     * (persistent classes requirements)
     */
    public Request() {
    }

    /** Class constructor
     *
     * @param status a Request.REQUEST_STATUS enum
     */
    public Request(REQUEST_STATUS status) {
        this.status = status;
    }

    /** Get the status of this request
     *
     * @return the status in the form of a Request.REQUEST_STATUS enum
     */
    public REQUEST_STATUS getStatus() {
        return status;
    }

    /**Set the status of this request
     *
     * @param status a Request.REQUEST_STATUS enum
     */
    public void setStatus(REQUEST_STATUS status) {
        this.status = status;
    }

    /** Represent the status of a request
     */
    public enum REQUEST_STATUS{
        PENDING,
        ACCEPTED,
        REFUSED
    }
}
