package com.example.hospitalpharmacy.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(name = "doctors")
public class Doctor extends Entity<Integer>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private int code;
    private int wardId;

    public Doctor() {
    }

    public Doctor(String name, int code, int wardId) {
        this.name = name;
        this.code = code;
        this.wardId = wardId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getWardId() {
        return wardId;
    }

    public void setWardId(int wardId) {
        this.wardId = wardId;
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
