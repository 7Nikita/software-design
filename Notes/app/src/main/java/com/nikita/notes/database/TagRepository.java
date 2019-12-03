package com.nikita.notes.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.nikita.notes.dao.TagDAO;
import com.nikita.notes.model.Tag;

import java.util.List;

public class TagRepository {

    private TagDAO tagDAO;

    private LiveData<List<Tag>> tags;

    public TagRepository(Application application) {
        NoteDatabase database = NoteDatabase.getInstance(application);
        tagDAO = database.tagDAO();
        tags = tagDAO.getTags();
    }

    public void insert(Tag tag) { new InsertTagAsyncTask(tagDAO).execute(tag); }

    public void delete(Tag tag) { new DeleteTagAsyncTask(tagDAO).execute(tag); }

    public void update(Tag tag) { new UpdateTagAsyncTask(tagDAO).execute(tag); }

    public LiveData<List<Tag>> getTags() { return tags; }

    private static class InsertTagAsyncTask extends AsyncTask<Tag, Void, Void> {
        private TagDAO tagDAO;

        private InsertTagAsyncTask(TagDAO tagDAO) { this.tagDAO = tagDAO; }

        @Override
        protected Void doInBackground(Tag... tags) {
            tagDAO.insert(tags[0]);
            return null;
        }
    }

    private static class DeleteTagAsyncTask extends AsyncTask<Tag, Void, Void> {
        private TagDAO tagDAO;

        private DeleteTagAsyncTask(TagDAO tagDAO) { this.tagDAO = tagDAO; }

        @Override
        protected Void doInBackground(Tag... tags) {
            tagDAO.delete(tags[0]);
            return null;
        }
    }

    private static class UpdateTagAsyncTask extends AsyncTask<Tag, Void, Void> {
        private TagDAO tagDAO;

        private UpdateTagAsyncTask(TagDAO tagDAO) { this.tagDAO = tagDAO; }

        @Override
        protected Void doInBackground(Tag... tags) {
            tagDAO.update(tags[0]);
            return null;
        }
    }

}
