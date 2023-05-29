package com.example.hospitalpharmacy;

import com.example.hospitalpharmacy.domain.Drug;
import com.example.hospitalpharmacy.domain.MyOrder;
import com.example.hospitalpharmacy.services.*;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;

public class InterfaceHospitalWard {
    public Spinner<Integer> quantityText;
    public TableView<Drug> drugsTable;
    public TableColumn<Drug, String> nameColumn;
    public TableColumn<Drug, String> quantityColumn;
    public TableView<Drug> tempDrugsTable;
    public TableColumn<Drug, String> tempNameColumn;
    public TableColumn<Drug, String> tempQuantityColumn;

    int idWard;
    ServiceDoctor sd;
    ServiceWard sw;
    ServiceDrug sDrug;
    ServiceDrugOrdered sDrOrd;
    ServiceOrder sOrd;

    public void setService(int idWard, ServiceDoctor sd, ServiceWard sw, ServiceDrug sDrug, ServiceDrugOrdered sDrOrd, ServiceOrder sOrd) {
        this.sd = sd;
        this.sw = sw;
        this.sDrug = sDrug;
        this.sDrOrd = sDrOrd;
        this.idWard = idWard;
        this.sOrd = sOrd;
    }

    public void initialization(){
        ordersAcceptedDenied();
        initializeTabelDrugs();
        initializeSpinner();
        initializeTempTabelDrugs();
    }

    private void initializeTabelDrugs(){
        nameColumn.setCellValueFactory(Drug -> {
            com.example.hospitalpharmacy.domain.Drug d = Drug.getValue();
            return Bindings.createStringBinding(() -> String.valueOf(d.getName()));
        });

        quantityColumn.setCellValueFactory(Drug -> {
            Drug d = Drug.getValue();
            return Bindings.createStringBinding(() -> String.valueOf(d.getQuantity()));
        });

        Platform.runLater(() -> {
            List<Drug> drugsList = sDrug.findAll();
            drugsTable.setItems(FXCollections.observableArrayList(drugsList));
        });
    }

    private void initializeSpinner(){
        drugsTable.setOnMouseClicked(event -> {
            Drug selected = drugsTable.getSelectionModel().getSelectedItem();
            if(selected != null){
                int quant = selected.getQuantity();
                SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, quant);
                valueFactory.setValue(1);
                quantityText.setValueFactory(valueFactory);
            }
        });
    }

    private void initializeTempTabelDrugs(){
        tempNameColumn.setCellValueFactory(Drug -> {
            Drug d = Drug.getValue();
            return Bindings.createStringBinding(() -> String.valueOf(d.getName()));
        });

        tempQuantityColumn.setCellValueFactory(Drug -> {
            Drug d = Drug.getValue();
            return Bindings.createStringBinding(() -> String.valueOf(d.getQuantity()));
        });

        Platform.runLater(() -> {
            List<Drug> drugsList = sDrOrd.getAllTempList();
            tempDrugsTable.setItems(FXCollections.observableArrayList(drugsList));
        });
    }

    private void reloadTempTableDrugs(){
        List<Drug> drugsList = sDrOrd.getAllTempList();
        tempDrugsTable.setItems(FXCollections.observableArrayList(drugsList));

        System.out.println("-------------------");
        for (Drug d:drugsList) {
            System.out.println(d.getId() + " | " + d.getName() + " | " + d.getQuantity());
        }

    }

    private void ordersAcceptedDenied(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Vector<MyOrder> vectAcc = sOrd.findAll(MyOrder.orderStatus.ACCEPTED);
        String acc  = "";
        Vector<MyOrder> vectDec = sOrd.findAll(MyOrder.orderStatus.DENIED);
        String dec = "";
        for (MyOrder o:vectAcc) {
            if(o.getWardId() == idWard){
                acc += "\n" + o.getId() + " | " + dateFormat.format(o.getDateTime());
                sOrd.delete(o.getId());
            }
        }
        for (MyOrder o:vectDec) {
            if(o.getWardId() == idWard){
                dec += "\n" + o.getId() + " | " + dateFormat.format(o.getDateTime());
                sOrd.delete(o.getId());
            }
        }
        if(acc.length() > 1) {
            Alert confAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confAlert.setHeaderText("COMENZI ONORATE!");
            confAlert.setContentText(acc);
            confAlert.showAndWait();
        }
        if(dec.length() > 1) {
            Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
            infoAlert.setHeaderText("COMENZI REFUZATE!");
            infoAlert.setContentText(dec);
            infoAlert.showAndWait();
        }
    }

    public void addDrug(){
        if(drugsTable.getSelectionModel().getSelectedItem() != null){
            Drug d = drugsTable.getSelectionModel().getSelectedItem();
            drugsTable.getSelectionModel().clearSelection();
            int quantity = quantityText.getValue();
            Drug newDr = new Drug(d.getName(), quantity);
            int id = sDrug.idDrug(d);
            newDr.setId(id);
            sDrOrd.addInTempList(newDr);
            reloadTempTableDrugs();
        } else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("ERROR!");
            errorAlert.setContentText("Nu ai selectat niciun medicament");
            errorAlert.showAndWait();
        }
    }

    public void deleteDrug(){
        if(tempDrugsTable.getSelectionModel().getSelectedItem() != null){
            Drug d = tempDrugsTable.getSelectionModel().getSelectedItem();
            tempDrugsTable.getSelectionModel().clearSelection();
            int id = sDrug.idDrug(d);
            d.setId(id);
            sDrOrd.delFromTempList(d);
            reloadTempTableDrugs();
        } else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("ERROR!");
            errorAlert.setContentText("Nu ai selectat niciun medicament din lista temporara");
            errorAlert.showAndWait();
        }
    }

    public void sendOrder(){
        MyOrder o = new MyOrder(idWard, MyOrder.orderStatus.BEING_PROCESSED, Calendar.getInstance().getTime());
        sOrd.addOrder(o);
        int idOrder = sOrd.idOrder(o);
        System.out.println(idOrder);
        sDrOrd.addDrugOrder(idOrder);
        reloadTempTableDrugs();
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
