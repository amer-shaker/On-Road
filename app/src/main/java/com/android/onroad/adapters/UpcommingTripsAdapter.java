package com.android.onroad.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.onroad.R;
import com.android.onroad.activities.DetailsTripActivity;
import com.android.onroad.beans.Trip;
import com.android.onroad.utils.Utility;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class UpcommingTripsAdapter extends RecyclerView.Adapter<UpcommingTripsAdapter.ViewHolder> {

    private Context context;
    private List<Trip> trips;

    public UpcommingTripsAdapter(Context context) {
        trips = new ArrayList<>();
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_upcomming_recycler, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Trip trip = trips.get(position);
        Log.e("trips size onBindViewH", trips.size() + "");
        holder.tvTripName.setText(trip.getName());
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

        @BindView(R.id.imv_cancel_trip)
        ImageView imgCancelTrip;
        @BindView(R.id.imv_edit_trip)
        ImageView imgEditTrip;

        @BindView(R.id.tv_trip_name)
        TextView tvTripName;

        @BindView(R.id.tv_start_point)
        TextView tvStartPoint;
        @BindView(R.id.tv_end_point)
        TextView tvEndPoint;

        @BindView(R.id.btn_start_trip)
        TextView btnStart;


        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @OnClick(R.id.imv_cancel_trip)
        void nacelTrip(View view) {

            Log.e("position clicked", getAdapterPosition() + "");
        }

        @OnClick(R.id.imv_edit_trip)
        void editTrip(View view) {
//            Intent intent=new Intent(context,eddit);
//            intent.putExtra("trip", trips.get(getAdapterPosition()));
//            context.startActivity(intent);
        }

        @OnClick(R.id.btn_start_trip)
        void startTrip(View view) {
            Log.e("position clicked", getAdapterPosition() + "");
            Utility.launchMap(context,trips.get(getAdapterPosition()));

        }


        @Override
        public void onClick(View v) {

            Intent intentdetails = new Intent(context, DetailsTripActivity.class);
            Trip trip = trips.get(getAdapterPosition());
            intentdetails.putExtra("trip", trips.get(getAdapterPosition()));
            Log.i("index", getAdapterPosition() + "");
            Toast.makeText(context, trip.getName(), Toast.LENGTH_SHORT).show();

            Toast.makeText(context, trips.get(getAdapterPosition()).getStartPoint(), Toast.LENGTH_SHORT).show();

            context.startActivity(intentdetails);

        }
    }
}
