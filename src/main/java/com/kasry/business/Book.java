package com.kasry.business;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hamzadev on 28/09/17.
 */
public class Book {
    private String isbn;
    private String title;
    private String authors;
    private String description;

    public Book(String isbn, String title, String authors, String description) {
        this.isbn = isbn;
        this.title = title;
        this.authors = authors;
        this.description = description;
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

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, String> toMap(){
        Map<String, String> bookMap = new HashMap<String, String>();
        bookMap.put("isbn", this.isbn);
        bookMap.put("title", this.title);
        bookMap.put("authors", this.authors);
        bookMap.put("description", this.description);
        return bookMap;
    }
}
