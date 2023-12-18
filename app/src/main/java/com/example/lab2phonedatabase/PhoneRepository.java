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
    public void deletePhone(Phone phone) {
        Executors.newSingleThreadExecutor().execute(() -> {
            phoneDao.deletePhone(phone);
        });
    }
    public void deleteAllPhones() {
        new Thread(() -> {
            phoneDao.deleteAllPhones();
        }).start();
    }

    public void addSamplePhones() {
        Executors.newSingleThreadExecutor().execute(() -> {
            phoneDao.insertPhone(new Phone("Samsung", "Galaxy S21", "Android 12", "www.samsung.com"));
            phoneDao.insertPhone(new Phone("Google", "Pixel 6", "Android 12", "www.store.google.com"));
            phoneDao.insertPhone(new Phone ("Xiaomi","13 Pro","Android 14","xiaomi.com"));
            // przyklady telefonow
        });
    }

    public void updatePhone(Phone phone){
        new Thread(() -> {
            phoneDao.updatePhone(phone);
        }).start();
    }



}