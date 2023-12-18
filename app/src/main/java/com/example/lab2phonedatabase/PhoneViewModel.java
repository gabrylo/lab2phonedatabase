package com.example.lab2phonedatabase;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class PhoneViewModel extends AndroidViewModel {

    private PhoneRepository phoneRepository;
    private LiveData<List<Phone>> allPhones;

    public PhoneViewModel(Application application) {
        super(application);
        phoneRepository = new PhoneRepository(application);
        allPhones = phoneRepository.getAllPhones();
    }

    LiveData<List<Phone>> getAllPhones() {
        return allPhones;
    }

    public void deleteAllPhones() {
        phoneRepository.deleteAllPhones();
    }

    public void deletePhone(Phone phone) {
        phoneRepository.deletePhone(phone);
    }

    public void updatePhone(Phone phone) {
        phoneRepository.updatePhone(phone);
    }

    public void insertPhone(Phone phone) {
        phoneRepository.insertPhone(phone);
    }

}

