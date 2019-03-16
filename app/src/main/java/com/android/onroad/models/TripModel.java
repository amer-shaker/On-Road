package com.android.onroad.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.android.onroad.beans.Note;
import com.google.firebase.database.IgnoreExtraProperties;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

@IgnoreExtraProperties
public class TripModel {

    private int tripId;
    private String name;
    private String type;
    private String status;
    private String startPoint;
    private String endPoint;
    private double startPointLatitude;
    private double startPointLongitude;
    private double endPointLatitude;
    private double endPointLongitude;
    private Date date;
    private String time;
    private ArrayList<Note> notes;

    public TripModel() {

    }

    public TripModel(int tripId, String name, String type, String status, String startPoint, String endPoint,
                     double startPointLatitude, double startPointLongitude, double endPointLatitude,
                     double endPointLongitude, Date date, String time, ArrayList<Note> notes) {
        this.tripId = tripId;
        this.name = name;
        this.type = type;
        this.status = status;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.startPointLatitude = startPointLatitude;
        this.startPointLongitude = startPointLongitude;
        this.endPointLatitude = endPointLatitude;
        this.endPointLongitude = endPointLongitude;
        this.date = date;
        this.time = time;
        this.notes = notes;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ArrayList<Note> getNotes() {
        return notes;
    }

    public void setNotes(ArrayList<Note> notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "TripModel{" +
                "tripId=" + tripId +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                ", startPoint='" + startPoint + '\'' +
                ", endPoint='" + endPoint + '\'' +
                ", startPointLatitude=" + startPointLatitude +
                ", startPointLongitude=" + startPointLongitude +
                ", endPointLatitude=" + endPointLatitude +
                ", endPointLongitude=" + endPointLongitude +
                ", date=" + date +
                ", time=" + time +
                ", notes=" + notes +
                '}';
    }

//    public static Creator<TripModel> getCREATOR() {
//        return CREATOR;
//    }
//
//    protected TripModel(Parcel in) {
//        name = in.readString();
//        tripId = in.readInt();
//        startPoint = in.readString();
//        endPoint = in.readString();
//        status = in.readString();
//    }
//
//    public static final Creator<TripModel> CREATOR = new Creator<TripModel>() {
//        @Override
//        public TripModel createFromParcel(Parcel in) {
//            return new TripModel(in);
//        }
//
//        @Override
//        public TripModel[] newArray(int size) {
//            return new TripModel[size];
//        }
//    };
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeInt(tripId);
//        dest.writeString(name);
//        dest.writeString(startPoint);
//        dest.writeString(endPoint);
//        dest.writeString(status);
//    }
}
