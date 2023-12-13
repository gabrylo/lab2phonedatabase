package com.example.lab2phonedatabase;

import androidx.lifecycle.ViewModel;

public class PhoneViewModel extends ViewModel {
    private PhoneRepository phoneRepository;

    public PhoneViewModel(PhoneRepository phoneRepository) {
        this.phoneRepository = phoneRepository;
    }

    // Metoda do dodawania telefonu do bazy danych
    public void insertPhone(String manufacturer, String model, String androidVersion, String website) {
        Phone phone = new Phone(manufacturer, model, androidVersion, website);
        phoneRepository.insertPhone(phone);
    }
}