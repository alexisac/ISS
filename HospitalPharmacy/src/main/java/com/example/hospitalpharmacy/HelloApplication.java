package com.example.hospitalpharmacy;

import com.example.hospitalpharmacy.domain.MyOrder;
import com.example.hospitalpharmacy.domain.Validate;
import com.example.hospitalpharmacy.repository.*;
import com.example.hospitalpharmacy.services.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

//        Properties props=new Properties();
//        try {
//            props.load(new FileReader("db.config"));
//        } catch (IOException e) {
//            System.out.println("Can't find bd.config " + e);
//        }

        initializeHibernate();

//        RepoDoctor rd = new RepoDoctor(props);
        RepoDoctor rd = new RepoDoctor(sessionFactory);
//        RepoWard rw = new RepoWard(props);
        RepoWard rw = new RepoWard(sessionFactory);
//        RepoDrug rDrug = new RepoDrug(props);
        RepoDrug rDrug = new RepoDrug(sessionFactory);
        RepoDrugOrdered rDrOrd = new RepoDrugOrdered(sessionFactory);
        RepoOrder rOrd = new RepoOrder(sessionFactory);

        Validate v = new Validate();

        ServiceDoctor sd = new ServiceDoctor(rd, v);
        ServiceWard sw = new ServiceWard(rw, v);
        ServiceDrug sDrug = new ServiceDrug(rDrug, v);
        ServiceDrugOrdered sDrOrd = new ServiceDrugOrdered(rDrOrd, v);
        ServiceOrder sOrd = new ServiceOrder(rOrd, v);

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("InterfaceLogin.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        InterfaceLogin login = fxmlLoader.getController();
        login.setService(sd, sw, sDrug, sDrOrd, sOrd);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private static SessionFactory sessionFactory;
    private static void initializeHibernate(){
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        try{
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        } catch (Exception ex){
            System.out.println("EXCEPTION: " + ex);
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    private static void close(){
        if(sessionFactory != null){
            sessionFactory.close();
        }
    }
}