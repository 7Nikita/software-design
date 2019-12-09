package com.nikita.notes.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.nikita.notes.dao.TagDAO;
import com.nikita.notes.model.Tag;
import com.nikita.notes.model.TagNoteJoin;
import com.nikita.notes.viewmodel.TagNoteJoinViewModel;

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

    public LiveData<List<Tag>> getTagsSortedByTitle() { return tagDAO.getTagsSortedByTitle(); }

    public void makeLink(String tagTitle, String noteId, TagNoteJoinViewModel tagNoteJoinViewModel) {
        TagExtendedParams params = new TagExtendedParams(tagTitle, noteId, tagNoteJoinViewModel);
        new MakeLinkAsyncTask(tagDAO).execute(params);
    }

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

    private static class MakeLinkAsyncTask extends  AsyncTask<TagExtendedParams, Void, Void> {
        private TagDAO tagDAO;

        private MakeLinkAsyncTask(TagDAO tagDAO) { this.tagDAO = tagDAO; }

        @Override
        protected Void doInBackground(TagExtendedParams... params) {
            Tag tag = null;

            final List<Tag> tags = tagDAO.getTagList();

            if (tags != null) {
                for (Tag el : tags) {
                    if (el.getTitle().equals(params[0].tagTitle)) {
                        tag = el;
                        break;
                    }
                }
            }

            if (tag == null) {
                tag = new Tag(params[0].tagTitle);
                tagDAO.insert(tag);
            }

            params[0].tagNoteJoinViewModel.insert(new TagNoteJoin(tag.getId(), params[0].noteId));
            return null;
        }
    }


    private class TagExtendedParams {
        public String tagTitle;
        public String noteId;
        public TagNoteJoinViewModel tagNoteJoinViewModel;

        public TagExtendedParams(String tagTitle, String noteId, TagNoteJoinViewModel tagNoteJoinViewModel) {
            this.tagTitle = tagTitle;
            this.noteId = noteId;
            this.tagNoteJoinViewModel = tagNoteJoinViewModel;
        }
    }
}
