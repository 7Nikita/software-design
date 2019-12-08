package com.nikita.notes.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.nikita.notes.model.Note;
import com.nikita.notes.model.Tag;
import com.nikita.notes.model.TagNoteJoin;

import java.util.List;

@Dao
public interface TagNoteJoinDAO {

    @Insert
    void insert(TagNoteJoin tagNoteJoin);

    @Delete
    void delete(TagNoteJoin tagNoteJoin);

    @Query("SELECT note_table.id, note_table.title, note_table.description, note_table.addingDate " +
            "FROM note_table " +
            "INNER JOIN tag_note_join ON note_table.id = tag_note_join.noteId " +
            "WHERE tag_note_join.tagId = :tagId")
    LiveData<List<Note>> selectNotesForTag(String tagId);

    @Query("SELECT tag_table.id, tag_table.title FROM tag_table " +
            "INNER JOIN tag_note_join ON tag_table.id = tag_note_join.tagId " +
            "WHERE tag_note_join.noteId = :noteId")
    LiveData<List<Tag>> selectTagsForNote(String noteId);

    @Query("SELECT tag_table.id, tag_table.title FROM tag_table " +
            "INNER JOIN tag_note_join ON tag_table.id = tag_note_join.tagId " +
            "WHERE tag_note_join.noteId = :noteId")
    List<Tag> getTagsForNote(String noteId);

    @Query("DELETE FROM tag_note_join " +
            "WHERE noteId = :noteId")
    void deleteTagsForNote(String noteId);

}
