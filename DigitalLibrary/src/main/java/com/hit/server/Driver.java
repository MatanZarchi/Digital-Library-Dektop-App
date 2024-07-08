package com.hit.server;

import com.hit.dm.Book;

public class Driver {
    public static void main(String[] args) {
        Server<String, Book> server = new Server<String, Book> (34567);
        new Thread(server).start();
        System.out.println("Server started");
    }
}
