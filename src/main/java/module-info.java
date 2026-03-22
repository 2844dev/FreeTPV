module com.mateo.freetpv {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires atlantafx.base;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome5;

    opens com.mateo.freetpv.model to javafx.base;
    opens  com.mateo.freetpv.controller to javafx.fxml;
    opens com.mateo.freetpv to javafx.fxml;
    exports com.mateo.freetpv;
}