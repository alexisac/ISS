package com.example.hospitalpharmacy.repository;
import com.example.hospitalpharmacy.domain.Ward;
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

public class RepoWard {
//    private JdbcUtils dbUtils;
//    public RepoWard(Properties props) {
//        this.dbUtils = new JdbcUtils(props);
//    }
    private final SessionFactory sessionFactory;

    public RepoWard(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public int idWard(Ward w){
//        int id = -1;
//        String sql = "SELECT * FROM wards WHERE name = '" + w.getName() + "'";
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
                List<Ward> vect = session.createQuery("FROM Ward WHERE name = '" + w.getName() + "'", Ward.class).list();
                if(vect.size() > 0){
                    id = vect.get(0).getId();
                }
            } catch (RuntimeException ex){
                System.out.println("Eroare la sectie: " + ex);
                if(tx != null){
                    tx.rollback();
                }
            }
        }
        return id;
    }
    public Ward findOne(int idWard){
//        Ward w = null;
//        String sql = "SELECT * FROM wards WHERE id = " + idWard;
//        try(Connection connection = dbUtils.getConnection();
//            PreparedStatement ps = connection.prepareStatement(sql);
//            ResultSet resultSet = ps.executeQuery()){
//            while (resultSet.next()) {
//                int id = Integer.parseInt(resultSet.getString("id"));
//                String name = resultSet.getString("name");
//                w = new Ward(name);
//                w.setId(id);
//            }
//        } catch (SQLException ex){
//            //System.out.println("");
//        }
//        return w;
        Ward w = null;
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                List<Ward> vect = session.createQuery("FROM Ward WHERE id = " + idWard, Ward.class).list();
                w = vect.get(0);
                tx.commit();
            } catch (RuntimeException ex){
                System.out.println("Eroare la selectie: " + ex);
                if(tx != null){
                    tx.rollback();
                }
            }
        }
        return w;
    }
    public Vector<Ward> findAll(){
//        Vector<Ward> vect = new Vector<Ward>();
//        String sql = "SELECT * FROM wards";
//        try (Connection connection = dbUtils.getConnection();
//             PreparedStatement ps = connection.prepareStatement(sql);
//             ResultSet resultSet = ps.executeQuery()){
//            while (resultSet.next()) {
//                int id = Integer.parseInt(resultSet.getString("id"));
//                String name = resultSet.getString("name");
//                Ward w = new Ward(name);
//                w.setId(id);
//                vect.add(w);
//            }
//        }catch (SQLException e){
//            //System.out.println("");
//        }
//        return vect;
        Vector<Ward> rez = new Vector<Ward>();
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                List<Ward> vect = session.createQuery("FROM Ward", Ward.class).list();
//                for (Ward w:vect) {
//                    System.out.println(w.getId() + " | " + w.getName());
//                }
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
}
