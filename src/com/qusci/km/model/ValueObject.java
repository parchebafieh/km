package com.qusci.km.model;

import java.io.Serializable;

/**
 * Created by Pedram Parchebafieh
 * User: pedram
 * Date: 11/3/11
 * Time: 4:26 PM
 */
public class ValueObject implements Serializable {

    static final short UNSAVED_VALUE = -1;

    long id = UNSAVED_VALUE;
    short status;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }


    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof ValueObject))
            return false;
        ValueObject vo = (ValueObject) obj;

        if (this.getClass().isInstance(obj))
            return vo.getId() == this.getId();
        else
            return false;
    }

    public int hashCode() {
        return (int) ((id ^ (id >>> 32)));
    }

}
