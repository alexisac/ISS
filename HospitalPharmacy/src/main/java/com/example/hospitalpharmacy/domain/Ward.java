package com.example.hospitalpharmacy.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(name = "wards")
public class Ward extends Entity<Integer>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    public Ward() {
    }

    public Ward(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
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
