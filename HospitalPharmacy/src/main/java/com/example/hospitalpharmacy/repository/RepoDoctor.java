package com.example.hospitalpharmacy.repository;
import com.example.hospitalpharmacy.domain.Doctor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

public class RepoDoctor {

//    private JdbcUtils dbUtils;
//
//    public RepoDoctor(Properties props) {
//        this.dbUtils = new JdbcUtils(props);
//    }
    private final SessionFactory sessionFactory;

    public RepoDoctor(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public int idDoctor(Doctor d){
//        int id = -1;
//        String sql = "SELECT * FROM doctors WHERE name = '" + d.getName() + "' AND code = " + d.getCode() + " AND wardId = " + d.getWardId();
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
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                List<Doctor> vect = session.createQuery("FROM Doctor WHERE name = '" + d.getName() + "' AND code = " + d.getCode() + " AND wardId = " + d.getWardId(), Doctor.class).list();
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

//    public Doctor findOne(int idDoctor){
////        Doctor d = null;
////        String sql = "SELECT * FROM doctors WHERE id = " + idDoctor;
////        try(Connection connection = dbUtils.getConnection();
////        PreparedStatement ps = connection.prepareStatement(sql);
////        ResultSet resultSet = ps.executeQuery()){
////            while (resultSet.next()) {
////                int id = Integer.parseInt(resultSet.getString("id"));
////                String name = resultSet.getString("name");
////                int code = Integer.parseInt(resultSet.getString("code"));
////                int wardId = Integer.parseInt(resultSet.getString("wardId"));
////                d = new Doctor(name, code, wardId);
////                d.setId(id);
////            }
////        } catch (SQLException ex){
////            //System.out.println("");
////        }
////        return d;
//        Doctor d = null;
//        try(Session session = sessionFactory.openSession()){
//            Transaction tx = null;
//            try{
//                tx = session.beginTransaction();
//                List<Doctor> vect = session.createQuery("FROM Doctor WHERE id = " + idDoctor, Doctor.class).list();
//                d = vect.get(0);
//                tx.commit();
//            } catch (RuntimeException ex){
//                System.out.println("Eroare la selectie: " + ex);
//                if(tx != null){
//                    tx.rollback();
//                }
//            }
//        }
//        return d;
//    }

    public Vector<Doctor> findAll(){
//        Vector<Doctor> vect = new Vector<Doctor>();
//        String sql = "SELECT * FROM doctors";
//        try (Connection connection = dbUtils.getConnection();
//             PreparedStatement ps = connection.prepareStatement(sql);
//             ResultSet resultSet = ps.executeQuery()){
//            while (resultSet.next()) {
//                int id = Integer.parseInt(resultSet.getString("id"));
//                String name = resultSet.getString("name");
//                int code = Integer.parseInt(resultSet.getString("code"));
//                int wardId = Integer.parseInt(resultSet.getString("wardId"));
//                Doctor d = new Doctor(name, code, wardId);
//                d.setId(id);
//                vect.add(d);
//            }
//        }catch (SQLException e){
//            //System.out.println("");
//        }
//        return vect;
        Vector<Doctor> rez = new Vector<Doctor>();
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                List<Doctor> vect = session.createQuery("FROM Doctor", Doctor.class).list();
//                for(Doctor d:vect){
//                    System.out.println(d.getId() + " | " + d.getName() + " | " + d.getWardId() + " | " + d.getCode());
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

    public void add(Doctor d){
//        String sql = "INSERT INTO doctors (name, code, wardId) VALUES (?, ?, ?)";
//        try(Connection connection = dbUtils.getConnection();
//        PreparedStatement ps = connection.prepareStatement(sql)){
//            ps.setString(1, String.valueOf(d.getName()));
//            ps.setInt(2, d.getCode());
//            ps.setInt(3, d.getWardId());
//            ps.executeUpdate();
//        } catch (SQLException ex){
//            //System.out.println("");
//        }

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
}
