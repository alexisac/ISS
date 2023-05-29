package com.example.hospitalpharmacy.services;

import com.example.hospitalpharmacy.domain.MyOrder;
import com.example.hospitalpharmacy.domain.Validate;
import com.example.hospitalpharmacy.repository.RepoOrder;

import java.util.Vector;

public class ServiceOrder {
    RepoOrder rOrder;
    Validate v;

    public ServiceOrder(RepoOrder rOrder, Validate v) {
        this.rOrder = rOrder;
        this.v = v;
    }


    public int idOrder(MyOrder o){
        return rOrder.idOrder(o);
    }
    public void addOrder(MyOrder o){
        rOrder.add(o);
    }

    public Vector<MyOrder> findAll(MyOrder.orderStatus status){
        return rOrder.findAll(status);
    }

    public void update(int id, MyOrder.orderStatus status){
        rOrder.update(id, status);
    }

    public void delete(int id){
        rOrder.delete(id);
    }
}
