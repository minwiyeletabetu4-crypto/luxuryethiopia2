package com.example.luxuryethiopia.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "bookings",
        foreignKeys = {
                @ForeignKey(
                        entity = User.class,
                        parentColumns = "user_id",
                        childColumns = "fk_user_id",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Destination.class,
                        parentColumns = "destination_id",
                        childColumns = "fk_destination_id",
                        onDelete = ForeignKey.CASCADE
                )
        },
        indices = {
                @Index(value = "fk_user_id"),
                @Index(value = "fk_destination_id")
        }
)
public class Booking {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "booking_id")
    private int id;

    @ColumnInfo(name = "fk_user_id")
    private int userId;

    @ColumnInfo(name = "fk_destination_id")
    private int destinationId;

    @ColumnInfo(name = "booking_date")
    private String bookingDate;

    // Constructor
    public Booking(int userId, int destinationId, String bookingDate) {
        this.userId = userId;
        this.destinationId = destinationId;
        this.bookingDate = bookingDate;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getDestinationId() { return destinationId; }
    public void setDestinationId(int destinationId) { this.destinationId = destinationId; }

    public String getBookingDate() { return bookingDate; }
    public void setBookingDate(String bookingDate) { this.bookingDate = bookingDate; }
}