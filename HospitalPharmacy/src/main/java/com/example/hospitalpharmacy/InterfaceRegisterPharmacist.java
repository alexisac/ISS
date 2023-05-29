package com.example.hospitalpharmacy;
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

public class InterfaceRegisterPharmacist {
    @FXML
    private TextField nameText;
    @FXML
    private TextField codeText;
    @FXML
    private TextField confirmedCodeText;
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

    public void signUp(ActionEvent event) throws IOException{
        String name = nameText.getText();
        String code = codeText.getText();
        String confirmedCode = confirmedCodeText.getText();
        int wardId = sw.idWard("farmacie");
        try {
            sd.addDoctor(name, Integer.parseInt(code), Integer.parseInt(confirmedCode), wardId);
            logout(event);
        } catch (ServiceException ex){
            Alert errorAlert =new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("ERROR!");
            errorAlert.setContentText(ex.getMyMessage());
            errorAlert.showAndWait();
        }
    }

    public void logout(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InterfaceLogin.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        InterfaceLogin login = fxmlLoader.getController();
        login.setService(sd, sw, sDrug, sDrOrd, sOrd);
        stage.setScene(scene);
        stage.show();
    }
}
