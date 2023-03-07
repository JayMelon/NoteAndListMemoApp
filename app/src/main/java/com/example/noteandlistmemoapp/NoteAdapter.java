package com.example.noteandlistmemoapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter {
    private ArrayList<Note> noteData;
    private static View.OnClickListener mOnItemClickListener;
    private boolean isDeleting;
    private Context parentContext;

    public NoteAdapter(ArrayList<Note> arrayList, Context context) {
        noteData = arrayList;
        parentContext = context;
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView priorityTextView;
        public Button deleteButton;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.textView_Adapter_Subject);
            priorityTextView = itemView.findViewById(R.id.textView_Adapter_Priority);
            deleteButton = itemView.findViewById(R.id.button_Adapter_Delete);
            itemView.setTag(this);
            itemView.setOnClickListener(mOnItemClickListener);
        }

        public TextView getTitleTextView() {
            return titleTextView;
        }

        public TextView getPriorityTextView() {
            return priorityTextView;
        }

        public Button getDeleteButton() {
            return deleteButton;
        }
    }

    public static void setOnItemClickListener(View.OnClickListener itemClickListener) {
        mOnItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_note, parent, false);
        return new NoteViewHolder(v);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        NoteViewHolder nvh = (NoteViewHolder) holder;
        String currentTitle = noteData.get(position).getTitle();
        int currentPriority = noteData.get(position).getPriority();

        if (isDeleting) {
            nvh.getDeleteButton().setVisibility(View.VISIBLE);
            nvh.getDeleteButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteItem(position);
                }
            });
        } else {
            nvh.getDeleteButton().setVisibility(View.VISIBLE);
        }
    }

    public void setDelete(boolean b) {
        isDeleting = b;
    }

    private void deleteItem(int position) {
        Note note = noteData.get(position);
        DataSource ds = new DataSource(parentContext);

        try {
            ds.open();
            boolean didDelete = ds.deleteNote(note.getNoteID());
            ds.close();
            if(didDelete) {
                noteData.remove(position);
                notifyDataSetChanged();
            } else {
                Toast.makeText(parentContext, "Delete Failed", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return noteData.size();
    }

}
