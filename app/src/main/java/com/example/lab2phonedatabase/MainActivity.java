package com.example.lab2phonedatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;



public class MainActivity extends AppCompatActivity implements PhoneAdapter.OnPhoneClickListener {

    private PhoneDao phoneDao;

    private PhoneRepository phoneRepository;
    private RecyclerView recyclerView;
    private PhoneAdapter adapter;

    private PhoneViewModel phoneViewModel;

    private static final int EDIT_PHONE_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        phoneViewModel = new ViewModelProvider(this).get(PhoneViewModel.class);

        phoneRepository = new PhoneRepository(getApplication());

        initViews();

        adapter.setOnPhoneClickListener(this);

        setupToolbar();



        loadPhonesFromViewModel();

        setupFloatingActionButton();

        phoneRepository.addSamplePhones();


        setupSwipeToDelete();

    }



    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.tb_menu);
        setSupportActionBar(toolbar);
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PhoneAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void loadPhonesFromViewModel() {
        phoneViewModel.getAllPhones().observe(this, phones -> {
            if (phones != null) {
                adapter.setPhones(phones);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void setupSwipeToDelete() {
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
                phoneViewModel.deletePhone(phoneToDelete);
            }
        };

        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
    }

    private void setupFloatingActionButton() {
        FloatingActionButton fab = findViewById(R.id.fabMain);
        fab.setOnClickListener(view -> startInputPhoneActivity());
    }

    private void startInputPhoneActivity() {
        Intent intent = new Intent(MainActivity.this, InputPhone.class);
        startActivity(intent);
    }

    @Override
    public void onPhoneClick(int position) {
        // Obsługa kliknięcia na element
        Phone clickedPhone = adapter.getPhones().get(position);
        String message = "Clicked: " + clickedPhone.getManufacturer() + " " + clickedPhone.getModel();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(MainActivity.this, InputPhone.class);
        intent.putExtra("EDIT_PHONE", clickedPhone);
        startActivityForResult(intent, EDIT_PHONE_REQUEST_CODE);

    }


    private void deleteAllPhonesFromViewModel() {
        phoneViewModel.deleteAllPhones();
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
            deleteAllPhonesFromViewModel();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }




}







