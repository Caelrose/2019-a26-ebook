package com.example.spider;

public class bookhistory {
        String bookname;
        int charer;
        int readingcharer;
        public bookhistory(String bn, int charer, int readingcharer)
        {
            this.bookname=bn;
            this.charer=charer;
            this.readingcharer=readingcharer;
        }
    public int getCharer() {
        return charer;
    }

    public int getReadingcharer() {
        return readingcharer;
    }

    public String getBookname() {
        return bookname;
    }
}
