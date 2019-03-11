package com.android.onroad.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class Trip  implements Parcelable {

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

    protected Trip(Parcel in) {
        tripName = in.readString();
        tripId = in.readInt();
        startPoint = in.readString();
        endPoint = in.readString();
        status = in.readString();
    }

    public static final Creator<Trip> CREATOR = new Creator<Trip>() {
        @Override
        public Trip createFromParcel(Parcel in) {
            return new Trip(in);
        }

        @Override
        public Trip[] newArray(int size) {
            return new Trip[size];
        }
    };

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tripName);
        dest.writeInt(tripId);
        dest.writeString(startPoint);
        dest.writeString(endPoint);
        dest.writeString(status);
    }
}
