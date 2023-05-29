package com.example.hospitalpharmacy.services;
import com.example.hospitalpharmacy.domain.Validate;
import com.example.hospitalpharmacy.domain.Ward;
import com.example.hospitalpharmacy.repository.RepoWard;

import java.util.Vector;

public class ServiceWard {
    RepoWard rWard;
    Validate v;


    public ServiceWard(RepoWard rWard, Validate v) {
        this.rWard = rWard;
        this.v = v;
    }




    public int idWard(String name){
        return rWard.idWard(new Ward(name));
    }
    public Ward findOne(int idWard){
        return rWard.findOne(idWard);
    }
    public Vector<Ward> findAll(){
        return rWard.findAll();
    }
}
