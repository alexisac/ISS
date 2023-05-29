package com.example.hospitalpharmacy.repository;

import com.example.hospitalpharmacy.domain.Doctor;
import com.example.hospitalpharmacy.domain.Drug;
import com.example.hospitalpharmacy.domain.DrugOrdered;
import com.example.hospitalpharmacy.domain.MyOrder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Objects;
import java.util.Vector;

public class RepoDrugOrdered {
    Vector<Drug> vect = new Vector<Drug>();
    private final SessionFactory sessionFactory;

    public RepoDrugOrdered(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addInTempList(Drug d){
        System.out.println("REPO: " + d.getId() + " | " + d.getName() + " | " + d.getQuantity());
        boolean exists = false;
        Vector<Drug> salv = new Vector<Drug>();
        for (Drug drug : vect) {
            if (Objects.equals(drug.getId(), d.getId())) {
                drug.setQuantity(drug.getQuantity() + d.getQuantity());
                exists = true;
            }
            salv.add(drug);
        }
        if (!exists) {
            vect.add(d);
        } else {
            vect.clear();
            vect.addAll(salv);
        }
    }

    public void delFromTempList(Drug d){
        vect.removeIf(drug -> Objects.equals(drug.getId(), d.getId()));
    }

    public Vector<Drug> getAllTempList(){
        return vect;
    }

    public void addDrugOrder(int idOrder){
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                for (Drug d: vect) {
                    DrugOrdered dOrd = new DrugOrdered(idOrder, d.getId(), d.getQuantity());
                    session.save(dOrd);
                }
                tx.commit();
                vect.clear();
            } catch (RuntimeException ex){
                System.out.println("Eroare la inserare: " + ex);
                if(tx != null){
                    tx.rollback();
                }
            }
        }
    }

    public Vector<DrugOrdered> findAll(int idOrder){
        Vector<DrugOrdered> rez = new Vector<DrugOrdered>();
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                List<DrugOrdered> vect = session.createQuery("FROM DrugOrdered WHERE orderId = " + idOrder, DrugOrdered.class).list();
                rez.addAll(vect);
                tx.commit();
            } catch (RuntimeException ex){
                System.out.println("Eroare la selectie: " + ex);
                if(tx != null){
                    tx.rollback();
                }
            }
        }
        return rez;
    }

    public boolean verifyDrugs(int idOrder){
        Vector<DrugOrdered> vect = findAll(idOrder);
        RepoDrug r = new RepoDrug(sessionFactory);
        boolean okay = true;
        for (DrugOrdered d:vect) {
            Drug drug = r.findOne(d.getDrugId());
            if(drug.getQuantity() < d.getQuantity()){
                okay = false;
            }
        }
        return okay;
    }

    public boolean processOrder(int idOrder){
        Vector<DrugOrdered> vect = findAll(idOrder);
        RepoDrug r = new RepoDrug(sessionFactory);
        if(verifyDrugs(idOrder)){
            for (DrugOrdered d:vect) {
                Drug drug = r.findOne(d.getDrugId());
                r.update(drug.getId(), drug.getQuantity() - d.getQuantity());
            }
            return true;
        } else {
            return false;
        }
    }

    public void delete(int idOrder){
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            Vector<DrugOrdered> vect = findAll(idOrder);
            try{
                tx = session.beginTransaction();
                for (DrugOrdered d:vect) {
                    DrugOrdered order = session.get(DrugOrdered.class, d.getId());
                    if (order != null) {
                        session.delete(order);
                    }
                }
                tx.commit();
            } catch (RuntimeException ex){
                System.out.println("Eroare la selectie: " + ex);
                if(tx != null){
                    tx.rollback();
                }
            }
        }
    }
}
