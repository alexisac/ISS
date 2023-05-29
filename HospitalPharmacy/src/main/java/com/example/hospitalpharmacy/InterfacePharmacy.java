package com.example.hospitalpharmacy;

import com.example.hospitalpharmacy.domain.Drug;
import com.example.hospitalpharmacy.domain.DrugOrdered;
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
import org.hibernate.criterion.Order;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Vector;

public class InterfacePharmacy {
    public Spinner<Integer> quantityText;
    public TextField nameText;
    public TableView<Drug> drugsTabel;
    public TableColumn<Drug, String> nameColumn;
    public TableColumn<Drug, String> quantityColumn;
    public TableView<MyOrder> ordersTable;
    public TableColumn<MyOrder, String> wardColumn;
    public TableColumn<MyOrder, String> dateColumn;
    public TableView<DrugOrdered> orderDrugsTable;
    public TableColumn<DrugOrdered, String> drugNameColumn;
    public TableColumn<DrugOrdered, String> drugQuantityColumn;
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

    public void initialization(){
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 999);
        valueFactory.setValue(1);
        quantityText.setValueFactory(valueFactory);
        initializeTabelDrugs();
        initializeTabelOrders();
        initializeTabelOrdDr(-1);
        initializeTabelOrderDrugs();
    }

    private void initializeTabelDrugs(){
        nameColumn.setCellValueFactory(Drug -> {
            Drug d = Drug.getValue();
            return Bindings.createStringBinding(() -> String.valueOf(d.getName()));
        });

        quantityColumn.setCellValueFactory(Drug -> {
            Drug d = Drug.getValue();
            return Bindings.createStringBinding(() -> String.valueOf(d.getQuantity()));
        });

        Platform.runLater(() -> {
            List<Drug> drugsList = sDrug.findAll();
            drugsTabel.setItems(FXCollections.observableArrayList(drugsList));
        });
    }

    private void initializeTabelOrders(){
        wardColumn.setCellValueFactory(MyOrder-> {
            MyOrder d = MyOrder.getValue();
            return Bindings.createStringBinding(() -> String.valueOf(d.getWardId()));
        });

        dateColumn.setCellValueFactory(MyOrder -> {
            MyOrder d = MyOrder.getValue();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return Bindings.createStringBinding(() -> dateFormat.format(d.getDateTime()));
        });

        Platform.runLater(() -> {
            List<MyOrder> ordersList = sOrd.findAll(MyOrder.orderStatus.BEING_PROCESSED);
            ordersTable.setItems(FXCollections.observableArrayList(ordersList));
        });
    }

    private void reloadOrderTable(){
        List<MyOrder> ordersList = sOrd.findAll(MyOrder.orderStatus.BEING_PROCESSED);
        ordersTable.setItems(FXCollections.observableArrayList(ordersList));
    }

    private void initializeTabelOrdDr(int idOrder){
        drugNameColumn.setCellValueFactory(DrugOrdered -> {
            DrugOrdered d = DrugOrdered.getValue();
            return Bindings.createStringBinding(() -> String.valueOf(sDrug.findOne(d.getDrugId()).getName()));
        });

        drugQuantityColumn.setCellValueFactory(DrugOrdered -> {
            DrugOrdered d = DrugOrdered.getValue();
            return Bindings.createStringBinding(() -> String.valueOf(d.getQuantity()));
        });

        Platform.runLater(() -> {
            List<DrugOrdered> drugsList = sDrOrd.findAll(idOrder);
            orderDrugsTable.setItems(FXCollections.observableArrayList(drugsList));
        });
    }

    private void reloadTabelOrderDrugs(int idOrder){
        List<DrugOrdered> drugsList = sDrOrd.findAll(idOrder);
        for (DrugOrdered d:drugsList) {
            System.out.println("-------" + d.getId() + " | " + d.getDrugId() + " | " + d.getOrderId() + " | " + d.getQuantity());
        }
        orderDrugsTable.setItems(FXCollections.observableArrayList(drugsList));
    }

    private void initializeTabelOrderDrugs(){
        ordersTable.setOnMouseClicked(event -> {
            MyOrder order = ordersTable.getSelectionModel().getSelectedItem();
            if(order != null){
                reloadTabelOrderDrugs(order.getId());
            }
        });
    }

    private void reloadTabelDrugs(){
        List<Drug> drugsList = sDrug.findAll();
        drugsTabel.setItems(FXCollections.observableArrayList(drugsList));
    }

    public void addDrug(){
        String name = nameText.getText();
        int quantity = quantityText.getValue();
        System.out.println(quantity);
        try {
            sDrug.addDrug(name, quantity);
            reloadTabelDrugs();
        } catch (ServiceException ex){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("ERROR!");
            errorAlert.setContentText(ex.getMyMessage());
            errorAlert.showAndWait();
        }
    }

    public void acceptOrder(){
        MyOrder order = ordersTable.getSelectionModel().getSelectedItem();
        if(sDrOrd.processOrder(order.getId())) {
            sOrd.update(order.getId(), MyOrder.orderStatus.ACCEPTED);
            sDrOrd.delete(order.getId());
            reloadTabelDrugs();
            reloadOrderTable();
            reloadTabelOrderDrugs(-1);
        } else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("ERROR!");
            errorAlert.setContentText("Cantitati insuficiente");
            errorAlert.showAndWait();
        }
    }

    public void declineOrder(){
        MyOrder order = ordersTable.getSelectionModel().getSelectedItem();
        sOrd.update(order.getId(), MyOrder.orderStatus.DENIED);
        sDrOrd.delete(order.getId());
        //reloadTabelDrugs();
        reloadOrderTable();
        reloadTabelOrderDrugs(-1);
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
