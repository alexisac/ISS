package com.example.hospitalpharmacy.services;
import com.example.hospitalpharmacy.domain.Doctor;
import com.example.hospitalpharmacy.domain.Drug;
import com.example.hospitalpharmacy.domain.DrugOrdered;
import com.example.hospitalpharmacy.domain.Validate;
import com.example.hospitalpharmacy.repository.RepoDrug;
import java.util.Vector;

public class ServiceDrug {
    RepoDrug rDrug;
    Validate v;


    public ServiceDrug(RepoDrug rDrug, Validate v) {
        this.rDrug = rDrug;
        this.v = v;
    }
    public Vector<Drug> findAll(){
        return rDrug.findAll();
    }
    public void addDrug(String name, int quantity) throws ServiceException{
        if(v.validQuantity(quantity) && v.validName(name)){
            Drug d = new Drug(name, quantity);
            rDrug.add(d);
        } else{
            if(!v.validQuantity(quantity) && !v.validName(name)){
                throw new ServiceException("The name and quantity are wrong!");
            } else if(!v.validQuantity(quantity)){
                throw new ServiceException("The quantity is wrong!");
            } else {
                throw new ServiceException("The name is wrong!");
            }
        }
    }

    public Drug findOne(int idDrug){
        return rDrug.findOne(idDrug);
    }

    public int idDrug(Drug d){
        return rDrug.idDrug(d);
    }
}
