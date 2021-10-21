package com.arafa.mohamed.sawaeidblooddonation.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

import android.os.Bundle;
import android.widget.SpinnerAdapter;

import com.arafa.mohamed.sawaeidblooddonation.R;
import com.arafa.mohamed.sawaeidblooddonation.adapter.BloodTypeAdapter;
import com.arafa.mohamed.sawaeidblooddonation.models.CustomSpinner;
import com.arafa.mohamed.sawaeidblooddonation.models.DataBloodTypeModel;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements CustomSpinner.OnSpinnerEventsListener {
    CustomSpinner spinnerBlood;
    BloodTypeAdapter bloodTypeAdapter;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar=findViewById(R.id.toolbar_blood_donation);
        setSupportActionBar(toolbar);

        spinnerBlood = findViewById(R.id.spinner_blood);
        spinnerBlood.setSpinnerEventsListener(this);
        bloodTypeAdapter = new BloodTypeAdapter(MainActivity.this, DataBloodTypeModel.getBloodType());
        spinnerBlood.setAdapter(bloodTypeAdapter);

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