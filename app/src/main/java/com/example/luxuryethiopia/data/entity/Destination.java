package com.example.luxuryethiopia.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "destinations")
public class Destination {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "destination_id")
    private int id;

    @ColumnInfo(name = "destination_name")
    private String name;

    @ColumnInfo(name = "destination_location")
    private String location;

    @ColumnInfo(name = "luxury_description")
    private String luxuryDescription;

    // Constructor
    public Destination(String name, String location, String luxuryDescription) {
        this.name = name;
        this.location = location;
        this.luxuryDescription = luxuryDescription;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getLuxuryDescription() { return luxuryDescription; }
    public void setLuxuryDescription(String luxuryDescription) {
        this.luxuryDescription = luxuryDescription;
    }
}