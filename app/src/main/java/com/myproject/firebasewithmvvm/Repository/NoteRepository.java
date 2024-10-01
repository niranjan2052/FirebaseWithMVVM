package com.myproject.firebasewithmvvm.Repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.myproject.firebasewithmvvm.Model.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteRepository {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference noteRef = db.collection("notes");
    private final MutableLiveData<List<Note>> notesLiveData = new MutableLiveData<>();

    public NoteRepository() {
        noteRef.addSnapshotListener((value, error) -> {
            if (error != null) {
                return;
            }
            if (value != null) {
                List<Note> notesList = new ArrayList<>();
                for (DocumentSnapshot doc : value.getDocuments()) {
                    Note note = doc.toObject(Note.class);
                    notesList.add(note);
                }
                notesLiveData.setValue(notesList);
            }
        });
    }

    //CURD Operation

    //Create Operation
    public void createNote(Note note) {
        //TODO: Create data in Firebase
        note.setId(noteRef.document().getId());
        noteRef.document(note.getId()).set(note);
    }

    //Update Operation
    public void updateNote(Note note) {
        //TODO: Update data in Firebase
        noteRef.document(note.getId()).set(note);
    }

    //Read Operation
    public LiveData<List<Note>> getNotes() {
        //TODO: Get data from Firebase
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
        //TODO: Delete data from Firebase
        noteRef.document(noteId).delete();
    }


}
