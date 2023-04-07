module com.example.hospitalpharmacy {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.hospitalpharmacy to javafx.fxml;
    exports com.example.hospitalpharmacy;
}