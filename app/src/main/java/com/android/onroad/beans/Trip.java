package com.android.onroad.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Trip  implements Parcelable {

    String tripName;
    private int tripId;
    private String startPoint , Year,Month,Day,Hour,Minute;
    private String endPoint;



    private  double latStartPoint , langStartPoint ,latEndPoint , langEndPoint;
    private Date DateTime;

    private String status;
    private ArrayList<Note> notes;

    private String repeat;

    public String getRepeat() {
        return repeat;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }

    public double getLatStartPoint() {
        return latStartPoint;
    }

    public void setLatStartPoint(double latStartPoint) {
        this.latStartPoint = latStartPoint;
    }

    public double getLangStartPoint() {
        return langStartPoint;
    }

    public void setLangStartPoint(double langStartPoint) {
        this.langStartPoint = langStartPoint;
    }

    public double getLatEndPoint() {
        return latEndPoint;
    }

    public void setLatEndPoint(double latEndPoint) {
        this.latEndPoint = latEndPoint;
    }

    public double getLangEndPoint() {
        return langEndPoint;
    }

    public void setLangEndPoint(double langEndPoint) {
        this.langEndPoint = langEndPoint;
    }



    public Trip() {

    }

    public Trip(int tripId, String startPoint, String endPoint, Date startDateTime
                , String status, ArrayList<Note> notes) {
        this.tripId = tripId;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.DateTime = startDateTime;

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
    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getMonth() {
        return Month;
    }

    public void setMonth(String month) {
        Month = month;
    }

    public String getDay() {
        return Day;
    }

    public void setDay(String day) {
        Day = day;
    }

    public String getHour() {
        return Hour;
    }

    public void setHour(String hour) {
        Hour = hour;
    }

    public String getMinute() {
        return Minute;
    }

    public void setMinute(String minute) {
        Minute = minute;
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

    public Date getDateTime() {
        return DateTime;
    }

    public void setDateTime(Date startDateTime) {
        this.DateTime = startDateTime;
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

    public void setNotes(ArrayList<Note> notes) {
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
