package com.vinsol.scheduler.Adapter;

import android.app.AlertDialog;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vinsol.scheduler.Models.SchedulerModel;
import com.vinsol.scheduler.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {
    private ArrayList<SchedulerModel> schedulerData;

    public ListAdapter(ArrayList<SchedulerModel> scheduler) {
        this.schedulerData = scheduler;
    }

    @Override
    public int getItemCount() {
        if(schedulerData != null){
            return schedulerData.size();
        }
        return 0;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_cell, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final SchedulerModel myListData = schedulerData.get(position);

        try {
            holder.imageView.setText(convertTO12Hour(myListData.startTime));
            holder.endTime.setText(convertTO12Hour(myListData.endTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.textView.setText(myListData.description);
    }

    private String convertTO12Hour (String _24HourTime) throws ParseException {
        SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
        SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
        Date _24HourDt = _24HourSDF.parse(_24HourTime);
        return _12HourSDF.format(_24HourDt);
    }



    protected class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView imageView;
        private TextView textView;
        private TextView endTime;

        private MyViewHolder(final View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.name);
            this.endTime = itemView.findViewById(R.id.endName);
            this.textView = itemView.findViewById(R.id.description);
        }
    }

}

