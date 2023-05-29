package com.example.hospitalpharmacy.services;
import com.example.hospitalpharmacy.domain.Doctor;
import com.example.hospitalpharmacy.domain.Validate;
import com.example.hospitalpharmacy.repository.RepoDoctor;

public class ServiceDoctor {
    RepoDoctor rDoctor;
    Validate v;



    public ServiceDoctor(RepoDoctor rDoctor, Validate v) {
        this.rDoctor = rDoctor;
        this.v = v;
    }



    public int idDoctor(String name, int code, int wardId){
        return rDoctor.idDoctor(new Doctor(name, code, wardId));
    }
    public void addDoctor(String name, int code, int codfirmedCode, int wardId) throws ServiceException{
        if(code == codfirmedCode){
            if(v.validCode(code) && v.validName(name)){
                Doctor d = new Doctor(name, code, wardId);
                rDoctor.add(d);
            } else{
                if(!v.validCode(code) && !v.validName(name)){
                    throw new ServiceException("The name and code are wrong!");
                } else if(!v.validCode(code)){
                    throw new ServiceException("The code is wrong!");
                } else {
                    throw new ServiceException("The name is wrong!");
                }
            }
        } else {
            throw new ServiceException("The two codes aren't identical");
        }

    }

}
