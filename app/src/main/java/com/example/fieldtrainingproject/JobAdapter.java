package com.example.fieldtrainingproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;


public class JobAdapter extends RecyclerView.Adapter<JobAdapter.JobViewHolder> {

    private Context context;
    private List<job> jobList;

    public JobAdapter(Context context, List<job> jobList) {
        this.context = context;
        this.jobList = jobList;
    }

    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_job_item, parent, false);
        return new JobViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position) {
        job jobItem = jobList.get(position);
        holder.textJobTitle.setText(jobItem.title);
        holder.textCompanyName.setText(jobItem.company_name);
        holder.textSalary.setText(jobItem.salary);
        holder.textExperience.setText(jobItem.experience);
        if (jobItem.work_field != null) {
            if (jobItem.work_field.isJsonObject()) {
                JsonObject obj = jobItem.work_field.getAsJsonObject();
                if (obj.has("en")) {
                    holder.textWorkField.setText(obj.get("en").getAsString());
                } else {
                    holder.textWorkField.setText(obj.toString());
                }
            } else if (jobItem.work_field.isJsonPrimitive()) {
                holder.textWorkField.setText(jobItem.work_field.getAsString());
            } else {
                holder.textWorkField.setText("N/A");
            }
        }
        holder.textCreatedAt.setText(jobItem.created_at);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, jobDetailsScreen.class);
            intent.putExtra("job_id", jobItem.id);
            context.startActivity(intent);
        });

        holder.bookmarkIcon.setImageResource(
                jobItem.is_favorite ? R.drawable.ic_bookmark_filled : R.drawable.ic_bookmark_border
        );

        holder.bookmarkIcon.setOnClickListener(v -> {
            ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
            apiService.markFavorite(jobItem.id).enqueue(new Callback<GenericResponse>() {
                @Override
                public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().success) {
                        jobItem.is_favorite = !jobItem.is_favorite;
                        notifyItemChanged(holder.getAdapterPosition());
                        Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Failed to update favorite", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<GenericResponse> call, Throwable t) {
                    Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        holder.cardConstraint.setOnClickListener(v -> {
            Intent intent = new Intent(context, companyProfile.class);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    public static class JobViewHolder extends RecyclerView.ViewHolder {

        TextView textJobTitle, textCompanyName, textSalary, textExperience, textWorkField, textCreatedAt;

        ImageView bookmarkIcon, ic_arrow_back;
        ConstraintLayout cardConstraint;


        public JobViewHolder(@NonNull View itemView) {
            super(itemView);

            textJobTitle = itemView.findViewById(R.id.text_job_title);
            textCompanyName = itemView.findViewById(R.id.text_company_name);
            textSalary = itemView.findViewById(R.id.text_salary);
            textExperience = itemView.findViewById(R.id.text_experience);
            textWorkField = itemView.findViewById(R.id.text_work_field);
            textCreatedAt = itemView.findViewById(R.id.text_time_ago);
            bookmarkIcon = itemView.findViewById(R.id.icon_bookmark);
            cardConstraint = itemView.findViewById(R.id.cardConstraint);
            ic_arrow_back = itemView.findViewById(R.id.icon_back);

        }
    }
}
