package com.example.lab2phonedatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "phones")
public class Phone implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "manufacturer")
    private String manufacturer;

    @ColumnInfo(name = "model")
    private String model;

    @ColumnInfo(name = "android_version")
    private String androidVersion;

    @ColumnInfo(name = "website")
    private String website;


    public Phone(String manufacturer, String model, String androidVersion, String website) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.androidVersion = androidVersion;
        this.website = website;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getAndroidVersion() {
        return androidVersion;
    }

    public void setAndroidVersion(String androidVersion) {
        this.androidVersion = androidVersion;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}

