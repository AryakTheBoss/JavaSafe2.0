module com.javamaster.javasafe.javasafe2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.desktop;
    requires jasypt;

    opens com.javamaster.javasafe.javasafe2 to javafx.fxml;
    exports com.javamaster.javasafe.javasafe2;
    exports com.javamaster.javasafe.javasafe2.controller.login;
    opens com.javamaster.javasafe.javasafe2.controller.login to javafx.fxml;
    opens com.javamaster.javasafe.javasafe2.controller.mainWindow to javafx.fxml;
}