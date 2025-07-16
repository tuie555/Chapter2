module com.example.adprolab2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.adprolab2 to javafx.fxml;
    exports com.example.adprolab2;
}