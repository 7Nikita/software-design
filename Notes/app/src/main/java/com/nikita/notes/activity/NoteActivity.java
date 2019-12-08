package com.nikita.notes.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.nikita.notes.R;
import com.nikita.notes.adapter.TagAdapter;
import com.nikita.notes.model.Note;
import com.nikita.notes.model.TagNoteJoin;
import com.nikita.notes.viewmodel.NoteViewModel;
import com.nikita.notes.viewmodel.TagNoteJoinViewModel;
import com.nikita.notes.viewmodel.TagViewModel;

public class NoteActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "com.nikita.notes.EXTRA_ID";
    public static final String EXTRA_DATE = "com.nikita.notes.EXTRA_DARE";
    public static final String EXTRA_TITLE = "com.nikita.notes.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION = "com.nikita.notes.EXTRA_DESCRIPTION";

    private EditText editTextTitle;
    private EditText editTextTagName;
    private EditText editTextDescription;

    private Note note;
    private boolean shouldDeleteNote = true;

    private TagViewModel tagViewModel;
    private NoteViewModel noteViewModel;
    private TagNoteJoinViewModel tagNoteJoinViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        tagViewModel = new ViewModelProvider(this).get(TagViewModel.class);
        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        tagNoteJoinViewModel = new ViewModelProvider(this).get(TagNoteJoinViewModel.class);

        final Button buttonAddTag = findViewById(R.id.button_add_tag);
        editTextTitle = findViewById(R.id.edit_text_title);
        editTextTagName = findViewById(R.id.edti_text_tag_name);
        editTextDescription = findViewById(R.id.edit_text_description);

        final RecyclerView recyclerView = findViewById(R.id.recycler_tagView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                RecyclerView.HORIZONTAL, false));

        final TagAdapter tagAdapter = new TagAdapter();
        recyclerView.setAdapter(tagAdapter);

        final Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            shouldDeleteNote = false;
            final String id = intent.getStringExtra(EXTRA_ID);
            final String title = intent.getStringExtra(EXTRA_TITLE);
            final String description = intent.getStringExtra(EXTRA_DESCRIPTION);

            editTextTitle.setText(title);
            editTextDescription.setText(description);

            note = new Note(title, description);
            note.setId(id);
        } else {
            note = new Note("", "");
            noteViewModel.insert(note);
        }

        tagNoteJoinViewModel.selectTagsForNote(note.getId()).observe(this, tagAdapter::setTags);

        buttonAddTag.setOnClickListener(v -> {
                    final String tagTitle = editTextTagName.getText().toString();
                    tagViewModel.makeLink(tagTitle, note.getId(), tagNoteJoinViewModel);
                    editTextTagName.setText("");
                }
        );

        tagAdapter.setOnItemClickListener(tag -> {
                    tagNoteJoinViewModel.delete(new TagNoteJoin(tag.getId(), note.getId()));
                }
        );

        setTitle(R.string.note_string);
    }

    private void saveNote() {
        shouldDeleteNote = false;
        final String title = editTextTitle.getText().toString();
        final String description = editTextDescription.getText().toString();

        note.setTitle(title.trim().isEmpty() ? note.getAddingDate() : title);
        note.setDescription(description);

        noteViewModel.update(note);

        setResult(RESULT_OK);
        finish();
    }

    private void deleteNote() {
        shouldDeleteNote = false;
        final String title = editTextTitle.getText().toString();
        final String description = editTextDescription.getText().toString();

        final Note note = new Note(title, description);

        final String id = getIntent().getStringExtra(EXTRA_ID);
        if (id != null) {
            note.setId(id);
            noteViewModel.delete(note, tagNoteJoinViewModel);
        }

        setResult(RESULT_OK);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save_note) {
            saveNote();
            return true;
        } else if (item.getItemId() == R.id.delete_note) {
            deleteNote();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (shouldDeleteNote) {
            noteViewModel.delete(note, tagNoteJoinViewModel);
        }
    }

}
