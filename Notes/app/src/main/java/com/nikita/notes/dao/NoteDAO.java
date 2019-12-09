package com.nikita.notes.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.nikita.notes.model.Note;

import java.util.List;

@Dao
public interface NoteDAO {

    @Insert
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("DELETE FROM note_table")
    void clear();

    @Query("SELECT * FROM note_table")
    LiveData<List<Note>> getNotes();

    @Query("SELECT * FROM note_table ORDER BY title")
    LiveData<List<Note>> getNotesSortedByTitle();

    @Query("SELECT * FROM note_table ORDER BY addingDate DESC ")
    LiveData<List<Note>> getNotesSortedByDate();

}
