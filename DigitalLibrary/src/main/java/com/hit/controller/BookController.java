package com.hit.controller;

import java.util.Collection;

import com.hit.algorithm.LRUAlgoCacheImpl;
import com.hit.dm.Book;
import com.hit.service.BookService;
import com.hit.util.Controller;

public class BookController implements Controller<String, Book> {

    private BookService bookService;

    public BookController() {
        bookService = new BookService(new LRUAlgoCacheImpl<Book>());
    }

    @Override
    public Book get(Book book) {
        return bookService.getByBook(book);
    }
    @Override
    public Collection<Book> get() {
        return bookService.ShowBooks();
    }
    @Override
    public void post(Book book) {
        bookService.addByBook(book);
    }
}
