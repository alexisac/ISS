package com.example.hospitalpharmacy.services;

import com.example.hospitalpharmacy.domain.Drug;
import com.example.hospitalpharmacy.domain.DrugOrdered;
import com.example.hospitalpharmacy.domain.Validate;
import com.example.hospitalpharmacy.repository.RepoDrugOrdered;

import java.util.Vector;

public class ServiceDrugOrdered {

    RepoDrugOrdered rd;
    Validate v;

    public ServiceDrugOrdered(RepoDrugOrdered rd, Validate v) {
        this.rd = rd;
        this.v = v;
    }

    public void addInTempList(Drug d){
        System.out.println("SERV: " + d.getId() + " | " + d.getName() + " | " + d.getQuantity());
        rd.addInTempList(d);
    }

    public void delFromTempList(Drug d){
        rd.delFromTempList(d);
    }

    public Vector<Drug> getAllTempList(){
        return rd.getAllTempList();
    }

    public void addDrugOrder(int idOrder){
        rd.addDrugOrder(idOrder);
    }

    public Vector<DrugOrdered> findAll(int idOrder){
        return rd.findAll(idOrder);
    }

    public boolean processOrder(int idOrder){
        return rd.processOrder(idOrder);
    }

    public void delete(int idOrder){
        rd.delete(idOrder);
    }
}
