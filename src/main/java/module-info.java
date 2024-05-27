module co.edu.uniquindio.agencia20241 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires org.mapstruct;
    requires java.logging;
    requires java.desktop;
    requires com.rabbitmq.client;

    opens co.edu.uniquindio.agencia20241 to javafx.fxml;
    exports co.edu.uniquindio.agencia20241;
    exports co.edu.uniquindio.agencia20241.viewController;
    opens co.edu.uniquindio.agencia20241.viewController to javafx.fxml;
    exports co.edu.uniquindio.agencia20241.controller;
    exports co.edu.uniquindio.agencia20241.mapping.dto;
    exports co.edu.uniquindio.agencia20241.mapping.mappers;
    exports co.edu.uniquindio.agencia20241.model;
    opens co.edu.uniquindio.agencia20241.controller to javafx.fxml;


}