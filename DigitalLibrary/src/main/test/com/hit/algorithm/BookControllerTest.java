package com.hit.algorithm;

import java.util.Collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.hit.controller.BookController;
import com.hit.dm.Book;

class BookControllerTest {
    private BookController controller;

    @BeforeAll
    public void init() {
        controller = new BookController();
    }

    @Test
    void check_put_get() {

        Book book = createBook();
        controller.post(book);
        Book b1 = controller.get(new Book(book.getIsbn(), "", ""));
        Assertions.assertEquals(true, b1.equals(book));
    }

    @Test
    void check_get_invalid_isbn() {
        long rnd = System.currentTimeMillis();
        String isbn = "isbnX" + rnd;

        Book b1 = controller.get(new Book(isbn, "", ""));
        Assertions.assertEquals(true, b1==null);
    }

    @Test
    void check_getList() {
        controller.post(createBook());
        controller.post(createBook());
        controller.post(createBook());
        Collection<Book> books = controller.get();
        Assertions.assertEquals(true, books.size() >=3);
    }

    private Book createBook() {
        long rnd = System.nanoTime();
        String isbn = "isbn" + rnd;
        String title = "title" + rnd;
        String author = "author" + rnd;
        Book book = new Book(isbn, title, author);
        return book;
    }
}