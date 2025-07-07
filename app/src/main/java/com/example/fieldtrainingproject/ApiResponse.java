package com.example.fieldtrainingproject;

import com.google.gson.JsonElement;

import java.util.List;

import kotlinx.coroutines.Job;

public class ApiResponse {
    public boolean success;
    public JsonElement message;
    public List<job> data;
}
