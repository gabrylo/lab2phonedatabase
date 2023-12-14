package com.example.lab2phonedatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewTreeViewModelKt;

import android.content.Intent;
import android.net.Uri;
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
        Button websiteButton = findViewById(R.id.btWebSite);

        etModelPhone = findViewById(R.id.etModelPhone);
        etPhoneName = findViewById(R.id.etPhoneName);
        etAndroidVersion = findViewById(R.id.etAndroidVersion);
        etWebSite = findViewById(R.id.etWebSite);

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

        websiteButton.setOnClickListener(view -> {
            String url = etWebSite.getText().toString().trim();
            if (!url.isEmpty()) {
                if (!url.startsWith("http://") && !url.startsWith("https://")) {
                    url = "http://" + url;
                }
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            } else {
                // Obsługa przypadku, gdy pole EditText jest puste
                // Możesz wyświetlić komunikat użytkownikowi lub podjąć inne działania
            }
        });

        Intent intent = getIntent();
        if(intent != null && intent.hasExtra("EDIT_PHONE")) {
            Phone phoneToEdit = (Phone) intent.getSerializableExtra("EDIT_PHONE");
            etPhoneName.setText(phoneToEdit.getManufacturer());
            etModelPhone.setText(phoneToEdit.getModel());
            etAndroidVersion.setText(phoneToEdit.getAndroidVersion());
            etWebSite.setText(phoneToEdit.getWebsite());

        }




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
        if (validateFields()) {
            String phoneName = etPhoneName.getText().toString().trim();
            String model = etModelPhone.getText().toString().trim();
            String androidVersion = etAndroidVersion.getText().toString().trim();
            String website = etWebSite.getText().toString().trim();

            Intent intent = getIntent();
            if(intent != null && intent.hasExtra("EDIT_PHONE")) {
                Phone phoneToEdit = (Phone) intent.getSerializableExtra("EDIT_PHONE");

                // Aktualizacja istniejącego obiektu Phone z nowymi danymi
                phoneToEdit.setManufacturer(phoneName);
                phoneToEdit.setModel(model);
                phoneToEdit.setAndroidVersion(androidVersion);
                phoneToEdit.setWebsite(website);

                // Aktualizacja danych w bazie za pomocą repozytorium
                PhoneRepository repository = new PhoneRepository(getApplication());
                repository.updatePhone(phoneToEdit);

                // Zakończenie aktywności
                finish();
            }
        }
    }


    private void onCancelButtonClick() {
        finish();
    }
}