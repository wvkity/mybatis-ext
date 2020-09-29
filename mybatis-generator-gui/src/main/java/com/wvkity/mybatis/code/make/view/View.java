package com.wvkity.mybatis.code.make.view;

public enum View {
    
    CONNECTION("view/Connection.fxml");
    private final String path;
    View(String path) {
        this.path = path;
    }
    
    public String getLocation() {
        return this.path;
    }
}
