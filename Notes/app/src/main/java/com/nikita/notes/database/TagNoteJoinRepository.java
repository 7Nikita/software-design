package com.nikita.notes.database;

import android.app.Application;
import android.os.AsyncTask;

import com.nikita.notes.dao.TagNoteJoinDAO;
import com.nikita.notes.model.TagNoteJoin;

public class TagNoteJoinRepository {

    private TagNoteJoinDAO tagNoteJoinDAO;

    public TagNoteJoinRepository(Application application) {
        NoteDatabase database = NoteDatabase.getInstance(application);
        tagNoteJoinDAO = database.tagNoteJoinDAO();
    }

    public void insert(TagNoteJoin tagNoteJoin) {
        new InsertTagNoteJoinAsyncTask(tagNoteJoinDAO).execute(tagNoteJoin);
    }

    public void delete(TagNoteJoin tagNoteJoin) {
        new DeleteTagNoteJoinAsyncTask(tagNoteJoinDAO).execute(tagNoteJoin);
    }

    private static class InsertTagNoteJoinAsyncTask extends AsyncTask<TagNoteJoin, Void, Void> {
        private TagNoteJoinDAO tagNoteJoinDAO;

        private InsertTagNoteJoinAsyncTask(TagNoteJoinDAO tagNoteJoinDAO) {
            this.tagNoteJoinDAO = tagNoteJoinDAO;
        }

        @Override
        protected Void doInBackground(TagNoteJoin... tagNoteJoins) {
            tagNoteJoinDAO.insert(tagNoteJoins[0]);
            return null;
        }
    }

    private static class DeleteTagNoteJoinAsyncTask extends AsyncTask<TagNoteJoin, Void, Void> {
        private TagNoteJoinDAO tagNoteJoinDAO;

        private DeleteTagNoteJoinAsyncTask(TagNoteJoinDAO tagNoteJoinDAO) {
            this.tagNoteJoinDAO = tagNoteJoinDAO;
        }

        @Override
        protected Void doInBackground(TagNoteJoin... tagNoteJoins) {
            tagNoteJoinDAO.delete(tagNoteJoins[0]);
            return null;
        }
    }

}
