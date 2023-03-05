package com.example.noteandlistmemoapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.fragment.app.DialogFragment;

public class NoteDialogFragment extends DialogFragment {
    Button btnSave, btnDelete;
    EditText etSubject, etNote;
    RadioGroup rgPriority;
    RadioButton rbLow, rbMedium, rbHigh;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dialog_expand_note, container);
        getDialog().setTitle("Select Date");


        return view;
    }
}
