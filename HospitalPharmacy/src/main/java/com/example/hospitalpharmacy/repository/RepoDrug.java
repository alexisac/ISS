package com.example.hospitalpharmacy.repository;
import com.example.hospitalpharmacy.domain.Doctor;
import com.example.hospitalpharmacy.domain.Drug;
import com.example.hospitalpharmacy.domain.DrugOrdered;
import com.example.hospitalpharmacy.domain.MyOrder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

public class RepoDrug {
//    private JdbcUtils dbUtils;
//    public RepoDrug(Properties props) {
//        this.dbUtils = new JdbcUtils(props);
//    }
    private final SessionFactory sessionFactory;

    public RepoDrug(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Vector<Drug> findAll(){
//        Vector<Drug> vect = new Vector<Drug>();
//        String sql = "SELECT * FROM drugs";
//        try (Connection connection = dbUtils.getConnection();
//             PreparedStatement ps = connection.prepareStatement(sql);
//             ResultSet resultSet = ps.executeQuery()){
//            while (resultSet.next()) {
//                int id = Integer.parseInt(resultSet.getString("id"));
//                String name = resultSet.getString("name");
//                int quantity = Integer.parseInt(resultSet.getString("quantity"));
//                Drug d = new Drug(name, quantity);
//                d.setId(id);
//                vect.add(d);
//            }
//        }catch (SQLException e){
//            //System.out.println("");
//        }
//        return vect;
        Vector<Drug> rez = new Vector<Drug>();
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                List<Drug> vect = session.createQuery("FROM Drug", Drug.class).list();
                for (Drug d:vect) {
                    System.out.println(d.getId() + " | " + d.getName() + " | " + d.getQuantity());
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

    public Drug findOne(int idDrug){
        Drug d = null;
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                List<Drug> vect = session.createQuery("FROM Drug WHERE id = " + idDrug, Drug.class).list();
                d = vect.get(0);
                tx.commit();
            } catch (RuntimeException ex){
                System.out.println("Eroare la selectie: " + ex);
                if(tx != null){
                    tx.rollback();
                }
            }
        }
        return d;
    }

    public int idDrug(Drug d){
//        int id = -1;
//        String sql = "SELECT * FROM drugs WHERE name = '" + d.getName() + "'";
//        try (Connection connection = dbUtils.getConnection();
//             PreparedStatement ps = connection.prepareStatement(sql);
//             ResultSet resultSet = ps.executeQuery()){
//            while(resultSet.next()) {
//                id = Integer.parseInt(resultSet.getString("id"));
//            }
//        }catch (SQLException e){
//            //System.out.println(e.getMessage());
//        }
//        return id;
        int id = -1;
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                List<Drug> vect = session.createQuery("FROM Drug WHERE name = '" + d.getName() + "'", Drug.class).list();
                if(vect.size() > 0){
                    id = vect.get(0).getId();
                }
                tx.commit();
            } catch (RuntimeException ex){
                System.out.println("Eroare la seelctie: " + ex);
                if(tx != null){
                    tx.rollback();
                }
            }
        }
        return id;
    }

    private int oldQuantity(Drug d){
//        int quantity = 0;
//        String sql = "SELECT * FROM drugs WHERE name = '" + d.getName() + "'";
//        try (Connection connection = dbUtils.getConnection();
//             PreparedStatement ps = connection.prepareStatement(sql);
//             ResultSet resultSet = ps.executeQuery()){
//            while(resultSet.next()) {
//                quantity = Integer.parseInt(resultSet.getString("quantity"));
//            }
//        }catch (SQLException e){
//            //System.out.println(e.getMessage());
//        }
//        return quantity;
        int quantity = 0;
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                List<Drug> vect = session.createQuery("FROM Drug WHERE name = '" + d.getName() + "'", Drug.class).list();
                quantity = vect.get(0).getQuantity();
            } catch (RuntimeException ex){
                System.out.println("Eroare la selectie: " + ex);
                if(tx != null){
                    tx.rollback();
                }
            }
        }
        return quantity;
    }

    public void add(Drug d){
        int id = idDrug(d);
        if(id == -1) {
//            String sql = "INSERT INTO drugs (name, quantity) VALUES (?, ?)";
//            try (Connection connection = dbUtils.getConnection();
//                 PreparedStatement ps = connection.prepareStatement(sql)) {
//                ps.setString(1, String.valueOf(d.getName()));
//                ps.setInt(2, d.getQuantity());
//                ps.executeUpdate();
//            } catch (SQLException ex) {
//                //System.out.println("");
//            }
            try(Session session = sessionFactory.openSession()){
                Transaction tx = null;
                try{
                    tx = session.beginTransaction();
                    session.save(d);
                    tx.commit();
                } catch (RuntimeException ex){
                    System.out.println("Eroare la inserare: " + ex);
                    if(tx != null){
                        tx.rollback();
                    }
                }
            }
        }
        else {
//            int newQuantity = oldQuantity(d) + d.getQuantity();
//            String sql = "UPDATE drugs SET quantity = " + newQuantity + " WHERE id = " + id  + " AND name = '" + d.getName() + "'";
//            try (Connection connection = dbUtils.getConnection();
//                 PreparedStatement ps = connection.prepareStatement(sql)) {
//                ps.executeUpdate();
//            } catch (SQLException ex) {
//                //System.out.println("");
//            }
            try(Session session = sessionFactory.openSession()) {
                Transaction tx = null;
                try {
                    int newQuantity = oldQuantity(d) + d.getQuantity();
                    d.setQuantity(newQuantity);
                    tx = session.beginTransaction();
                    Drug drug = session.get(Drug.class, id);
                    drug.setQuantity(newQuantity);
                    session.update(drug);
                    tx.commit();
                } catch (RuntimeException ex){
                    System.out.println("Eroare la inserare: " + ex);
                    if(tx != null){
                        tx.rollback();
                    }
                }
            }
        }
    }

    public void update(int id, int quantity){
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                Drug drug = session.get(Drug.class, id);
                drug.setQuantity(quantity);
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
