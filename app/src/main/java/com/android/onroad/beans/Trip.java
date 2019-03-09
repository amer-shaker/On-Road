package com.android.onroad.beans;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class Trip {

    String tripName;
    private int tripId;
    private String startPoint;
    private String endPoint;
    private Date startDateTime;
    private Date endDateTime;
    private String status;
    private List<Note> notes;

    public Trip() {

    }

    public Trip(int tripId, String startPoint, String endPoint, Date startDateTime,
                Date endDateTime, String status, List<Note> notes) {
        this.tripId = tripId;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.status = status;
        this.notes = notes;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
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

    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Date getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }


}
