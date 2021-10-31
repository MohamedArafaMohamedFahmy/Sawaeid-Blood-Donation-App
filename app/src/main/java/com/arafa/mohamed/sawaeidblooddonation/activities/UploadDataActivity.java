package com.arafa.mohamed.sawaeidblooddonation.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.arafa.mohamed.sawaeidblooddonation.R;
import com.arafa.mohamed.sawaeidblooddonation.models.DonorDataModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UploadDataActivity extends AppCompatActivity {
    AppCompatButton btParse;
    RequestQueue mQueue;
    DatabaseReference databaseReference;
    AppCompatEditText etNameSheet, etUrlSheet;
    Toolbar toolbar;
    String name, city, phoneNumber, lastDonation, bloodType, notes, indexType, url, nameFile,id;
    Spinner spinnerType;
    List<DonorDataModel> sendJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_data);
        toolbar = findViewById(R.id.toolbar_upload_data);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btParse = findViewById(R.id.button_upload);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        etNameSheet = findViewById(R.id.edit_text_name_sheet);
        etUrlSheet = findViewById(R.id.edit_text_url_sheet);
        spinnerType = findViewById(R.id.list_blood_type);

        ArrayAdapter<CharSequence> listBloodType = ArrayAdapter.createFromResource(UploadDataActivity.this, R.array.blood_type,R.layout.my_spinner);
        listBloodType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(listBloodType);
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                indexType = spinnerType.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        mQueue = Volley.newRequestQueue(this);
        sendJson = new ArrayList<>();
        btParse.setOnClickListener(v -> jsonParse());

    }

    private void jsonParse() {
        url = Objects.requireNonNull(etUrlSheet.getText()).toString();
        nameFile = Objects.requireNonNull(etNameSheet.getText()).toString();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONArray jsonArray = response.getJSONArray(nameFile);


                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    name = jsonObject.getString("name");
                    city = jsonObject.getString("city");
                    phoneNumber = jsonObject.getString("phone number");
                    lastDonation = jsonObject.getString("last donation");
                    bloodType = jsonObject.getString("blood type");
                    notes = jsonObject.getString("notes");
                    id = databaseReference.push().getKey();
                    DonorDataModel sd = new DonorDataModel(name, city, phoneNumber, lastDonation, bloodType,notes,id);
                    sendJson.add(sd);
                    databaseReference.child("BloodDonors").child(indexType).child(id).setValue(sd).addOnCompleteListener(task -> {
                        if (task.isSuccessful()){
                            Toast.makeText(UploadDataActivity.this, "Successfully", Toast.LENGTH_SHORT).show();
                        }
                    });

                }


            } catch (JSONException e) {
                Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }, error -> Toast.makeText(this, ""+error.getMessage(), Toast.LENGTH_SHORT).show());

        mQueue.add(request);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == android.R.id.home) {
                finish();
        }
        return true;
    }
}