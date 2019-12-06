package com.nikita.notes.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.nikita.notes.database.TagRepository;
import com.nikita.notes.model.Tag;

import java.util.List;

public class TagViewModel extends AndroidViewModel {
    private TagRepository repository;
    private LiveData<List<Tag>> tags;

    public TagViewModel(@NonNull Application application) {
        super(application);
        repository = new TagRepository(application);
        tags = repository.getTags();
    }

    public void insert(Tag tag) { repository.insert(tag); }

    public void update(Tag tag) { repository.update(tag); }

    public void delete(Tag tag) { repository.delete(tag); }

    public LiveData<List<Tag>> getTags() { return repository.getTags(); }

    public void makeLink(String tagTitle, String noteId, TagNoteJoinViewModel tagNoteJoinViewModel) {
        repository.makeLink(tagTitle, noteId, tagNoteJoinViewModel);
    }
}
