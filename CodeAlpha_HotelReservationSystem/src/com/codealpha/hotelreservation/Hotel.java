package com.codealpha.hotelreservation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Hotel {

    private String hotelName;

    private List<Room> rooms;
    private List<Reservation> reservations;
    private List<Payment> payments;

    private int nextReservationId = 1001;
    private int nextPaymentId = 5001;

    public Hotel(String hotelName) {

        this.hotelName = hotelName;

        rooms = new ArrayList<>();
        reservations = new ArrayList<>();
        payments = new ArrayList<>();
    }

    // =====================================
    // ADD ROOM
    // =====================================

    public void addRoom(Room room) {

        rooms.add(room);
    }

    // =====================================
    // DISPLAY AVAILABLE ROOMS
    // =====================================

    public void displayAvailableRooms() {

        System.out.println();
        System.out.println(
                "========== AVAILABLE ROOMS =========="
        );

        boolean found = false;

        for (Room room : rooms) {

            if (room.isAvailable()) {

                room.displayRoom();

                found = true;
            }
        }

        if (!found) {

            System.out.println(
                    "No rooms available."
            );
        }
    }

    // =====================================
    // SEARCH BY ROOM TYPE
    // =====================================

    public void searchRooms(
            Room.RoomType roomType) {

        System.out.println();

        System.out.println(
                "========== SEARCH RESULTS =========="
        );

        boolean found = false;

        for (Room room : rooms) {

            if (room.isAvailable()
                    && room.getRoomType()
                    == roomType) {

                room.displayRoom();

                found = true;
            }
        }

        if (!found) {

            System.out.println(
                    "No available rooms found."
            );
        }
    }

    // =====================================
    // GET ROOM
    // =====================================

    public Room getRoom(int roomNumber) {

        for (Room room : rooms) {

            if (room.getRoomNumber()
                    == roomNumber) {

                return room;
            }
        }

        return null;
    }

    // =====================================
    // BOOK ROOM
    // =====================================

    public Reservation bookRoom(
            Customer customer,
            int roomNumber,
            LocalDate checkIn,
            LocalDate checkOut) {

        Room room =
                getRoom(roomNumber);

        if (room == null) {

            throw new IllegalArgumentException(
                    "Room not found."
            );
        }

        if (!room.isAvailable()) {

            throw new IllegalArgumentException(
                    "Room is already booked."
            );
        }

        if (!checkOut.isAfter(checkIn)) {

            throw new IllegalArgumentException(
                    "Check-out date must be after check-in date."
            );
        }

        Reservation reservation =
                new Reservation(
                        nextReservationId++,
                        customer,
                        room,
                        checkIn,
                        checkOut
                );

        room.setAvailable(false);

        reservations.add(reservation);

        return reservation;
    }

    // =====================================
    // CANCEL RESERVATION
    // =====================================

    public void cancelReservation(
            int reservationId) {

        Reservation reservation =
                getReservation(reservationId);

        if (reservation == null) {

            throw new IllegalArgumentException(
                    "Reservation not found."
            );
        }

        if (reservation.getStatus()
                .equals("CANCELLED")) {

            throw new IllegalArgumentException(
                    "Reservation is already cancelled."
            );
        }

        reservation.cancelReservation();

        System.out.println(
                "Reservation cancelled successfully."
        );
    }

    // =====================================
    // GET RESERVATION
    // =====================================

    public Reservation getReservation(
            int reservationId) {

        for (Reservation reservation
                : reservations) {

            if (reservation.getReservationId()
                    == reservationId) {

                return reservation;
            }
        }

        return null;
    }

    // =====================================
    // DISPLAY ALL RESERVATIONS
    // =====================================

    public void displayReservations() {

        System.out.println();
        System.out.println(
                "========== ALL RESERVATIONS =========="
        );

        if (reservations.isEmpty()) {

            System.out.println(
                    "No reservations available."
            );

            return;
        }

        for (Reservation reservation
                : reservations) {

            reservation.displayReservation();
        }
    }

    // =====================================
    // PROCESS PAYMENT
    // =====================================

    public Payment processPayment(
            Reservation reservation,
            Payment.PaymentMethod method) {

        Payment payment =
                new Payment(
                        nextPaymentId++,
                        reservation.getReservationId(),
                        reservation.getTotalAmount(),
                        method
                );

        payments.add(payment);

        return payment;
    }

    // =====================================
    // GET HOTEL NAME
    // =====================================

    public String getHotelName() {

        return hotelName;
    }
}
