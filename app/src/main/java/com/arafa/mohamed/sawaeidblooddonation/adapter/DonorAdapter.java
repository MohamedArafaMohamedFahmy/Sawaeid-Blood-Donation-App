package com.arafa.mohamed.sawaeidblooddonation.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.arafa.mohamed.sawaeidblooddonation.R;
import com.arafa.mohamed.sawaeidblooddonation.activities.DonorDetailsActivity;
import com.arafa.mohamed.sawaeidblooddonation.activities.LoginActivity;
import com.arafa.mohamed.sawaeidblooddonation.activities.MainActivity;
import com.arafa.mohamed.sawaeidblooddonation.models.DonorDataModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

public class DonorAdapter extends RecyclerView.Adapter<DonorAdapter.MyViewHolder> {
    Context context;
    ArrayList<DonorDataModel> downloadData;
    AppCompatButton btYes,btNo;
    DatabaseReference databaseReference;
    String bloodType;


    public DonorAdapter(Context context, ArrayList<DonorDataModel> downloadData, String bloodType) {
        this.context = context;
        this.downloadData = downloadData;
        this.bloodType = bloodType;
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
        holder.btPhone.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+downloadData.get(position).getPhoneNumber()));
                context.startActivity(intent);
        });

        holder.itemView.setOnClickListener(v -> {
          Intent intentDetails = new Intent(context, DonorDetailsActivity.class);
          intentDetails.putExtra("details",downloadData.get(position));
          context.startActivity(intentDetails);
        });

        holder.itemView.setOnLongClickListener(v -> {
            showCustomDialog(position);
            return false;
        });

    }

    @Override
    public int getItemCount() {
        return downloadData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView tvNameDonor,tvPhoneNumberDonor,tvCity,tvLastDonation;
        AppCompatImageButton btPhone;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNameDonor = itemView.findViewById(R.id.text_name_donor);
            tvPhoneNumberDonor = itemView.findViewById(R.id.text_phone_number_donor);
            tvCity = itemView.findViewById(R.id.text_city);
            tvLastDonation = itemView.findViewById(R.id.text_last_donation);
            btPhone = itemView.findViewById(R.id.button_phone);


        }
    }

    public  void showCustomDialog(int position){

        databaseReference = FirebaseDatabase.getInstance().getReference();

        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_dialog_delete);

        btYes = dialog.findViewById(R.id.button_yes);
        btNo = dialog.findViewById(R.id.button_no);

        btYes.setOnClickListener(v -> {
            databaseReference.child("BloodDonors").child(bloodType).child(downloadData.get(position).getId()).removeValue().addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    Toast.makeText(context, "Delete Successfully", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, ""+ Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            dialog.dismiss();

        });

        btNo.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }
}
