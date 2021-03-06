package com.nikita.notes.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

import static androidx.room.ForeignKey.CASCADE;

@Entity(
        tableName = "tag_note_join",
        primaryKeys = {"tagId", "noteId"},
        indices = {@Index("tagId"), @Index("noteId")},
        foreignKeys = {
                @ForeignKey(
                        entity = Tag.class,
                        parentColumns = "id",
                        childColumns = "tagId",
                        onDelete = CASCADE
                ),
                @ForeignKey(
                        entity = Note.class,
                        parentColumns = "id",
                        childColumns = "noteId",
                        onDelete = CASCADE
                )
        }
)
public class TagNoteJoin {

    @NonNull
    private String tagId;

    @NonNull
    private String noteId;

    public TagNoteJoin(@NonNull String tagId, @NonNull String noteId) {
        this.tagId = tagId;
        this.noteId = noteId;
    }

    @NonNull
    public String getTagId() {
        return tagId;
    }

    public void setTagId(@NonNull String tagId) {
        this.tagId = tagId;
    }

    @NonNull
    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(@NonNull String noteId) {
        this.noteId = noteId;
    }

}
