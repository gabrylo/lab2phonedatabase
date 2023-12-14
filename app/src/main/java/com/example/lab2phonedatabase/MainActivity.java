package com.example.lab2phonedatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.sax.Element;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements PhoneAdapter.OnPhoneClickListener {

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

        adapter.setOnPhoneClickListener(this);

        Toolbar toolbar = findViewById(R.id.tb_menu);
        setSupportActionBar(toolbar);




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

        phoneRepository.addSamplePhones();

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(
                0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Phone phoneToDelete = adapter.getPhones().get(position);

                // Usunięcie telefonu z bazy danych po przeciągnięciu w lewo lub w prawo
                phoneRepository.deletePhone(phoneToDelete);
            }
        };

        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);





    }

    @Override
    public void onPhoneClick(int position) {
        // Obsługa kliknięcia na element
        Phone clickedPhone = adapter.getPhones().get(position);
        String message = "Clicked: " + clickedPhone.getManufacturer() + " " + clickedPhone.getModel();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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
        phoneRepository.deleteAllPhones();
    }




    private void addSamplePhones() {
        Executors.newSingleThreadExecutor().execute(() -> {
            phoneDao.insertPhone(new Phone("Samsung", "Galaxy S21", "Android 12", "www.samsung.com"));
            phoneDao.insertPhone(new Phone("Google", "Pixel 6", "Android 12", "www.store.google.com"));
            // Dodaj inne telefony
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item){
        int id = item.getItemId();
        if(id == R.id.clear_database){
            deleteAllPhonesFromDatabase();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }




}







