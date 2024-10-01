package com.myproject.firebasewithmvvm.View.Activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.myproject.firebasewithmvvm.Model.Note;
import com.myproject.firebasewithmvvm.View.Adapter.NoteAdapter;
import com.myproject.firebasewithmvvm.ViewModel.NoteViewModel;
import com.myproject.firebasewithmvvm.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private NoteViewModel noteViewModel;
    private NoteAdapter noteAdapter;

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        binding.noteRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        noteAdapter = new NoteAdapter(noteViewModel);
        binding.noteRecyclerView.setAdapter(noteAdapter);

        LoadData();


        binding.saveButton.setOnClickListener(view -> {
            String title = binding.titleEditText.getText().toString();
            String description = binding.descriptionEditText.getText().toString();

            if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(description)) {
                // Create new note and add it via ViewModel
                Note newNote = new Note(null, title, description);
                noteViewModel.createNote(newNote); // This will trigger the LiveData change

                // Clear input fields after saving
                binding.titleEditText.setText("");
                binding.descriptionEditText.setText("");
            } else {
                Toast.makeText(this, "Please Fill in both Fields", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void LoadData() {
        noteViewModel.getNotes().observe(this, notes -> {
            if (notes.isEmpty()) {
                binding.noteRecyclerView.setVisibility(View.GONE);
                binding.ivNoItem.setVisibility(View.VISIBLE);
            } else {
                binding.noteRecyclerView.setVisibility(View.VISIBLE);
                binding.ivNoItem.setVisibility(View.GONE);
            }
            noteAdapter.setNotes(notes);
        });
    }
}