package com.example.hospitalpharmacy.domain;

import javax.persistence.*;
import java.util.Date;

@javax.persistence.Entity
@Table(name = "orders")
public class MyOrder extends Entity<Integer>{
    public enum orderStatus {ACCEPTED, DENIED, BEING_PROCESSED}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int wardId;
    private orderStatus status;
    @Temporal(TemporalType.DATE)
    private Date dateTime;

    public MyOrder() {
    }

    public MyOrder(int wardId, orderStatus status, Date dateTime) {
        this.wardId = wardId;
        this.status = status;
        this.dateTime = dateTime;
    }

    public int getWardId() {
        return wardId;
    }

    public void setWardId(int wardId) {
        this.wardId = wardId;
    }

    public orderStatus getStatus() {
        return status;
    }

    public void setStatus(orderStatus status) {
        this.status = status;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public Integer getId() {
        super.setId(id);
        return super.getId();
    }

    @Override
    public void setId(Integer id) {
        super.setId(id);
    }
}
