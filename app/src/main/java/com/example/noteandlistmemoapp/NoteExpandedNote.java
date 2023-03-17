package com.example.noteandlistmemoapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Locale;

public class NoteExpandedNote extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private Button dateButton;
    private Button timeButton;
    private Button saveButton;
    private Button deleteButton;
    private EditText titleEdit, noteEdit;
    private RadioGroup radioGroupSelectPriority;

    Note currentNote;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand_note);
        initDatePicker();
        initContent();
        initTimePicker();
        initSaveButton();
        //Inits Radiogroup
        radioGroupSelectPriority = findViewById(R.id.radioGroup_SelectPriority);
        timeButton = findViewById(R.id.timePickerButton);
        timeButton.setText(getCurrentTime());
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());
    }
    //Returns int 1-3 depending on the Selected radiogroups
    private int getSelectedPriority() {
        int selectedId = radioGroupSelectPriority.getCheckedRadioButtonId();
        switch (selectedId) {
            case R.id.radioButton_SelectPriority_Low:
                return 1;
            case R.id.radioButton_SelectPriority_Med:
                return 2;
            case R.id.radioButton_SelectPriority_High:
                return 3;
            default:
                return 0; // Return 0 or an appropriate default value if none of the options are selected
        }
    }

//Method that returns a String of today's Date
    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month++;
        int day = cal.get(Calendar.DAY_OF_MONTH);
       return makeDateString(day, month, year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = makeDateString(dayOfMonth, month, year);
                dateButton.setText(date);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

//Returns the given month from an int to a String of the month
    private String getMonthFormat(int month) {
        String monthName;
        switch (month) {
            case 1:
                monthName = "Jan";
                break;
            case 2:
                monthName = "Feb";
                break;
            case 3:
                monthName = "Mar";
                break;
            case 4:
                monthName = "Apr";
                break;
            case 5:
                monthName = "May";
                break;
            case 6:
                monthName = "Jun";
                break;
            case 7:
                monthName = "Jul";
                break;
            case 8:
                monthName = "Aug";
                break;
            case 9:
                monthName = "Sep";
                break;
            case 10:
                monthName = "Oct";
                break;
            case 11:
                monthName = "Nov";
                break;
            case 12:
                monthName = "Dec";
                break;
            default:
                monthName = "Inv";
                break;
        }

        return monthName.toUpperCase();
    }
//Opens the dialog
    public void openDatePicker(View view) {
datePickerDialog.show();
    }

    private void initTimePicker() {
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String time = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                timeButton.setText(time);
            }
        };

        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        timePickerDialog = new TimePickerDialog(this, style, timeSetListener, hour, minute, true);
    }
    private String getCurrentTime() {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        return String.format(Locale.getDefault(), "%02d:%02d", hour, minute);
    }
    public void openTimePicker(View view) {
        timePickerDialog.show();
    }
    private void initSaveButton(){
        saveButton = findViewById(R.id.noteDialog_Button_Save);
        currentNote = new Note();
saveButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        currentNote.setTitle(titleEdit.getText().toString());
        currentNote.setContent(noteEdit.getText().toString());
        currentNote.setPriority(getSelectedPriority());
        boolean wasSuccessful;
        DataSource ds = new DataSource(NoteExpandedNote.this);
        try{
            ds.open();
            wasSuccessful = ds.insertNote(currentNote);
            if(wasSuccessful){
                int newID = ds.getLastNoteID();
                currentNote.setNoteID(newID);
                System.out.println("Added Note");
            }
            System.out.println("Updating");
            ds.updateNote(currentNote);
            ds.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
});
    }
    private void initDeleteButton(){
        deleteButton = findViewById(R.id.noteDialog_Button_Delete);
    }
    private void initContent(){
        titleEdit = findViewById(R.id.editText_ExpandedNote_editTitle);
        noteEdit = findViewById(R.id.editText_expandNote_editNote);
    }
}