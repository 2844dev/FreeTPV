module com.mateo.freetpv {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires atlantafx.base;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome5;
    requires org.slf4j;
    requires ch.qos.logback.classic;
    requires bcrypt;
    requires org.jsoup;
    requires java.net.http;
    requires org.json;

    opens com.mateo.freetpv.model to javafx.base;
    opens  com.mateo.freetpv.controller to javafx.fxml;
    opens com.mateo.freetpv to javafx.fxml;
    exports com.mateo.freetpv;
}