package com.example.lab2phonedatabase;

import static com.example.lab2phonedatabase.R.string.website_field_is_empty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewTreeViewModelKt;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InputPhone extends AppCompatActivity {

    private EditText etPhoneName;
    private EditText etModelPhone;
    private EditText etAndroidVersion;
    private EditText etWebSite;

    private PhoneViewModel phoneViewModel;
    private boolean isEditing = false;
    private Phone phoneToEdit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_phone);

        etModelPhone = findViewById(R.id.etModelPhone);
        etPhoneName = findViewById(R.id.etPhoneName);
        etAndroidVersion = findViewById(R.id.etAndroidVersion);
        etWebSite = findViewById(R.id.etWebSite);


        phoneViewModel = new ViewModelProvider(this).get(PhoneViewModel.class);



        Intent intent = getIntent();
        if(intent != null && intent.hasExtra("EDIT_PHONE")) {
            isEditing = true;
            phoneToEdit = (Phone) intent.getSerializableExtra("EDIT_PHONE");
            fillFieldsForEditing(phoneToEdit);
        }

        setupButtons();
    }

    private void setupButtons() {

        Button cancelButton = findViewById(R.id.btCancel);
        Button saveButton = findViewById(R.id.btSave);
        Button websiteButton = findViewById(R.id.btWebSite);

        cancelButton.setOnClickListener(v -> onCancelButtonClick());
        saveButton.setOnClickListener(v -> savePhoneDetails());

        websiteButton.setOnClickListener(v -> openWebsite());
    }

    private void fillFieldsForEditing(Phone phone) {
        etPhoneName.setText(phone.getManufacturer());
        etModelPhone.setText(phone.getModel());
        etAndroidVersion.setText(phone.getAndroidVersion());
        etWebSite.setText(phone.getWebsite());
    }

    private void savePhoneDetails() {
        if (validateFields()) {
            String phoneName = etPhoneName.getText().toString().trim();
            String model = etModelPhone.getText().toString().trim();
            String androidVersion = etAndroidVersion.getText().toString().trim();
            String website = etWebSite.getText().toString().trim();

            if (isEditing && phoneToEdit != null) {
                phoneToEdit.setManufacturer(phoneName);
                phoneToEdit.setModel(model);
                phoneToEdit.setAndroidVersion(androidVersion);
                phoneToEdit.setWebsite(website);

                phoneViewModel.updatePhone(phoneToEdit);
            } else {
                Phone newPhone = new Phone(phoneName, model, androidVersion, website);
                phoneViewModel.insertPhone(newPhone);
            }

            finish();
        }
    }

    private boolean validateFields() {
        boolean isValid = true;

        EditText[] fields = {etPhoneName, etModelPhone, etAndroidVersion, etWebSite};
        int[] errorMessages = {R.string.phone_name_empty, R.string.phone_model_empty, R.string.phone_version_empty, R.string.phone_website_empty};

        for (int i = 0; i < fields.length; i++) {
            String value = fields[i].getText().toString().trim();

            if (TextUtils.isEmpty(value)) {
                fields[i].setError(getString(errorMessages[i]));
                isValid = false;
            } else {
                fields[i].setError(null);
            }
        }

        return isValid;
    }

    private void openWebsite() {
        String url = etWebSite.getText().toString().trim();
        if (!url.isEmpty()) {
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "http://" + url;
            }
            if (!url.matches(".*\\.[^.]{2,}$")) {
                url += ".com"; // Dodanie domyślnego rozszerzenia, jeśli nie jest podane
            }
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        }else{
            Toast.makeText(this, website_field_is_empty, Toast.LENGTH_SHORT).show();
        }
    }

    private void onCancelButtonClick() {
        finish();
    }
}
