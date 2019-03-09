package com.android.onroad.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.onroad.R;
import com.android.onroad.beans.Trip;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class TripsAdapter extends RecyclerView.Adapter<TripsAdapter.ViewHolder> {

    private Context context;
    private List<Trip> items;

    public TripsAdapter(Context context) {
        items = new ArrayList<>();
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_trip, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Trip trip = items.get(position);
        Log.e("items size onBindViewH", items.size() + "");
        holder.tvTripName.setText(trip.getTripName());
        holder.tvStartPoint.setText(trip.getStartPoint());
        holder.tvEndPoint.setText(trip.getEndPoint());
    }

    @Override
    public int getItemCount() {
        Log.e("items getItemCount", items.size() + "");

        return items.size();

    }

    public void setItems(List<Trip> items) {
        this.items = items;
        Log.e("items size", items.size() + "");
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

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
        }

        @OnClick(R.id.imv_cancel_trip)
        void nacelTrip(View view) {
            Log.e("position clicked", getAdapterPosition() + "");
        }

        @OnClick(R.id.imv_edit_trip)
        void editTrip(View view) {
            Log.e("position clicked", getAdapterPosition() + "");

        }

        @OnClick(R.id.btn_start_trip)
        void startTrip(View view) {
            Log.e("position clicked", getAdapterPosition() + "");

        }


    }
}
