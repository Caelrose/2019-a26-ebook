package com.example.spider;

public class ChapterBean {
    private String chapterName;
    private String chapterUrl;
    private int currentLength;
    private  int totalLength;

    public ChapterBean(String chapterName, String chapterUrl, int currentLength, int totalLength) {
        this.chapterName = chapterName;
        this.chapterUrl = chapterUrl;
        this.currentLength = currentLength;
        this.totalLength = totalLength;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getChapterUrl() {
        return chapterUrl;
    }

    public void setChapterUrl(String chapterUrl) {
        this.chapterUrl = chapterUrl;
    }

    public int getCurrentLength() {
        return currentLength;
    }

    public void setCurrentLength(int currentLength) {
        this.currentLength = currentLength;
    }

    public int getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(int totalLength) {
        this.totalLength = totalLength;
    }

    @Override
    public String toString() {
        return "ChapterBean{" +
                "chapterName='" + chapterName + '\'' +
                ", chapterUrl='" + chapterUrl + '\'' +
                ", currentLength=" + currentLength +
                ", totalLength=" + totalLength +
                '}';
    }
}
