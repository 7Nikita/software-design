package com.nikita.notes.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.Calendar;

import com.nikita.notes.R;
import com.nikita.notes.model.Note;
import com.nikita.notes.viewmodel.NoteViewModel;

public class NoteActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "com.nikita.notes.EXTRA_ID";
    public static final String EXTRA_TITLE = "com.nikita.notes.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION = "com.nikita.notes.EXTRA_DESCRIPTION";

    private EditText editTextTitle;
    private EditText editTextDescription;

    private NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);
        
        Intent intent = getIntent();

        if(intent.hasExtra(EXTRA_ID)) {
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
        }

        setTitle(R.string.note_string);
    }

    private void saveNote() {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();

        if (title.trim().isEmpty()) {
            title = Calendar.getInstance().getTime().toString();
        }

        Note note = new Note(title, description);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            note.setId(id);
            noteViewModel.update(note);
        } else {
            noteViewModel.insert(note);
        }

        setResult(RESULT_OK);
        finish();
    }

    private void deleteNote() {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();

        Note note = new Note(title, description);
        
        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            note.setId(id);
            noteViewModel.delete(note);
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
}
