module weather.app {

    requires javafx.fxml;
    requires javafx.controls;
    requires java.net.http;

    opens application;
    opens application.controller;
}