package com.nikita.notes.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.nikita.notes.dao.NoteDAO;
import com.nikita.notes.model.Note;

import java.util.List;

public class NoteRepository {

    private NoteDAO noteDAO;

    private LiveData<List<Note>> notes;

    public NoteRepository(Application application) {
        NoteDatabase database = NoteDatabase.getInstance(application);
        noteDAO = database.noteDAO();
        notes = noteDAO.getNotes();
    }

    public void insert(Note note) {
        new InsertNoteAsyncTask(noteDAO).execute(note);
    }

    public void update(Note note) {
        new UpdateNoteAsyncTask(noteDAO).execute(note);
    }

    public void delete(Note note) {
        new DeleteNoteAsyncTask(noteDAO).execute(note);
    }

    public void clear() {
        new ClearNoteAsyncTask(noteDAO).execute();
    }

    public LiveData<List<Note>> getNotes() {
        return notes;
    }

    private static class InsertNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDAO noteDAO;

        private InsertNoteAsyncTask(NoteDAO noteDAO) {
            this.noteDAO = noteDAO;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDAO.insert(notes[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDAO noteDAO;

        private UpdateNoteAsyncTask(NoteDAO noteDAO) {
            this.noteDAO = noteDAO;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDAO.update(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDAO noteDAO;

        private DeleteNoteAsyncTask(NoteDAO noteDAO) {
            this.noteDAO = noteDAO;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDAO.delete(notes[0]);
            return null;
        }
    }

    private static class ClearNoteAsyncTask extends AsyncTask<Void, Void, Void> {
        private NoteDAO noteDAO;

        private ClearNoteAsyncTask(NoteDAO noteDAO) {
            this.noteDAO = noteDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDAO.clear();
            return null;
        }
    }

}
