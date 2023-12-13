package com.example.lab2phonedatabase;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executors;

public class PhoneRepository {

    private PhoneDao phoneDao;
    private LiveData<List<Phone>> allPhones;

    public PhoneRepository(Application application) {
        PhoneDatabase db = PhoneDatabase.getDatabase(application);
        phoneDao = db.phoneDao();
        allPhones = phoneDao.getAllPhones();
    }

    public LiveData<List<Phone>> getAllPhones() {
        return allPhones;
    }

    public void insertPhone(Phone phone) {
        Executors.newSingleThreadExecutor().execute(() -> {
            phoneDao.insertPhone(phone);
        });
    }
    public void deletePhone(Phone phone){
        Executors.newSingleThreadExecutor().execute(() -> {
            phoneDao.deletePhone(phone);
        });
    }
    public void deleteAllPhones(List<Phone> phones) {
        new Thread(() -> {
            for (Phone phone : phones) {
                phoneDao.deletePhone(phone);
            }
        }).start();
    }



}