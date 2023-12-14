package com.example.lab2phonedatabase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PhoneAdapter extends RecyclerView.Adapter<PhoneAdapter.PhoneViewHolder> {

    private List<Phone> phones = new ArrayList<>();
    private OnPhoneClickListener onPhoneClickListener;

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
        notifyDataSetChanged();
    }

    public interface OnPhoneClickListener {
        void onPhoneClick(int position);
    }

    public void setOnPhoneClickListener(OnPhoneClickListener listener) {
        this.onPhoneClickListener = listener;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    @NonNull
    @Override
    public PhoneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item_phone, parent, false);
        return new PhoneViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneViewHolder holder, int position) {
        Phone phone = phones.get(position);
        holder.bind(phone);
        holder.itemView.setOnClickListener(view -> {
            if (onPhoneClickListener != null) {
                onPhoneClickListener.onPhoneClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return phones.size();
    }



    static class PhoneViewHolder extends RecyclerView.ViewHolder {

        private TextView manufacturerModelTextView;
        private TextView androidVersionTextView;
        private TextView websiteTextView;

        public PhoneViewHolder(@NonNull View itemView) {
            super(itemView);
            manufacturerModelTextView = itemView.findViewById(R.id.manufacturerModelTextView);
            androidVersionTextView = itemView.findViewById(R.id.androidVersionTextView);
            websiteTextView = itemView.findViewById(R.id.websiteTextView);
        }

        public void bind(Phone phone) {
            manufacturerModelTextView.setText(phone.getManufacturer() + " " + phone.getModel());
            androidVersionTextView.setText("Android: " + phone.getAndroidVersion());
            websiteTextView.setText("Website: " + phone.getWebsite());
        }
    }
}

