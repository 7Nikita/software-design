package com.nikita.notes.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.nikita.notes.TimestampConverter;

import java.util.UUID;
import java.util.Date;

@Entity(tableName = "note_table")
public class Note {

    @NonNull
    @PrimaryKey
    private String id = UUID.randomUUID().toString();

    private String title;

    private String description;

    @TypeConverters({TimestampConverter.class})
    private String addingDate;

    public Note(String title, String description) {
        this.title = title;
        this.description = description;
        this.addingDate = TimestampConverter.toTimestamp(new Date());
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
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

    public String getAddingDate() { return addingDate; }

    public void setAddingDate(String addingDate) { this.addingDate = addingDate; }

}
