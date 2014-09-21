package com.github.alexghr.bulkshare.db;

public class Link {

    private String title, link;
    private long id, listId = 1;

    public Link(String title, String link, long id, long listId) {
        this.title = title;
        this.link = link;
        this.id = id;
        this.listId = listId;
    }

    public Link() {

    }

    public long getListId() {
        return listId;
    }

    public void setListId(long listId) {
        this.listId = listId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
