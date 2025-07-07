package com.example.fieldtrainingproject;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class faqsScreen extends AppCompatActivity {

    RecyclerView faqRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faqs_screen);

        faqRecyclerView = findViewById(R.id.faqRecyclerView);
        faqRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        getFaqsFromApi();
    }

    private void getFaqsFromApi() {
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        apiService.getFaqs().enqueue(new Callback<FaqResponse>() {
            @Override
            public void onResponse(Call<FaqResponse> call, Response<FaqResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Faq> faqList = response.body().data;
                    FaqAdapter adapter = new FaqAdapter(faqsScreen.this, faqList, faq -> openFaqDetails(faq));
                    faqRecyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(faqsScreen.this, "Failed to load FAQs", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FaqResponse> call, Throwable t) {
                Toast.makeText(faqsScreen.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openFaqDetails(Faq faq) {
        FAQ_Details_Screen fragment = FAQ_Details_Screen.newInstance(faq.question, faq.answer);
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, fragment)
                .addToBackStack(null)
                .commit();
    }
}
