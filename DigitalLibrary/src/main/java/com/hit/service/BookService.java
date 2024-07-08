package com.hit.service;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Scanner;

import com.hit.algorithm.IAlgoCache;
import com.hit.dao.DaoFileImpl;
import com.hit.dao.IDao;
import com.hit.dm.Book;

public class BookService {
    private static final String RESOURCE_FILE = "DataSource.txt";
    private IAlgoCache<Book> cache;
    private IDao<String, Book> dao;

    public BookService(IAlgoCache<Book> cache){
        this.cache = cache;
        String fileName = readConfiguration();
        this.dao = new DaoFileImpl(fileName);
        try {
            Collection<Book> books =  dao.load();
            for(Book book : books){
                cache.putElementInCache(book);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error starting book service " + e.getMessage());
            System.exit(1);
        }
    }

    private String readConfiguration()  {
        try{
            Scanner scanner = new Scanner(new File("./src/main/resources/"+RESOURCE_FILE));
            if(scanner.hasNext()){
                String configLine = scanner.nextLine();
                String[] field = configLine.split("=");
                return field[1]; //fileName, books.bin
            }
        }catch(IOException e){
            System.out.println("could not read file "+ RESOURCE_FILE);
            System.exit(1);
        }catch(ArrayIndexOutOfBoundsException e){
            System.out.println("filename is malformed in " + RESOURCE_FILE);
            System.exit(1);
        }

        return null;
    }

    public Book getByBook(Book book) {
        Book b = cache.getElementFromCache(book);
        if (b == null) {
            System.out.println("Not exist in cache");
            if(b == null){
                // load from file system
                b = dao.find(book.getIsbn());
            }
        }
        return b;
    }

    public boolean addByBook(Book book) {
        cache.putElementInCache(book);
        if(dao.add(book)){
            System.out.println("Added successfully");
            return true;
        }
        System.out.println("error adding");
        return false;
    }

    public Collection<Book> ShowBooks() {
        return cache.display();
    }
}
