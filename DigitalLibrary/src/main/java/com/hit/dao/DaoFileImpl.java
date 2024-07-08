package com.hit.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.hit.dm.Book;

public class DaoFileImpl implements IDao<String, Book>
{
    private String fileName;

    public DaoFileImpl(String fileName)
    {
        this.fileName = fileName;
    }

    @Override
    public void save(Collection<Book> books) throws IllegalArgumentException{
        try
        {
            ObjectOutputStream objectOutput = new ObjectOutputStream(new FileOutputStream(fileName));
            objectOutput.writeObject(books);
            objectOutput.flush();
            objectOutput.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Collection<Book> load() throws IOException, ClassNotFoundException {
        Collection<Book> books = null;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(fileName));
            books = (Collection<Book>) objectInputStream.readObject();
            objectInputStream.close();
        }catch(FileNotFoundException e) {
            System.out.println("File does not exist. creating an empty collection");
            return new ArrayList<Book>();
        }
        return books;
    }

    public boolean add(Book book){
        try{
            Collection<Book> books = load();
            books.add(book);
            save(books);
        }catch(IOException | ClassNotFoundException e){
            return false;
        }
        return true;
    }

    @Override
    public Book find(String isbn) throws IllegalArgumentException {
        Collection<Book> books = null;
        try {
            books = load();
        }catch(IOException | ClassNotFoundException e){
            return null;
        }
        for(Book book : books){
            if(book.getIsbn() == isbn){
                return book;
            }
        }
        return null;
    }
}
