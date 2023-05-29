package com.example.hospitalpharmacy.repository;

import com.example.hospitalpharmacy.domain.MyOrder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Vector;

public class RepoOrder {

    private final SessionFactory sessionFactory;

    public RepoOrder(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public int idOrder(MyOrder o){
        int id = -1;
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                int statusValue = o.getStatus().ordinal();
                List<MyOrder> vect = session.createQuery("FROM MyOrder WHERE status = '" + statusValue + "' AND wardId = " + o.getWardId() + " AND dateTime = " + o.getDateTime().getTime(), MyOrder.class).list();
                if(vect.size() > 0){
                    id = vect.get(0).getId();
                }
                tx.commit();
            } catch (RuntimeException ex){
                System.out.println("Eroare la selectie: " + ex);
                if(tx != null){
                    tx.rollback();
                }
            }
        }
        return id;
    }

    public void add(MyOrder o){
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                session.save(o);
                tx.commit();
            } catch (RuntimeException ex){
                System.out.println("Eroare la inserare: " + ex);
                if(tx != null){
                    tx.rollback();
                }
            }
        }
    }

    public Vector<MyOrder> findAll(MyOrder.orderStatus status){
        Vector<MyOrder> rez = new Vector<MyOrder>();
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                int statusValue = status.ordinal();
                List<MyOrder> vect = session.createQuery("FROM MyOrder WHERE status = " + statusValue, MyOrder.class).list();

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                for(MyOrder d:vect){
                    System.out.println(d.getId() + " | " + d.getStatus() + " | " + d.getWardId() + " | " + dateFormat.format(d.getDateTime()));
                }

                rez.addAll(vect);
                tx.commit();
            } catch (RuntimeException ex){
                System.out.println("Eroare la select: " + ex);
                if(tx != null){
                    tx.rollback();
                }
            }
        }
        return rez;
    }

    public void update(int id, MyOrder.orderStatus status){
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                MyOrder order = session.get(MyOrder.class, id);
                order.setStatus(status);

                tx.commit();
            } catch (RuntimeException ex){
                System.out.println("Eroare la selectie: " + ex);
                if(tx != null){
                    tx.rollback();
                }
            }
        }
    }

    public void delete(int id){
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                MyOrder myOrder = session.get(MyOrder.class, id);
                if (myOrder != null) {
                    session.delete(myOrder);
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
