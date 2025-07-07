package com.example.fieldtrainingproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.FaqViewHolder> {

    private Context context;
    private List<Faq> faqList;
    private OnFaqClickListener listener;

    public interface OnFaqClickListener {
        void onFaqClick(Faq faq);
    }

    public FaqAdapter(Context context, List<Faq> faqList, OnFaqClickListener listener) {
        this.context = context;
        this.faqList = faqList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FaqViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_faq, parent, false);
        return new FaqViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FaqViewHolder holder, int position) {
        Faq faq = faqList.get(position);
        holder.questionText.setText(faq.question);
        holder.itemView.setOnClickListener(v -> listener.onFaqClick(faq));
    }

    @Override
    public int getItemCount() {
        return faqList.size();
    }

    public static class FaqViewHolder extends RecyclerView.ViewHolder {
        TextView questionText;

        public FaqViewHolder(@NonNull View itemView) {
            super(itemView);
            questionText = itemView.findViewById(R.id.text_question);
        }
    }
}
