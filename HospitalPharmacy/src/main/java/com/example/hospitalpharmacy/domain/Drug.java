package com.example.hospitalpharmacy.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(name = "drugs")
public class Drug extends Entity<Integer>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int quantity;


    public Drug() {
    }

    public Drug(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int stock) {
        this.quantity = stock;
    }

    @Override
    public Integer getId() {
        super.setId(id);
        return super.getId();
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
        super.setId(id);
    }
}
