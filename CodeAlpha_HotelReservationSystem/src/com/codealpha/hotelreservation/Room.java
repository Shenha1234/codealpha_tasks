package com.codealpha.hotelreservation;

public class Room {

    public enum RoomType {
        STANDARD,
        DELUXE,
        SUITE
    }

    private int roomNumber;
    private RoomType roomType;
    private double pricePerNight;
    private boolean available;

    public Room(
            int roomNumber,
            RoomType roomType,
            double pricePerNight) {

        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
        this.available = true;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void displayRoom() {

        System.out.printf(
                "Room: %-5d | Type: %-8s | Price/Night: INR %-10.2f | Status: %s%n",
                roomNumber,
                roomType,
                pricePerNight,
                available ? "Available" : "Booked"
        );
    }
}

