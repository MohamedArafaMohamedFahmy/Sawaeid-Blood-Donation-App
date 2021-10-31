package com.arafa.mohamed.sawaeidblooddonation.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.Toast;

import com.arafa.mohamed.sawaeidblooddonation.R;
import com.arafa.mohamed.sawaeidblooddonation.adapter.BloodTypeAdapter;
import com.arafa.mohamed.sawaeidblooddonation.models.CustomSpinner;
import com.arafa.mohamed.sawaeidblooddonation.models.DataBloodTypeModel;
import com.arafa.mohamed.sawaeidblooddonation.models.DonorDataModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Objects;

public class DonorDetailsActivity extends AppCompatActivity implements CustomSpinner.OnSpinnerEventsListener,DatePickerDialog.OnDateSetListener{
    CustomSpinner spinnerBlood;
    BloodTypeAdapter bloodTypeAdapter;
    Toolbar toolbar;
    TextInputLayout layoutLastDonation;
    TextInputEditText etNameDonor, etPhoneNumber, etCity, etLastDonation, etNotes;
    AppCompatButton btRegister;
    String nameDonor, phoneNumber, city, lastDonation, bloodType, notes, idDonor,donationDate;
    DonorDataModel donorDataModel, retrieveDonorData;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_details);

        toolbar=findViewById(R.id.toolbar_donor_details);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        etNameDonor = findViewById(R.id.editText_name_donor);
        etPhoneNumber = findViewById(R.id.editText_phone_number);
        etCity = findViewById(R.id.editText_city);
        etLastDonation = findViewById(R.id.editText_last_donation);
        etNotes = findViewById(R.id.editText_notes);
        spinnerBlood = findViewById(R.id.spinner_blood_details);
        layoutLastDonation = findViewById(R.id.last_donation_layout);
        btRegister = findViewById(R.id.button_register);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        final Object objDetailed = getIntent().getSerializableExtra("details");
        retrieveDonorData = (DonorDataModel) objDetailed;

        if (retrieveDonorData != null){
            etNameDonor.setText(retrieveDonorData.getName());
            etPhoneNumber.setText(getResources().getString(R.string.zero,retrieveDonorData.getPhoneNumber()));
            etCity.setText(retrieveDonorData.getCity());
            etLastDonation.setText(retrieveDonorData.getLastDonation());
            etNotes.setText(retrieveDonorData.getNotes());
            btRegister.setText(R.string.update);
        }

        spinnerBlood.setSpinnerEventsListener(this);
        bloodTypeAdapter = new BloodTypeAdapter(DonorDetailsActivity.this, DataBloodTypeModel.getBloodType());
        spinnerBlood.setAdapter(bloodTypeAdapter);
        spinnerBlood.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bloodType =  DataBloodTypeModel.getBloodType().get(position).name;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        layoutLastDonation.setEndIconOnClickListener(v -> {
          showDatePickerDialog();
        });

        btRegister.setOnClickListener(v -> {
            nameDonor = Objects.requireNonNull(etNameDonor.getText()).toString();
            phoneNumber = Objects.requireNonNull(etPhoneNumber.getText()).toString();
            city = Objects.requireNonNull(etCity.getText()).toString();
            lastDonation = Objects.requireNonNull(etLastDonation.getText()).toString();
            notes = Objects.requireNonNull(etNotes.getText()).toString();
            idDonor = databaseReference.push().getKey();
            donorDataModel = new DonorDataModel(nameDonor, city, phoneNumber, lastDonation, bloodType, notes,idDonor);
            if (!nameDonor.isEmpty() && phoneNumber.length() == 10 && !city.isEmpty() && !lastDonation.isEmpty() && !bloodType.isEmpty() ) {
                databaseReference.child("BloodDonors").child(bloodType).child(idDonor).setValue(donorDataModel).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Register Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "" + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            if (nameDonor.isEmpty()){
                etNameDonor.setError("Please enter Name Donor");
            }

            if (phoneNumber.isEmpty()){
                etPhoneNumber.setError("Please enter Phone Number");
            }
            if (phoneNumber.length() < 10){
                etPhoneNumber.setError("Please enter 11 number");
            }
            if (city.isEmpty()){
                etCity.setError("Please enter City");
            }
            if (lastDonation.isEmpty()){
                etLastDonation.setError("Please enter Last Donation");
            }
            
        });
    }

    @Override
    public void onPopupWindowOpened(AppCompatSpinner spinner) {
        spinnerBlood.setBackground(AppCompatResources.getDrawable(DonorDetailsActivity.this,R.drawable.bg_spinner_blood_type_up));
    }

    @Override
    public void onPopupWindowClosed(AppCompatSpinner spinner) {
        spinnerBlood.setBackground(AppCompatResources.getDrawable(DonorDetailsActivity.this,R.drawable.bg_spinner_blood_type));
    }

    public void showDatePickerDialog() {
        DatePickerDialog datePickerDialogBorn = new DatePickerDialog(
                this ,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialogBorn.show();
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        donationDate = dayOfMonth + " - " + (month + 1) + " - " + year;
        etLastDonation.setText(donationDate);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return true;
    }
}