package com.arafa.mohamed.sawaeidblooddonation.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.arafa.mohamed.sawaeidblooddonation.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class ForgotPasswordActivity extends AppCompatActivity {

    TextInputEditText etEmailAddress;
    AppCompatTextView tvMessageReset;
    AppCompatButton btFindAccount;
    FirebaseAuth firebaseauth;
    String emailAddress;
    LinearLayout linearProgressBar;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        toolbar=findViewById(R.id.toolbar_forgot_password);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        firebaseauth= FirebaseAuth.getInstance();
        etEmailAddress=findViewById(R.id.editText_email);
        btFindAccount=findViewById(R.id.button_findAccount);
        tvMessageReset=findViewById(R.id.text_message_reset);
        linearProgressBar = findViewById(R.id.linear_progress_bar);

        findAccount();
    }

    public void findAccount(){
        btFindAccount.setOnClickListener(v -> {
            emailAddress= Objects.requireNonNull(etEmailAddress.getText()).toString();

            if(!emailAddress.isEmpty()){
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                etEmailAddress.setCursorVisible(false);
                closeKeyboard();
                linearProgressBar.setVisibility(View.VISIBLE);

                firebaseauth.sendPasswordResetEmail(emailAddress).addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        linearProgressBar.setVisibility(View.GONE);
                        etEmailAddress.setCursorVisible(true);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        tvMessageReset.setText(getResources().getString(R.string.message_forgot_password,emailAddress));
                    }
                    else{
                        linearProgressBar.setVisibility(View.GONE);
                        etEmailAddress.setCursorVisible(true);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        Toast.makeText(ForgotPasswordActivity.this, ""+ Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
            if (emailAddress.isEmpty()){
                etEmailAddress.setError("Please enter email address");
            }
        });
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
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