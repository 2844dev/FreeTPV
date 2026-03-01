module com.mateo.freetpv {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens  com.mateo.freetpv.controller to javafx.fxml;
    opens com.mateo.freetpv to javafx.fxml;
    exports com.mateo.freetpv;
}