package com.myproject.firebasewithmvvm.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.myproject.firebasewithmvvm.Model.Note;
import com.myproject.firebasewithmvvm.Repository.NoteRepository;

import java.util.List;

public class NoteViewModel extends ViewModel {
    private final NoteRepository repository;
    private final LiveData<List<Note>> notesLiveData;

    public NoteViewModel() {
        repository = new NoteRepository();
        notesLiveData = repository.getNotes();
    }

    public LiveData<List<Note>> getNotes() {
        return notesLiveData;
    }

    public void createNote(Note note) {
        repository.createNote(note);
    }

    public void updateNote(Note note) {
        repository.updateNote(note);
    }

    public void deleteNote(String noteId) {
        repository.deleteNote(noteId);
    }
}
