module weather.app {

    requires javafx.fxml;
    requires javafx.controls;

    opens application;
    opens application.controller;
}