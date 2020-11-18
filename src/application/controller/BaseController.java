package application.controller;

import application.view.ViewManager;

public abstract class BaseController {

    protected ViewManager viewManager;
    private String fxmlName;

    public BaseController(ViewManager viewManager, String fxmlName) {
        this.viewManager = viewManager;
        this.fxmlName = fxmlName;
    }

    public String getFxmlName() {
        return fxmlName;
    }
}
