package com.nikita.notes.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.nikita.notes.dao.TagNoteJoinDAO;
import com.nikita.notes.model.Tag;
import com.nikita.notes.model.TagNoteJoin;

import java.util.List;

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

    public void deleteTagNoteJoins(String noteId) {
        new DeleteTagsForNoteAsyncTask(tagNoteJoinDAO).execute(noteId);
    }

    public LiveData<List<Tag>> selectTagsForNote(String noteId) {
        return tagNoteJoinDAO.selectTagsForNote(noteId);
    }

    private static class InsertTagNoteJoinAsyncTask extends AsyncTask<TagNoteJoin, Void, Void> {
        private TagNoteJoinDAO tagNoteJoinDAO;

        private InsertTagNoteJoinAsyncTask(TagNoteJoinDAO tagNoteJoinDAO) {
            this.tagNoteJoinDAO = tagNoteJoinDAO;
        }

        @Override
        protected Void doInBackground(TagNoteJoin... tagNoteJoins) {
            final List<Tag> joins = tagNoteJoinDAO.getTagsForNote(tagNoteJoins[0].getNoteId());

            if (joins != null) {
                for (Tag tag : joins) {
                    if (tag.getId().equals(tagNoteJoins[0].getTagId())) {
                        return null;
                    }
                }
            }

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

    private static class DeleteTagsForNoteAsyncTask extends AsyncTask<String, Void, Void> {
        private TagNoteJoinDAO tagNoteJoinDAO;

        private DeleteTagsForNoteAsyncTask(TagNoteJoinDAO tagNoteJoinDAO) {
            this.tagNoteJoinDAO = tagNoteJoinDAO;
        }


        @Override
        protected Void doInBackground(String... noteId) {
            tagNoteJoinDAO.deleteTagsForNote(noteId[0]);
            return null;
        }
    }

}
