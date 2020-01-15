package com.vinsol.scheduler.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SchedulerModel {
    public SchedulerModel(String description, String startTime, String endTime, List<String> participants) {
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.participants = participants;
    }

    @SerializedName("start_time")
    public String startTime;

    @SerializedName("end_time")
    public String endTime;

    @SerializedName("description")
    public String description;

    @SerializedName("participants")
    public List<String> participants;

    @Override
    public String toString() {
        return startTime +" "+endTime+" "+description;
    }
}
