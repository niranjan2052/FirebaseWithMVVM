package com.myproject.firebasewithmvvm.Repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.Firebase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.myproject.firebasewithmvvm.Model.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteRepository {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference noteRef = db.collection("notes");
    private MutableLiveData<List<Note>> notesLiveData = new MutableLiveData<>();
    //CURD Operation

    //Create Operation
    public void createNote(Note note) {
        //TODO: Not Yet Implemented // Will create data in Firebase
        note.setId(noteRef.document().getId());
        noteRef.document(note.getId()).set(note);
    }

    //Update Operation
    public void updateNote(Note note) {
        //TODO: Not Yet Implemented // Will update data in Firebase
        noteRef.document(note.getId()).set(note);
    }

    //Read Operation
    public LiveData<List<Note>> getNotes() {
        //TODO: Not Yet implemented // Will get data from Firebase
        noteRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Note> noteList = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Note note = document.toObject(Note.class);
                    noteList.add(note);
                }
                notesLiveData.setValue(noteList);
            }
        });
        return notesLiveData;
    }

    //Delete Operation
    public void deleteNote(String noteId) {
        //TODO: Not Yet implemented // Will delete data from Firebase
        noteRef.document(noteId).delete();
    }


}
