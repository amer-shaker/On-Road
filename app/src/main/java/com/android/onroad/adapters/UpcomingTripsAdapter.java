package com.android.onroad.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.onroad.R;
import com.android.onroad.activities.AddTripActivity;
import com.android.onroad.activities.TripDetailsActivity;
import com.android.onroad.beans.Trip;
import com.android.onroad.fragments.UpcomingTripsFragment;
import com.android.onroad.utils.Constants;
import com.android.onroad.utils.Utility;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpcomingTripsAdapter extends RecyclerView.Adapter<UpcomingTripsAdapter.ViewHolder> {

    private Context mContext;
    private List<Trip> trips;
    private UpcomingTripsFragment upcomingTripsFragment;

    public UpcomingTripsAdapter(Context mContext, List<Trip> trips, UpcomingTripsFragment upcomingTripsFragment) {
        this.mContext = mContext;
        this.trips = trips;
        this.upcomingTripsFragment = upcomingTripsFragment;
    }

    public void updateList(List<Trip> trips) {
        this.trips = trips;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trip, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Trip trip = trips.get(position);

        if (trip != null) {
            holder.tripNameTextView.setText(trip.getName());
            holder.tripStartPointTextView.setText(trip.getStartPoint());
            holder.tripEndPointTextView.setText(trip.getEndPoint());

//            Glide
//                    .with(mContext)
//                    .load(Const.IMAGE_URL + males.getMales_image_path())
//                    .placeholder(R.drawable.mealplaceholder)
//                    .override(80, 80)
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .centerCrop()
//                    .bitmapTransform(new CropCircleTransformation(mContext))
//                    .into(holder.maleImage);
        }
    }

    @Override
    public int getItemCount() {
        return trips.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.trip_name_text_view)
        TextView tripNameTextView;

        @BindView(R.id.trip_start_point_text_view)
        TextView tripStartPointTextView;

        @BindView(R.id.trip_end_point_text_view)
        TextView tripEndPointTextView;

        @BindView(R.id.edit_image_view)
        ImageView editTripImageView;

        @BindView(R.id.delete_trip_image_view)
        ImageView deleteTripImageView;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @OnClick(R.id.delete_trip_image_view)
        void deleteTrip(View view) {
            upcomingTripsFragment.onDeleteTrip(trips.get(getAdapterPosition()));
            Utility.cancelAlarm(mContext,trips.get(getAdapterPosition()).getAlarmId());
            trips.remove(trips.get(getAdapterPosition()));
            updateList(trips);
        }

        @OnClick(R.id.edit_image_view)
        void editTrip(View view) {
            Intent intent = new Intent(mContext, AddTripActivity.class);
            intent.putExtra("trip", trips.get(getAdapterPosition()));
            mContext.startActivity(intent);
        }

//        @OnClick(R.id.btn_start_trip)
//        void startTrip(View view) {
//            Log.e("position clicked", getAdapterPosition() + "");
//            Utility.launchMap(context, trips.get(getAdapterPosition()));
//
//            Intent intentDialog = new Intent(context, DilogActivity.class);
//            intentDialog.putExtra("trip", trips.get(getAdapterPosition()));
//            context.startActivity(intentDialog);
//        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, TripDetailsActivity.class);
            intent.putExtra(Constants.TRIP_EXTRA, trips.get(getAdapterPosition()));

            mContext.startActivity(intent);
        }
    }
}