package com.android.onroad.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.onroad.R;
import com.android.onroad.activities.DetailsTripActivity;
import com.android.onroad.beans.Trip;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryTripsAdapter  extends RecyclerView.Adapter<HistoryTripsAdapter.ViewHolder> {
    private Context context;
    private List<Trip> trips;

    public HistoryTripsAdapter(Context context) {
        trips = new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_trips_recycler, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        Trip trip = trips.get(position);
        Log.e("trips size onBindViewH", trips.size() + "");
        holder.tvTripName.setText(trip.getTripName());
        holder.tvStartPoint.setText(trip.getStartPoint());
        holder.tvEndPoint.setText(trip.getEndPoint());
    }

    @Override
    public int getItemCount() {
        Log.e("trips getItemCount", trips.size() + "");

        return trips.size();

    }

    public void setItems(List<Trip> items) {
        this.trips = items;
        Log.e("trips size", items.size() + "");
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {




        @BindView(R.id.tv_trip_name_history)
        TextView tvTripName;

        @BindView(R.id.tv_start_point_history)
        TextView tvStartPoint;
        @BindView(R.id.tv_end_point_history)
        TextView tvEndPoint;



        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

       @Override
        public void onClick(View v) {
            Intent intentdetails=new Intent(context, DetailsTripActivity.class);
            intentdetails.putExtra("trip",trips.get(getAdapterPosition()));

            context.startActivity(intentdetails);

        }
    }
}
