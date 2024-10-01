package com.myproject.firebasewithmvvm.View.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myproject.firebasewithmvvm.Model.Note;
import com.myproject.firebasewithmvvm.R;
import com.myproject.firebasewithmvvm.ViewModel.NoteViewModel;
import com.myproject.firebasewithmvvm.databinding.DialogUpdateNoteBinding;
import com.myproject.firebasewithmvvm.databinding.ItemNoteBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

        // Set a click listener on each item to open the update dialog
        holder.binding.ivEditNote.setOnClickListener(view -> {
            showUpdateNoteDialog(holder.itemView.getContext(), currentNote, position);
        });

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    private void showUpdateNoteDialog(Context context, Note currentNote, int position) {
        Dialog dialog = new Dialog(context);
        DialogUpdateNoteBinding binding;
        binding = DialogUpdateNoteBinding.inflate(LayoutInflater.from(context));
        dialog.setContentView(binding.getRoot());

        //Set dialog width to 80% of screen width
        int screenWidth = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.8);
        Objects.requireNonNull(dialog.getWindow()).setLayout(screenWidth, ViewGroup.LayoutParams.WRAP_CONTENT);

        //Set the current values in the fields
        binding.updateTitleEditText.setText(currentNote.getTitle());
        binding.updateDescriptionEditText.setText(currentNote.getDescription());

        //Set a click listener fro the update button
        binding.updateButton.setOnClickListener(view -> {
            String updatedTitle = binding.updateTitleEditText.getText().toString();
            String updatedDescription = binding.updateDescriptionEditText.getText().toString();

            viewModel.updateNote(new Note(currentNote.getId(), updatedTitle, updatedDescription));
            //Notify the adapter about the updated item
            notifyItemChanged(position);
            dialog.dismiss(); // Close the dialog

        });
        dialog.show();
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
