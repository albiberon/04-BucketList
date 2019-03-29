package com.example.bucketlist;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

public class BucketAdapter extends RecyclerView.Adapter<BucketAdapter.ViewHolder> {

    List<Bucket> bucketList;

    public BucketAdapter(List<Bucket> bucketList) {
        this.bucketList = bucketList;
    }

    @NonNull
    @Override
    public BucketAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.content_view, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(row);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final BucketAdapter.ViewHolder viewHolder, int i) {
        final Bucket bucket = bucketList.get(i);
        (viewHolder).titleView.setText(bucket.getTitle());
        (viewHolder).descriptionView.setText(bucket.getDescription());

        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    viewHolder.titleView.setPaintFlags(viewHolder.titleView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    viewHolder.descriptionView.setPaintFlags(viewHolder.descriptionView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    viewHolder.titleView.setPaintFlags(viewHolder.titleView.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
                    viewHolder.descriptionView.setPaintFlags(viewHolder.descriptionView.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
                }
            }
        });




    }

    @Override
    public int getItemCount() {
        return bucketList.size();
    }

    public void swapList (List<Bucket> newList) {
        bucketList = newList;
        if (newList != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        TextView titleView;
        TextView descriptionView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkBox);
            titleView = itemView.findViewById(R.id.titleView);
            descriptionView = itemView.findViewById(R.id.descriptionView);


        }
    }
}
