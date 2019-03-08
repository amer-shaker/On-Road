package com.android.onroad.beans;

import java.time.LocalDateTime;
import java.util.List;

public class TripModel {

    private int tripId;
    private String startPoint;
    private String endPoint;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String status;
    private List<NoteModel> notes;

    public TripModel() {

    }

    public TripModel(int tripId, String startPoint, String endPoint, LocalDateTime startDateTime,
                     LocalDateTime endDateTime, String status, List<NoteModel> notes) {
        this.tripId = tripId;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.status = status;
        this.notes = notes;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<NoteModel> getNotes() {
        return notes;
    }

    public void setNotes(List<NoteModel> notes) {
        this.notes = notes;
    }
}
