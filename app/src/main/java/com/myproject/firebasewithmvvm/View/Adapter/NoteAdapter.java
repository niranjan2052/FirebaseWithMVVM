package com.myproject.firebasewithmvvm.View.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myproject.firebasewithmvvm.Model.Note;
import com.myproject.firebasewithmvvm.R;
import com.myproject.firebasewithmvvm.ViewModel.NoteViewModel;
import com.myproject.firebasewithmvvm.databinding.ItemNoteBinding;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private List<Note> notes = new ArrayList<>();
    NoteViewModel viewModel;

    public NoteAdapter(NoteViewModel viewModel) {
        this.viewModel = viewModel;
    }


    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note currentNote = notes.get(position);
        holder.binding.titleTextView.setText(currentNote.getTitle());
        holder.binding.descriptionTextView.setText(currentNote.getDescription());

        holder.binding.ivDeleteNote.setOnClickListener(view -> new AlertDialog.Builder(holder.itemView.getContext())
                .setTitle("Delete")
                .setMessage("Do you really want to Delete this Note")
                .setIcon(R.drawable.ic_delete)
                .setPositiveButton("Yes", (dialogInterface, i) -> viewModel.deleteNote(currentNote.getId())).setNegativeButton("No", null).show());

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    @SuppressLint("NotifyDataSetChanged")
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
