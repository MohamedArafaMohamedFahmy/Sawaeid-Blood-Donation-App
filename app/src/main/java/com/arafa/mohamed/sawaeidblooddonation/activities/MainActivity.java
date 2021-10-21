package com.arafa.mohamed.sawaeidblooddonation.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.arafa.mohamed.sawaeidblooddonation.R;
import com.arafa.mohamed.sawaeidblooddonation.adapter.BloodTypeAdapter;
import com.arafa.mohamed.sawaeidblooddonation.adapter.DonorAdapter;
import com.arafa.mohamed.sawaeidblooddonation.models.CustomSpinner;
import com.arafa.mohamed.sawaeidblooddonation.models.DataBloodTypeModel;
import com.arafa.mohamed.sawaeidblooddonation.models.DonorDataModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements CustomSpinner.OnSpinnerEventsListener {
    CustomSpinner spinnerBlood;
    BloodTypeAdapter bloodTypeAdapter;
    Toolbar toolbar;
    RecyclerView recListDonor;
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
                            donorAdapter = new DonorAdapter(MainActivity.this,listDonor);
                            recListDonor.setAdapter(donorAdapter);
                            recListDonor.setLayoutManager(new LinearLayoutManager(MainActivity.this));
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
}