package com.android.onroad.adapters;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.onroad.HomeActivity;
import com.android.onroad.R;
import com.android.onroad.beans.TripModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;


/**
 * Created by abdulrahman on 6/4/2018.
 */

public class TripsAdapter extends RecyclerView.Adapter<TripsAdapter.ViewHolder> {

    private HomeActivity activity;
    private List<TripModel> items;

    public TripsAdapter(HomeActivity activity) {
        items = new ArrayList<>();
        this.activity = activity;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_trip, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TripModel repo = items.get(position);
//        holder.tvRepoDescribtion.setText(repo.getDescription());
//        holder.tvRepoNAme.setText(repo.getFull_name());
//        holder.tvRepoOwner.setText(repo.getName());


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<TripModel> items) {
        this.items = items;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        //        @BindView(R.id.tv_repo_name)
//        TextView tvRepoNAme;
//
//        @BindView(R.id.tv_repo_owner)
//        TextView tvRepoOwner;
//
//        @BindView(R.id.tv_repo_describtion)
//        TextView tvRepoDescribtion;
//        @BindView(R.id.row_constraint)
        ConstraintLayout layout;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
//            itemView.setOnLongClickListener(this);
        }


    }
}
