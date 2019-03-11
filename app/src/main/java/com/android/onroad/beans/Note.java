package com.android.onroad.beans;

public class Note {

    private int noteId;
    private String note;

    public Note() {

    }

    public Note(int noteId, String note) {
        this.noteId = noteId;
        this.note = note;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}