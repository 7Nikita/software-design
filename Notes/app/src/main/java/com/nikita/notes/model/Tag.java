package com.nikita.notes.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(
        tableName = "tag_table",
        indices = {
                @Index(value = "title", unique = true)
        }
)
public class Tag {

    @NonNull
    @PrimaryKey
    private String id = UUID.randomUUID().toString();

    private String title;

    public Tag(String title) {
        this.title = title;
    }

    @NonNull
    public String getId() { return id; }

    public void setId(@NonNull String id) { this.id = id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

}
