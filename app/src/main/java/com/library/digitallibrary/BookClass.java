package com.library.digitallibrary;

public class BookClass {
    public String username,bookName;
    public BookClass() {

    }

    public BookClass(String username, String bookName) {
        this.username = username;
        this.bookName = bookName;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

