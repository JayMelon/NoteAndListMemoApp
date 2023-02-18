package com.example.noteandlistmemoapp;

import java.util.Calendar;

public class Note {
    private int noteID;
    private String title;
    private String content;
    private String priority;
    private Calendar creationTime;
    private Calendar dueTime;

    public Note() {
        noteID = -1;
        creationTime = Calendar.getInstance();
    }

    public int getNoteID() {
        return noteID;
    }

    public void setNoteID(int noteID) {
        this.noteID = noteID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Calendar getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Calendar creationTime) {
        this.creationTime = creationTime;
    }

    public Calendar getDueTime() {
        return dueTime;
    }

    public void setDueTime(Calendar dueTime) {
        this.dueTime = dueTime;
    }
}
