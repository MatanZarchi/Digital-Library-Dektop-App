package com.hit.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.hit.dm.Book;
import com.hit.util.Controller;
import com.hit.util.ControllerFactory;

public class Server<K,V> implements Runnable{


    private ServerSocket serverSocket;
    private ControllerFactory controllerFactory;

    public Server(int port){

        controllerFactory = ControllerFactory.getInstance();
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("Error starting server on port " + port);
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    @Override
    public void run(){ //as Main.
        Controller<String, Book> controller = controllerFactory.getController("Book");
        try {
            while(true){
                Socket clientSocket = serverSocket.accept();
                HandleRequest handler = new HandleRequest(clientSocket,controller);
                new Thread(handler).start();

            }
        }catch(IOException e){
            System.out.println("Error listening to connections");
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
}
