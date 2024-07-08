package com.hit.server;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.Collection;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hit.dm.Book;
import com.hit.util.Controller;

public class HandleRequest implements Runnable{
    private Socket clientSocket;
    private Controller<String,Book> controller;

    public HandleRequest(Socket socket, Controller<String,Book> controller){
        this.clientSocket = socket;
        this.controller = controller;
    }

    @Override
    public void run(){
        try {

            Scanner reader = new Scanner(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            String req = reader.nextLine();
            Type reqRef = new TypeToken<Request<Book>>(){}.getType();
            Type respRef = new TypeToken<Response<Book>>(){}.getType();
            Request<Book> request = new Gson().fromJson(req, reqRef);
            String action = request.getAction();
            Response resp = null;

            Book b = null;
            switch (action) {
                case Request.ADD:
                    b = request.getBody();
                    controller.post(b);
                    resp = new Response<Book>(Response.ACK, b);
                    break;
                case Request.GET:
                    b = controller.get(request.getBody());
                    if(b != null){
                        resp = new Response<Book>(Response.ACK, b);
                    }else{
                        resp = new Response<Book>(Response.NACK, null);
                    }
                    break;
                case Request.SHOW:
                    Collection<Book> books = controller.get();
                    resp = new Response<Collection<Book>>(Response.ACK, books);
                    break;
            }

            String jsonResponse = new Gson().toJson(resp, respRef);
            writer.println(jsonResponse);
            writer.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
