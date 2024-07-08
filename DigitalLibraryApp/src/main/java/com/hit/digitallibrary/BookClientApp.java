package com.hit.digitallibrary;

import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.Collection;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import  javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class BookClientApp extends Application {

    public static void main(String[] args) {launch(args);
    }

    @Override
    public void start(Stage primaryStage){

            primaryStage.setTitle("DIGITAL LIBRARY");

        StackPane root = new StackPane();
        root.setId("stack-pane");

        GridPane grid = new GridPane();
        root.getChildren().add(grid);
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(root, 550, 400);
        primaryStage.setScene(scene);

        scene.getStylesheets().add(String.valueOf(this.getClass().getResource("library.css")));

        Text scenetitle = new Text("Welcome to Digital Library!");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        // new book
        Text addTitle = new Text("Add a new Book");
        addTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
        grid.add(addTitle, 0, 1, 2, 1);

        // row 3:
        grid.add(new Label("ISBN"), 0, 3);

        TextField addIsbnTextField = new TextField();
        grid.add(addIsbnTextField, 1, 3);

        // row 4
        grid.add( new Label("Author"), 0, 4);

        TextField addAuthorTextField = new TextField();
        grid.add(addAuthorTextField, 1, 4);

        // row 5
        grid.add( new Label("Title"), 0, 5);

        TextField addTitleTextField = new TextField();
        grid.add(addTitleTextField, 1, 5);

        // row 6
        Button btnAddBook = new Button("Add Book");
        HBox hbAddBtn = new HBox(10);
        hbAddBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbAddBtn.getChildren().add(btnAddBook);
        grid.add(hbAddBtn, 1, 6);

        // row 7
        Text searchTitle = new Text("Search by ISBN");
        searchTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
        grid.add(searchTitle, 0, 7, 2, 1);

        // row 8
        grid.add(new Label("ISBN:"), 0, 8);

        TextField txtIsbn = new TextField();
        grid.add(txtIsbn, 1, 8);

        //row 9
        Button btnGetBook = new Button("Get book by ISBN");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btnGetBook);
        grid.add(hbBtn, 1, 9);

        //row 10
        Button btnGetAllBooks = new Button("Get all books");
        HBox hbAllBooksBtn = new HBox(10);
        hbAllBooksBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbAllBooksBtn.getChildren().add(btnGetAllBooks);
        grid.add(hbAllBooksBtn, 1, 10);

        // row 11
        final Text actiontarget = new Text("result");
        actiontarget.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
        grid.add(actiontarget, 0, 11, 2, 1);

        btnGetBook.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                String host = "127.0.0.01";
                int port = 34567;

                try{
                    String isbn = txtIsbn.getText();
                    if(isbn.length() == 0) {
                        Alert a = new Alert(AlertType.ERROR, "Missing searched ISBN");
                        a.show();
                        return;
                    }

                    Socket clientSocket = new Socket(host, port);
                    Scanner reader = new Scanner(new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter writer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                    // request
                    Request<Book> reqRef = new Request<Book>(Request.GET, new Book(isbn, "", ""));
                    Type respRef = new TypeToken<Response<Book>>(){}.getType();
                    Type ref = new TypeToken<Request<Book>>(){}.getType();
                    String jsonRequest = new Gson().toJson(reqRef, ref);
                    writer.println(jsonRequest);
                    writer.flush();

                    // wait for response form server
                    String resp = reader.nextLine();
                    if(reqRef.getAction().equals(Request.GET)) {
                        Response<Book> response = new Gson().fromJson(resp, respRef);
                        if(response.getStatus().equals(Response.ACK)) {
                            actiontarget.setText(response.getBody().getTitle() +"\n" + response.getBody().getAuthor());
                        }else {
                            Alert a = new Alert(AlertType.ERROR, "Book with ISBN "+txtIsbn.getText() + " was not found");
                            a.show();
                        }

                    }
                    reader.close();
                    writer.close();
                    clientSocket.close();
                }catch(IOException ex){

                }
            }
        });

        btnGetAllBooks.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                String host = "127.0.0.01";
                int port = 34567;

                try{
                    Socket clientSocket = new Socket(host, port);
                    Scanner reader = new Scanner(new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter writer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                    // request
                    Request<Book> reqRef = new Request<Book>(Request.SHOW, new Book("", "", ""));
                    Type respRef = new TypeToken<Response<Collection<Book>>>(){}.getType();
                    Type ref = new TypeToken<Request<Book>>(){}.getType();
                    String jsonRequest = new Gson().toJson(reqRef, ref);
                    writer.println(jsonRequest);
                    writer.flush();

                    // wait for response form server
                    String resp = reader.nextLine();

                    Response<Collection<Book>> response = new Gson().fromJson(resp, respRef);
                    if(response.getStatus().equals(Response.ACK)) {
                        StringBuffer sb = new StringBuffer();
                        for(Book book : response.getBody()) {
                            sb.append(book.getIsbn());
                            sb.append(": ");
                            sb.append(book.getTitle());
                            sb.append(" (");
                            sb.append(book.getAuthor());
                            sb.append(")\n");
                        }
                        actiontarget.setText(sb.toString());
                    }else {
                        Alert a = new Alert(AlertType.ERROR, "Book with ISBN "+txtIsbn.getText() + " was not found");
                        a.show();
                    }

                    reader.close();
                    writer.close();
                    clientSocket.close();
                }catch(IOException ex){

                }
            }
        });

        // Add book
        btnAddBook.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                String host = "127.0.0.01";
                int port = 34567;

                try{

                    String isbn = addIsbnTextField.getText();
                    String author = addAuthorTextField.getText();
                    String title = addTitleTextField.getText();
                    if(isbn.length() == 0) {
                        Alert a = new Alert(AlertType.ERROR, "Missing ISBN");
                        a.show();
                        return;
                    }
                    if(author.length() == 0) {
                        Alert a = new Alert(AlertType.ERROR, "Missing Author");
                        a.show();
                        return;
                    }

                    if(title.length() == 0) {
                        Alert a = new Alert(AlertType.ERROR, "Missing Author");
                        a.show();
                        return;
                    }

                    Socket clientSocket = new Socket(host, port);
                    Scanner reader = new Scanner(new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter writer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                    // request
                    Request<Book> reqRef = new Request<Book>(Request.ADD, new Book(isbn, author, title));
                    Type respRef = new TypeToken<Response<Book>>(){}.getType();
                    Type ref = new TypeToken<Request<Book>>(){}.getType();
                    String jsonRequest = new Gson().toJson(reqRef, ref);
                    writer.println(jsonRequest);
                    writer.flush();

                    // wait for response form server
                    String resp = reader.nextLine();

                    Response<Book> response = new Gson().fromJson(resp, respRef);
                    if(response.getStatus().equals(Response.ACK)) {
                        Alert a = new Alert(AlertType.INFORMATION, "Book "+title + " was added");
                        a.show();
                    }else{
                        Alert a = new Alert(AlertType.ERROR, "Book "+title + " was not added");
                        a.show();
                    }

                    reader.close();
                    writer.close();
                    clientSocket.close();

                }catch(IOException ex){

                }
            }
        });

        primaryStage.show();
    }


}