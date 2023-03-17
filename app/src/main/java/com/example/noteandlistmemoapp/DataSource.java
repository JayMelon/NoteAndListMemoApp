package com.example.noteandlistmemoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Calendar;

public class DataSource {
    private SQLiteDatabase database;
    private DatabaseHelper databaseHelper;

    public DataSource(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        databaseHelper.close();
    }

    // Inserts a Note into the database. Returns true/false based on whether the delete was successful
    public boolean insertNote(Note note) {

        boolean didSucceed = false;
        try {
            ContentValues initialValues = new ContentValues();
            initialValues.put(DatabaseHelper.TITLE, note.getTitle());
            initialValues.put(DatabaseHelper.CONTENT, note.getContent());
            initialValues.put(DatabaseHelper.PRIORITY, note.getPriority());
            initialValues.put(DatabaseHelper.DUE_TIME, note.getDueTime().toString());

            didSucceed = database.insert(DatabaseHelper.NOTES_TABLE, null, initialValues) > 0;

        } catch (Exception e) {

        }
        return didSucceed;
    }

    // Updates a Note that is already in the database. Returns true/false based on whether the delete was successful
    public boolean updateNote(Note note) {
        boolean didSucceed = false;
        try {
            String whereClause  = DatabaseHelper.ID + " = ";
            Long rowID = Long.valueOf(note.getNoteID());
            didSucceed = database.update(DatabaseHelper.NOTES_TABLE, notePutter(note), whereClause + rowID, null) > 0;
        } catch (Exception e) {}
        return didSucceed;
    }

    // Deletes a Note from the database. Returns true/false based on whether the delete was successful
    public boolean deleteNote(int noteID) {
        boolean didDelete = false;
        try{
            didDelete = database.delete(DatabaseHelper.NOTES_TABLE,DatabaseHelper.ID + " = " + noteID,null )> 0;
        }catch(Exception e ){}
        return didDelete;
    }

    // Returns the ID of the last Note that was interacted with
    public int getLastNoteID() {
        int lastId;
        try {
            String query = "Select MAX(" + DatabaseHelper.ID + ") from " + DatabaseHelper.NOTES_TABLE;
            Cursor cursor = database.rawQuery(query, null);
            cursor.moveToFirst();
            lastId = cursor.getInt(0);
            cursor.close();
        } catch (Exception e) {
            lastId = -1;
        }
        return lastId;
    }

    // Returns Notes in an Array
    public ArrayList<Note> getNotes() {
        String query = "SELECT * FROM " + DatabaseHelper.NOTES_TABLE;
        return noteSetter(query);
    }

    // Returns Notes in an Organized Array
    public ArrayList<Note> getNotes(String sortField, String sortOrder) {
        String query = "SELECT * FROM " + DatabaseHelper.NOTES_TABLE + " ORDER BY " + sortField + " " + sortOrder;
        return noteSetter(query);
    }

    // Returns a note given its ID.
    public Note getSpecificNote(int noteID) {
        Note note = new Note();
        String query = "SELECT * FROM " + DatabaseHelper.NOTES_TABLE + " WHERE " + DatabaseHelper.ID + " = " + noteID;
        Cursor cursor = database.rawQuery(query,null);
        if(cursor.moveToFirst()){
            noteSet(note, cursor);
            cursor.close();
        }
        return note;
    }

    // Used to shorten code for insertNote and updateNote methods
    private ContentValues notePutter(Note note) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.TITLE, note.getTitle());
        values.put(DatabaseHelper.CONTENT, note.getContent());
        values.put(DatabaseHelper.PRIORITY, note.getPriority());
        values.put(DatabaseHelper.CREATION_TIME, String.valueOf(note.getCreationTime().getTimeInMillis()));
        values.put(DatabaseHelper.DUE_TIME, String.valueOf(note.getDueTime().getTimeInMillis()));
        return values;
    }

    // Used to shorten code for getNotes methods
    private ArrayList<Note> noteSetter(String query) {
        ArrayList<Note> notes = new ArrayList<>();
        try {
            Cursor cursor = database.rawQuery(query, null);
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                Note note = new Note();
                noteSet(note, cursor);
                notes.add(note);
                cursor.moveToNext();
            }
            cursor.close();
        } catch (Exception e) {
            notes = new ArrayList<>();
        }
        return notes;
    }

    // Used to shorten code for noteSetter and getSpecificNote methods
    private void noteSet(Note note, Cursor cursor) {
        note.setNoteID(cursor.getInt(0));
        note.setTitle(cursor.getString(1));
        note.setContent(cursor.getString(2));
        note.setPriority(cursor.getInt(3));
        //
        // Need to figure out how to pull these properly
        //
        Calendar calendarCreation = Calendar.getInstance();
        calendarCreation.setTimeInMillis(Long.valueOf(cursor.getString(4)));
        note.setCreationTime(calendarCreation);
        Calendar calendarDue = Calendar.getInstance();
        calendarDue.setTimeInMillis(Long.valueOf(cursor.getString(5)));
        note.setDueTime(calendarDue);
    }
}
