package com.nikita.notes.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.nikita.notes.model.Note;
import com.nikita.notes.database.NoteRepository;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    private NoteRepository repository;
    private LiveData<List<Note>> notes;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository = new NoteRepository(application);
        notes = repository.getNotes();
    }

    public void insert(Note note) {
        repository.insert(note);
    }

    public void update(Note note) {
        repository.update(note);
    }

    public void delete(Note note, TagNoteJoinViewModel tagNoteJoinViewModel) {
        repository.delete(note, tagNoteJoinViewModel);
    }

    public void clear() {
        repository.clear();
    }

    public LiveData<List<Note>> getNotes() {
        return repository.getNotes();
    }

}
