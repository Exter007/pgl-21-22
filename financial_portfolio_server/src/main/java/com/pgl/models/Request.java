package com.pgl.models;


import javax.persistence.Column;
import javax.persistence.MappedSuperclass;


@MappedSuperclass
public class Request extends Persistent{

    @Column(name = "status", nullable=false)
    private REQUEST_STATUS status;

    public Request(REQUEST_STATUS status) {
        this.status = status;
    }

    public REQUEST_STATUS getStatus() {
        return status;
    }

    public void setStatus(REQUEST_STATUS status) {
        this.status = status;
    }

    public enum REQUEST_STATUS{
        PENDING,
        ACCEPTED,
        REFUSED
    }
}
