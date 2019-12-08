package com.nikita.notes.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nikita.notes.adapter.NoteAdapter;
import com.nikita.notes.adapter.TagAdapter;
import com.nikita.notes.viewmodel.NoteViewModel;
import com.nikita.notes.R;
import com.nikita.notes.viewmodel.TagNoteJoinViewModel;
import com.nikita.notes.viewmodel.TagViewModel;

public class MainActivity extends AppCompatActivity {

    private TagViewModel tagViewModel;
    private NoteViewModel noteViewModel;
    private TagNoteJoinViewModel tagNoteJoinViewModel;

    private Switch aSwitch;
    private RecyclerView recyclerView;

    private final TagAdapter tagAdapter = new TagAdapter();
    private final NoteAdapter noteAdapter = new NoteAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton buttonAddNote = findViewById(R.id.button_add_note);
        buttonAddNote.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, NoteActivity.class);
            startActivity(intent);
        });

        recyclerView = findViewById(R.id.recycler_view);
        tagViewModel = new ViewModelProvider(this).get(TagViewModel.class);
        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        tagNoteJoinViewModel = new ViewModelProvider(this).get(TagNoteJoinViewModel.class);

        setAdapterOnClickListeners();

        int orientation = this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(noteAdapter);
        noteViewModel.getNotes().observe(this, noteAdapter::setNotes);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.switch_mode);
        item.setActionView(R.layout.switch_layout);

        aSwitch = item.getActionView().findViewById(R.id.switchForActionBar);
        aSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                recyclerView.setAdapter(tagAdapter);
                tagViewModel.getTags().observe(this, tagAdapter::setTags);
            } else {
                recyclerView.setAdapter(noteAdapter);
                noteViewModel.getNotes().observe(this, noteAdapter::setNotes);
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.delete_all_notes) {
            noteViewModel.clear();
            return true;
        } else if (item.getItemId() == R.id.button_refresh) {
            recyclerView.setAdapter(noteAdapter);
            noteViewModel.getNotes().observe(this, noteAdapter::setNotes);
            aSwitch.setChecked(false);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setAdapterOnClickListeners() {
        noteAdapter.setOnItemClickListener(note -> {
            Intent intent = new Intent(MainActivity.this, NoteActivity.class);
            intent.putExtra(NoteActivity.EXTRA_ID, note.getId());
            intent.putExtra(NoteActivity.EXTRA_TITLE, note.getTitle());
            intent.putExtra(NoteActivity.EXTRA_DATE, note.getAddingDate());
            intent.putExtra(NoteActivity.EXTRA_DESCRIPTION, note.getDescription());

            startActivity(intent);
        });
        tagAdapter.setOnItemClickListener(tag -> {
            recyclerView.setAdapter(noteAdapter);
            tagNoteJoinViewModel.selectNotesForTag(tag.getId()).observe(this, noteAdapter::setNotes);
            aSwitch.setChecked(false);
        });
    }

}
