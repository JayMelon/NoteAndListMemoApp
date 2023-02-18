package com.example.noteandlistmemoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

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

    private ContentValues noteChanger(Note note) {
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.TITLE, note.getTitle());
        values.put(DatabaseHelper.CONTENT, note.getContent());
        values.put(DatabaseHelper.IMPORTANCE, note.getImportance());
        values.put(DatabaseHelper.CREATION_TIME, String.valueOf(note.getCreationTime().getTimeInMillis()));
        values.put(DatabaseHelper.DUE_TIME, String.valueOf(note.getDueTime().getTimeInMillis()));

        return values;
    }

    public boolean insertNote(Note note) {
        boolean didSucceed = false;
        try {
            didSucceed = database.insert(DatabaseHelper.NOTES_TABLE, null, noteChanger(note)) > 0;
        } catch (Exception e) {

        }
        return didSucceed;
    }

    public boolean updateNote(Note note) {
        boolean didSucceed = false;
        try {
            String whereClause  = DatabaseHelper.ID + " = ";
            Long rowID = Long.valueOf(note.getNoteID());
            didSucceed = database.update(DatabaseHelper.NOTES_TABLE, noteChanger(note), whereClause + rowID, null) > 0;
        } catch (Exception e) {

        }
        return didSucceed;
    }

    public boolean deleteNote(int noteID) {
        boolean didDelete = false;
        try{
            didDelete = database.delete(DatabaseHelper.NOTES_TABLE,DatabaseHelper.ID + " = " + noteID,null )> 0;
        }catch(Exception e ){

        }
        return didDelete;
    }

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

    public ArrayList<Note> getNotes() {

    }
}
