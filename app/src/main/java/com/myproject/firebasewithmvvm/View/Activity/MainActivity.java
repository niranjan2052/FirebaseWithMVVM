package com.myproject.firebasewithmvvm.View.Activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.myproject.firebasewithmvvm.Model.Note;
import com.myproject.firebasewithmvvm.View.Adapter.NoteAdapter;
import com.myproject.firebasewithmvvm.ViewModel.NoteViewModel;
import com.myproject.firebasewithmvvm.databinding.ActivityMainBinding;

import java.util.List;

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
        noteAdapter = new NoteAdapter();
        binding.noteRecyclerView.setAdapter(noteAdapter);


        noteViewModel.getNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                noteAdapter.setNotes(notes);
            }
        });

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
                noteAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(this, "Please Fill in both Fields", Toast.LENGTH_SHORT).show();
            }
        });

    }
}