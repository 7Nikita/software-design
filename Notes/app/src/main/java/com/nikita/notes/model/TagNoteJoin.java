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
    private int tagId;

    @NonNull
    public int noteId;

    public TagNoteJoin(int tagId, int noteId) {
        this.tagId = tagId;
        this.noteId = noteId;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

}
