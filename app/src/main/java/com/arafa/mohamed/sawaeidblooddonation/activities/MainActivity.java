package com.arafa.mohamed.sawaeidblooddonation.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Toast;
import com.arafa.mohamed.sawaeidblooddonation.R;
import com.arafa.mohamed.sawaeidblooddonation.adapter.BloodTypeAdapter;
import com.arafa.mohamed.sawaeidblooddonation.adapter.DonorAdapter;
import com.arafa.mohamed.sawaeidblooddonation.models.CustomSpinner;
import com.arafa.mohamed.sawaeidblooddonation.models.DataBloodTypeModel;
import com.arafa.mohamed.sawaeidblooddonation.models.DonorDataModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements CustomSpinner.OnSpinnerEventsListener {
    CustomSpinner spinnerBlood;
    BloodTypeAdapter bloodTypeAdapter;
    Toolbar toolbar;
    RecyclerView recListDonor;
    AppCompatButton btYes,btNo;
    DatabaseReference databaseReference;
    String nameSelected;
    ArrayList<DonorDataModel> listDonor;
    DonorDataModel donorDataModel;
    DonorAdapter donorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar=findViewById(R.id.toolbar_blood_donation);
        setSupportActionBar(toolbar);

        spinnerBlood = findViewById(R.id.spinner_blood);
        recListDonor = findViewById(R.id.rec_data_donation);
        spinnerBlood.setSpinnerEventsListener(this);
        bloodTypeAdapter = new BloodTypeAdapter(MainActivity.this, DataBloodTypeModel.getBloodType());
        spinnerBlood.setAdapter(bloodTypeAdapter);
        listDonor = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.CALL_PHONE},1);

        spinnerBlood.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nameSelected =  DataBloodTypeModel.getBloodType().get(position).name;
                databaseReference.child("BloodDonors").child(nameSelected).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull  DataSnapshot snapshot) {
                        listDonor.clear();
                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            donorDataModel = postSnapshot.getValue(DonorDataModel.class);
                            listDonor.add(donorDataModel);
                        }
                        if (!listDonor.isEmpty()){
                            donorAdapter = new DonorAdapter(MainActivity.this,listDonor, nameSelected);
                            donorAdapter.notifyDataSetChanged();
                            recListDonor.setAdapter(donorAdapter);
                            recListDonor.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        }else{
                            donorAdapter.notifyDataSetChanged();
                            Toast.makeText(MainActivity.this, "Not Found Data", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull  DatabaseError error) {
                        Toast.makeText(MainActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }

    @Override
    public void onPopupWindowOpened(AppCompatSpinner spinner) {
        spinnerBlood.setBackground(AppCompatResources.getDrawable(MainActivity.this,R.drawable.bg_spinner_blood_type_up));
    }

    @Override
    public void onPopupWindowClosed(AppCompatSpinner spinner) {
        spinnerBlood.setBackground(AppCompatResources.getDrawable(MainActivity.this,R.drawable.bg_spinner_blood_type));
    }



    public  void showCustomDialog(){

        Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_dialog);

        btYes = dialog.findViewById(R.id.button_yes);
        btNo = dialog.findViewById(R.id.button_no);

        btYes.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            dialog.dismiss();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        });

        btNo.setOnClickListener(v -> dialog.dismiss());
        dialog.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

         if(item.getItemId() == R.id.action_sign_out){
            showCustomDialog();
        }else if(item.getItemId() == R.id.action_add_donor){
             startActivity(new Intent(MainActivity.this,DonorDetailsActivity.class));
         }
        return super.onOptionsItemSelected(item);
    }
}