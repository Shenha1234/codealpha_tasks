package com.codealpha.hotelreservation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Reservation {

    private int reservationId;
    private Customer customer;
    private Room room;

    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    private double totalAmount;

    private String status;

    public Reservation(
            int reservationId,
            Customer customer,
            Room room,
            LocalDate checkInDate,
            LocalDate checkOutDate) {

        this.reservationId = reservationId;
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;

        long nights =
                java.time.temporal.ChronoUnit.DAYS.between(
                        checkInDate,
                        checkOutDate
                );

        this.totalAmount =
                nights * room.getPricePerNight();

        this.status = "CONFIRMED";
    }

    public int getReservationId() {
        return reservationId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Room getRoom() {
        return room;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void cancelReservation() {

        status = "CANCELLED";

        room.setAvailable(true);
    }

    public void displayReservation() {

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd-MM-yyyy");

        System.out.println();
        System.out.println(
                "=========================================="
        );

        System.out.println(
                "Reservation ID : " + reservationId
        );

        System.out.println(
                "Customer       : "
                        + customer.getName()
        );

        System.out.println(
                "Room Number    : "
                        + room.getRoomNumber()
        );

        System.out.println(
                "Room Type      : "
                        + room.getRoomType()
        );

        System.out.println(
                "Check-in       : "
                        + checkInDate.format(formatter)
        );

        System.out.println(
                "Check-out      : "
                        + checkOutDate.format(formatter)
        );

        System.out.printf(
                "Total Amount   : INR %.2f%n",
                totalAmount
        );

        System.out.println(
                "Status         : " + status
        );

        System.out.println(
                "=========================================="
        );
    }
}