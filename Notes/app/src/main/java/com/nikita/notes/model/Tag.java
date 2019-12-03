package com.nikita.notes.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tag_table")
public class Tag {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;

    public Tag(String title) {
        this.title = title;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

}
