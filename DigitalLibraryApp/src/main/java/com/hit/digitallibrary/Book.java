package com.hit.digitallibrary;

import java.io.Serializable;

public class Book implements Serializable{
    private static final long serialVersionUID = 1L;

    private String isbn;
    private String title;
    private String author;

    public Book(String isbn, String title, String author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' + "}";
    }

    @Override
    public boolean equals(Object obj){
        if( !(obj instanceof  Book)) return false;
        Book other = (Book)obj;
        return this.isbn.equals(other.isbn);
    }

    @Override
    public int hashCode(){
        return this.isbn.hashCode();
    }

}
