package com.vinsol.scheduler.Interface;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.vinsol.scheduler.Models.SchedulerModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("/api/schedule?date=7/8/2015")
    Call<ArrayList<SchedulerModel>> listRepos(@Query("date") String date);
}

