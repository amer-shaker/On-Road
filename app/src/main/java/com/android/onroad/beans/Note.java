package com.android.onroad.beans;

import android.os.Parcel;
import android.os.Parcelable;

public class Note implements Parcelable {

    private int noteId;
    private String note;

    public Note() {

    }

    public Note(int noteId, String note) {
        this.noteId = noteId;
        this.note = note;
    }

    protected Note(Parcel in) {
        noteId = in.readInt();
        note = in.readString();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(noteId);
        dest.writeString(note);
    }

}