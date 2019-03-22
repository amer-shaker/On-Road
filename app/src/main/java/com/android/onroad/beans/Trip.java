package com.android.onroad.beans;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.Date;

@IgnoreExtraProperties
public class Trip implements Parcelable {

    public static final String UPCOMING_TRIP = "Upcoming",PAST_TRIP = "Past",CANCELED_TRIP = "Canceled";

    private String tripId,name,type,status,startPoint,endPoint,time,tripDate;
    private double startPointLatitude,startPointLongitude,endPointLatitude,endPointLongitude;
    private Date date;
    private int alarmId;
    private ArrayList<Note> notes;

    public Trip() {

    }

    public String getTripDate() {
        return tripDate;
    }

    public void setTripDate(String tripDate) {
        this.tripDate = tripDate;
    }

    public Trip(String tripId, String name, String type, String status, String startPoint, String endPoint,
                double startPointLatitude, double startPointLongitude, double endPointLatitude, String time,
                double endPointLongitude, Date date, int alarmId,String tripDate, ArrayList<Note> notes) {
        this.tripId = tripId;
        this.name = name;
        this.type = type;
        this.status = status;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.time = time;
        this.startPointLatitude = startPointLatitude;
        this.startPointLongitude = startPointLongitude;
        this.endPointLatitude = endPointLatitude;
        this.endPointLongitude = endPointLongitude;
        this.date = date;
        this.alarmId = alarmId;
        this.tripDate=tripDate;
        this.notes = notes;
    }

    protected Trip(Parcel in) {
        tripId = in.readString();
        name = in.readString();
        type = in.readString();
        status = in.readString();
        startPoint = in.readString();
        endPoint = in.readString();
        time = in.readString();
        startPointLatitude = in.readDouble();
        startPointLongitude = in.readDouble();
        endPointLatitude = in.readDouble();
        endPointLongitude = in.readDouble();
        alarmId = in.readInt();
        tripDate=in.readString();
        notes = in.createTypedArrayList(Note.CREATOR);
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

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getStartPointLatitude() {
        return startPointLatitude;
    }

    public void setStartPointLatitude(double startPointLatitude) {
        this.startPointLatitude = startPointLatitude;
    }

    public double getStartPointLongitude() {
        return startPointLongitude;
    }

    public void setStartPointLongitude(double startPointLongitude) {
        this.startPointLongitude = startPointLongitude;
    }

    public double getEndPointLatitude() {
        return endPointLatitude;
    }

    public void setEndPointLatitude(double endPointLatitude) {
        this.endPointLatitude = endPointLatitude;
    }

    public double getEndPointLongitude() {
        return endPointLongitude;
    }

    public void setEndPointLongitude(double endPointLongitude) {
        this.endPointLongitude = endPointLongitude;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(int alarmId) {
        this.alarmId = alarmId;
    }

    public ArrayList<Note> getNotes() {
        return notes;
    }

    public void setNotes(ArrayList<Note> notes) {
        this.notes = notes;
    }

    public String getStartCoordinates() {
        return startPointLatitude + "," + startPointLongitude;
    }

    public String getDestinationCoordinates() {
        return endPointLatitude + "," + endPointLongitude;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tripId);
        dest.writeString(name);
        dest.writeString(type);
        dest.writeString(status);
        dest.writeString(startPoint);
        dest.writeString(endPoint);
        dest.writeString(time);
        dest.writeDouble(startPointLatitude);
        dest.writeDouble(startPointLongitude);
        dest.writeDouble(endPointLatitude);
        dest.writeDouble(endPointLongitude);
        dest.writeInt(alarmId);
        dest.writeString(tripDate);

        dest.writeTypedList(notes);
    }
}