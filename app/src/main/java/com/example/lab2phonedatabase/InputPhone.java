package com.example.lab2phonedatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewTreeViewModelKt;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class InputPhone extends AppCompatActivity {

    public EditText etPhoneName;
    public EditText etModelPhone;
    public EditText etAndroidVersion;
    public EditText etWebSite;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_phone);
        Button cancelButton = findViewById(R.id.btCancel);
        Button saveButton = findViewById(R.id.btSave);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCancelButtonClick();
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateFields();
                savePhoneDetails();
            }
        });

        etModelPhone = findViewById(R.id.etModelPhone);
        etPhoneName = findViewById(R.id.etPhoneName);
        etAndroidVersion = findViewById(R.id.etAndroidVersion);
        etWebSite = findViewById(R.id.etWebSite);
    }

    private boolean validateFields() {
        boolean isValid = true;

        EditText[] fields = {etPhoneName, etModelPhone, etAndroidVersion, etWebSite};
        String[] errorMessages = {getString(R.string.phone_name_empty), "Model telefonu nie może być pusty", "Wersja Androida nie może być pusta", "Adres strony internetowej nie może być pusty"};

        for (int i = 0; i < fields.length; i++) {
            String value = fields[i].getText().toString().trim();

            if (TextUtils.isEmpty(value)) {
                fields[i].setError(errorMessages[i]);
                isValid = false;
            } else {
                fields[i].setError(null);
            }
        }

        return isValid;
    }



    private void savePhoneDetails() {

    }

    private void onCancelButtonClick() {
        finish();
    }
}