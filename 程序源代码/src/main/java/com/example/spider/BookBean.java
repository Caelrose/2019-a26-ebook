package com.example.spider;

import java.util.List;

public class BookBean {
    private String bookName;
    private String bookUrl;
    private List<ChapterBean> chapter;
    private int total_chapter;

    public BookBean(String bookName, String bookUrl, List<ChapterBean> chapter, int total_chapter) {
        this.bookName = bookName;
        this.bookUrl = bookUrl;
        this.chapter = chapter;
        this.total_chapter = total_chapter;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookUrl() {
        return bookUrl;
    }

    public void setBookUrl(String bookUrl) {
        this.bookUrl = bookUrl;
    }

    public List<ChapterBean> getChapter() {
        return chapter;
    }

    public void setChapter(List<ChapterBean> chapter) {
        this.chapter = chapter;
    }

    public int getTotal_chapter() {
        return total_chapter;
    }

    public void setTotal_chapter(int total_chapter) {
        this.total_chapter = total_chapter;
    }

    @Override
    public String toString() {
        return "BookBean{" +
                "bookName='" + bookName + '\'' +
                ", bookUrl='" + bookUrl + '\'' +
                ", chapter=" + chapter +
                ", total_chapter=" + total_chapter +
                '}';
    }
}
