package com.hit.util;

import com.hit.controller.BookController;

public class ControllerFactory {
    private static ControllerFactory instance = new ControllerFactory();

    public static ControllerFactory getInstance() {
        return instance;
    }

    public <K,V> Controller<K,V> getController(String type) {
        if(type.equals("Book")) {
            return (Controller<K, V>) new BookController();
        }
        return null;
    }
}
