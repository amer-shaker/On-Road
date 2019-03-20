package com.android.onroad.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.onroad.R;
import com.android.onroad.activities.DetailsTripActivity;
import com.android.onroad.beans.Trip;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryTripsAdapter extends RecyclerView.Adapter<HistoryTripsAdapter.ViewHolder> {
    private Context context;
    private List<Trip> trips;

    public HistoryTripsAdapter(Context context) {
        trips = new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trip, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Trip trip = trips.get(position);
        Log.e("trips size onBindViewH", trips.size() + "");

        holder.tripControlsLinearLayout.setVisibility(View.GONE);
        holder.tripNameTextView.setText(trip.getName());
        DateFormat formatter = new SimpleDateFormat("dd / MM / yy");

        long now = trip.getTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(now);

        holder.tripDateTextView.setText(formatter.format(calendar.getTime()));
        holder.tripStatusTextView.setText(trip.getStatus());
    }

    @Override
    public int getItemCount() {
        Log.e("trips getItemCount", trips.size() + "");

        return trips.size();

    }

    public void updateList(List<Trip> trips) {
        this.trips = trips;
        this.notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.trip_controls_linear_layout)
        LinearLayout tripControlsLinearLayout;

        @BindView(R.id.trip_name_text_view)
        TextView tripNameTextView;

        @BindView(R.id.trip_date_text_view)
        TextView tripDateTextView;

        @BindView(R.id.trip_status_text_view)
        TextView tripStatusTextView;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intentdetails = new Intent(context, DetailsTripActivity.class);
//          //  intentdetails.putExtra("trip",trips.get(getAdapterPosition()));
            context.startActivity(intentdetails);
        }
    }
}
