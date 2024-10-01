package com.myproject.firebasewithmvvm.View.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myproject.firebasewithmvvm.Model.Note;
import com.myproject.firebasewithmvvm.R;
import com.myproject.firebasewithmvvm.databinding.ItemNoteBinding;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private List<Note> notes = new ArrayList<>();
    String TAG = "NoteAdapter";


    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note currentNote = notes.get(position);
        Log.d(TAG, "onBindViewHolder: " + currentNote.getId());
        holder.binding.titleTextView.setText(currentNote.getTitle());
        holder.binding.descriptionTextView.setText(currentNote.getDescription());

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }


    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        ItemNoteBinding binding;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemNoteBinding.bind(itemView);
        }
    }
}
