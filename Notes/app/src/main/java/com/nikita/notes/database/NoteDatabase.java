package com.nikita.notes.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.nikita.notes.dao.NoteDAO;
import com.nikita.notes.dao.TagDAO;
import com.nikita.notes.dao.TagNoteJoinDAO;
import com.nikita.notes.model.Note;
import com.nikita.notes.model.Tag;
import com.nikita.notes.model.TagNoteJoin;

@Database(entities = {Note.class, Tag.class, TagNoteJoin.class}, version = 4)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;

    public abstract NoteDAO noteDAO();

    public abstract TagDAO tagDAO();

    public abstract TagNoteJoinDAO tagNoteJoinDAO();

    public static synchronized NoteDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    NoteDatabase.class,
                    "note_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}
