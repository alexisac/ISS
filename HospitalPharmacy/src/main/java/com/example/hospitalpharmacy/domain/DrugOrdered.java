package com.example.hospitalpharmacy.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@javax.persistence.Entity
@Table(name = "drugsOrdered")
public class DrugOrdered extends Entity<Integer>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int orderId;
    private int drugId;
    private int quantity;

    public DrugOrdered() {
    }

    public DrugOrdered(int orderId, int drugId, int quantity) {
        this.orderId = orderId;
        this.drugId = drugId;
        this.quantity = quantity;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getDrugId() {
        return drugId;
    }

    public void setDrugId(int drugId) {
        this.drugId = drugId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
