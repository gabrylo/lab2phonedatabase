package com.example.lab2phonedatabase;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private PhoneDao phoneDao;

    private PhoneRepository phoneRepository;
    private RecyclerView recyclerView;
    private PhoneAdapter adapter;
    private Button btClear;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phoneRepository = new PhoneRepository(getApplication());

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PhoneAdapter();
        recyclerView.setAdapter(adapter);

        Button btClear = findViewById(R.id.btClear);
        btClear.setOnClickListener(view -> {
            // Wywołanie metody usuwającej bazę danych
            deleteAllPhonesFromDatabase();
        });

        loadPhonesFromRepository();

        FloatingActionButton fab = findViewById(R.id.fabMain);
        fab.setOnClickListener(view -> {
            // Uruchomienie drugiej aktywności po kliknięciu FAB
            Intent intent = new Intent(MainActivity.this, InputPhone.class);
            startActivity(intent);
        });


    }


    private void loadPhonesFromRepository() {
        phoneRepository.getAllPhones().observe(this, phones -> {
            if (phones != null) {
                adapter.setPhones(phones);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void deleteAllPhonesFromDatabase() {
        List<Phone> phones = adapter.getPhones();
        if (phones != null && phones.size() > 0) {
            for (Phone phone : phones) {
                phoneRepository.deletePhone(phone);
            }
        }
    }



    private void addSamplePhones() {
        Executors.newSingleThreadExecutor().execute(() -> {
            phoneDao.insertPhone(new Phone("Samsung", "Galaxy S21", "Android 12", "www.samsung.com"));
            phoneDao.insertPhone(new Phone("Google", "Pixel 6", "Android 12", "www.store.google.com"));
            // Dodaj inne telefony
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.clear_button) {
            deleteAllPhonesFromDatabase();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }


}







