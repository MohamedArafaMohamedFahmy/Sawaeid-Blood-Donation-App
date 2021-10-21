package com.arafa.mohamed.sawaeidblooddonation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import com.arafa.mohamed.sawaeidblooddonation.R;
import com.arafa.mohamed.sawaeidblooddonation.models.DonorDataModel;

import java.util.ArrayList;

public class DonorAdapter extends RecyclerView.Adapter<DonorAdapter.MyViewHolder> {
    Context context;
    ArrayList<DonorDataModel> downloadData;


    public DonorAdapter(Context context, ArrayList<DonorDataModel> downloadData) {
        this.context = context;
        this.downloadData = downloadData;
    }

    @NonNull
    @Override
    public DonorAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View v = layoutInflater.inflate(R.layout.recycler_item_donation,parent,false);
        return new DonorAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull  DonorAdapter.MyViewHolder holder, int position) {

        holder.tvNameDonor.setText(downloadData.get(position).getName());
        holder.tvPhoneNumberDonor.setText(context.getResources().getString(R.string.zero,downloadData.get(position).getPhoneNumber()));
        holder.tvCity.setText(downloadData.get(position).getCity());
        holder.tvLastDonation.setText(downloadData.get(position).getLastDonation());

    }

    @Override
    public int getItemCount() {
        return downloadData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView tvNameDonor,tvPhoneNumberDonor,tvCity,tvLastDonation;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNameDonor = itemView.findViewById(R.id.text_name_donor);
            tvPhoneNumberDonor = itemView.findViewById(R.id.text_phone_number_donor);
            tvCity = itemView.findViewById(R.id.text_city);
            tvLastDonation = itemView.findViewById(R.id.text_last_donation);


        }
    }
}
