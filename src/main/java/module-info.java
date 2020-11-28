module weather.app {

    requires javafx.fxml;
    requires javafx.controls;
    requires java.net.http;
    requires com.google.gson;
    requires org.apache.httpcomponents.httpcore;
    requires org.apache.httpcomponents.httpclient;

    opens application;
    opens application.controller;
    opens icons;
}