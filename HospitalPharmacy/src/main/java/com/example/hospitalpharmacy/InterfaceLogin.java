package com.example.hospitalpharmacy;

import com.example.hospitalpharmacy.domain.Ward;
import com.example.hospitalpharmacy.services.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class InterfaceLogin {
    @FXML
    private TextField nameText;
    @FXML
    private TextField codeText;
    @FXML
    private TextField wardCodeText;

    ServiceDoctor sd;
    ServiceWard sw;
    ServiceDrug sDrug;
    ServiceDrugOrdered sDrOrd;
    ServiceOrder sOrd;

    public void setService(ServiceDoctor sd, ServiceWard sw, ServiceDrug sDrug, ServiceDrugOrdered sDrOrd, ServiceOrder sOrd){
        this.sd = sd;
        this.sw = sw;
        this.sDrug = sDrug;
        this.sDrOrd = sDrOrd;
        this.sOrd = sOrd;
    }

    public void Login(ActionEvent event) throws IOException{
        String name = nameText.getText();
        String code = codeText.getText();
        String wardCode = wardCodeText.getText();
        if(name.length() != 0 && code.length() != 0 && wardCode.length() != 0){
            int idDoctor = sd.idDoctor(name, Integer.parseInt(code), Integer.parseInt(wardCode));
            if(idDoctor != -1){
                Ward ward = sw.findOne(Integer.parseInt(wardCode));
                if(ward.getName().equals("farmacie")){
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InterfacePharmacy.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 1114, 527);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    InterfacePharmacy ph = fxmlLoader.getController();
                    ph.setService(sd, sw, sDrug, sDrOrd, sOrd);
                    ph.initialization();
                    stage.setScene(scene);
                    stage.show();
                } else {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InterfaceHospitalWard.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 860, 504);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    InterfaceHospitalWard hw = fxmlLoader.getController();
                    hw.setService(Integer.parseInt(wardCode), sd, sw, sDrug, sDrOrd, sOrd);
                    hw.initialization();
                    stage.setScene(scene);
                    stage.show();
                }
            } else{
                Alert errorAlert =new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("ERROR!");
                errorAlert.setContentText("This account doesn't exist!");
                errorAlert.showAndWait();
            }
        } else {
            Alert errorAlert =new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("ERROR!!!");
            errorAlert.setContentText("All fields must be completed to proceed!");
            errorAlert.showAndWait();
        }
    }

    public void registerDoctor(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InterfaceRegisterDoctor.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        InterfaceRegisterDoctor doct = fxmlLoader.getController();
        doct.setService(sd, sw, sDrug, sDrOrd, sOrd);
        doct.initialization();
        stage.setScene(scene);
        stage.show();
    }

    public void registerPharmacist(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InterfaceRegisterPharmacist.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        InterfaceRegisterPharmacist ph = fxmlLoader.getController();
        ph.setService(sd, sw, sDrug, sDrOrd, sOrd);
        stage.setScene(scene);
        stage.show();
    }
}
