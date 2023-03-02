package com.example.noteandlistmemoapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter {
    private ArrayList<Note> noteArrayList;
    private static View.OnClickListener mOnItemClickListener;
    private boolean isDeleting;
    private Context parentContext;

    public NoteAdapter(ArrayList<Note> arrayList, Context context) {
        noteArrayList = arrayList;
        parentContext = context;
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView priorityTextView;
        public Button deleteButton;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            //titleTextView = itemView.findViewById(R.id.___);
            //priorityTextView = itemView.findViewById(R.id.___);
            //deleteButton = itemView.findViewById(R.id.___);
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
        //View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.___, parent, false);
        return new NoteViewHolder;
    }
}
