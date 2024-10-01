package com.myproject.firebasewithmvvm.Model;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Note {
    private String id;
    private String title;
    private String description;
    @ServerTimestamp
    private Date date;

    public Note() {
    }

    public Note(String id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }
}
