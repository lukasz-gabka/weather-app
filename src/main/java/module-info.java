module application {

    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.swing;
    requires java.net.http;
    requires com.google.gson;
    requires org.apache.httpcomponents.httpcore;
    requires org.apache.httpcomponents.httpclient;

    opens application;
    opens application.controller;
    opens images.icons;

    exports application;
    opens application.model.dto to com.google.gson;
}