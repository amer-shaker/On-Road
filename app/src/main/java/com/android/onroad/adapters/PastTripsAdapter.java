package com.android.onroad.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.onroad.R;
import com.android.onroad.activities.TripDetailsActivity;
import com.android.onroad.beans.Trip;
import com.android.onroad.utils.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PastTripsAdapter extends RecyclerView.Adapter<PastTripsAdapter.TripsViewHolder> {

    private Context mContext;
    private List<Trip> trips;

    public PastTripsAdapter(Context mContext, List<Trip> trips) {
        this.mContext = mContext;
        this.trips = trips;
    }

    public void updateList(List<Trip> trips) {
        this.trips = trips;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TripsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trip, parent, false);
        return new TripsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TripsViewHolder holder, int position) {
        final Trip trip = trips.get(position);

        if (trip != null) {
            holder.tripControlsLinearLayout.setVisibility(View.GONE);
            holder.tripNameTextView.setText(trip.getName());
            holder.tripStartPointTextView.setText(trip.getStartPoint());
            holder.tripEndPointTextView.setText(trip.getEndPoint());
        }
    }

    @Override
    public int getItemCount() {
        return trips.size();
    }

    class TripsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.trip_controls_linear_layout)
        LinearLayout tripControlsLinearLayout;

        @BindView(R.id.trip_name_text_view)
        TextView tripNameTextView;

        @BindView(R.id.trip_start_point_text_view)
        TextView tripStartPointTextView;

        @BindView(R.id.trip_end_point_text_view)
        TextView tripEndPointTextView;


        private TripsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, TripDetailsActivity.class);
            Toast.makeText(mContext, "" + trips.get(getAdapterPosition()).getName(), Toast.LENGTH_SHORT).show();
            intent.putExtra(Constants.TRIP_EXTRA, trips.get(getAdapterPosition()));
            mContext.startActivity(intent);
        }

    }
}