module co.edu.uniquindio.agencia20241 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.mapstruct;


    opens co.edu.uniquindio.agencia20241 to javafx.fxml;
    exports co.edu.uniquindio.agencia20241;
    exports co.edu.uniquindio.agencia20241.model;
    exports co.edu.uniquindio.agencia20241.controller;
    exports co.edu.uniquindio.agencia20241.viewController;

     exports co.edu.uniquindio.agencia20241.mapping.dto;
    exports co.edu.uniquindio.agencia20241.mapping.mappers;




}