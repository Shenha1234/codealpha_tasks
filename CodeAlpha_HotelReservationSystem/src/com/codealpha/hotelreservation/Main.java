package com.codealpha.hotelreservation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {

    private static Scanner scanner =
            new Scanner(System.in);

    private static Hotel hotel =
            new Hotel("CodeAlpha Grand Hotel");

    public static void main(String[] args) {

        initializeRooms();

        System.out.println();
        System.out.println(
                "=============================================="
        );

        System.out.println(
                "       CODEALPHA HOTEL RESERVATION SYSTEM"
        );

        System.out.println(
                "=============================================="
        );

        System.out.println(
                "Welcome to "
                        + hotel.getHotelName()
                        + "!"
        );

        boolean running = true;

        while (running) {

            displayMenu();

            int choice =
                    readInt(
                            "Enter your choice: "
                    );

            try {

                switch (choice) {

                    case 1:
                        hotel.displayAvailableRooms();
                        break;

                    case 2:
                        searchRooms();
                        break;

                    case 3:
                        bookRoom();
                        break;

                    case 4:
                        cancelReservation();
                        break;

                    case 5:
                        viewReservation();
                        break;

                    case 6:
                        hotel.displayReservations();
                        break;

                    case 7:
                        running = false;

                        System.out.println();
                        System.out.println(
                                "Thank you for using "
                                        + "Hotel Reservation System!"
                        );
                        break;

                    default:
                        System.out.println(
                                "Invalid choice. "
                                        + "Please select 1-7."
                        );
                }

            } catch (IllegalArgumentException e) {

                System.out.println();
                System.out.println(
                        "ERROR: " + e.getMessage()
                );
            }
        }

        scanner.close();
    }

    // ==========================================
    // INITIALIZE ROOMS
    // ==========================================

    private static void initializeRooms() {

        hotel.addRoom(
                new Room(
                        101,
                        Room.RoomType.STANDARD,
                        2500
                )
        );

        hotel.addRoom(
                new Room(
                        102,
                        Room.RoomType.STANDARD,
                        2500
                )
        );

        hotel.addRoom(
                new Room(
                        201,
                        Room.RoomType.DELUXE,
                        4000
                )
        );

        hotel.addRoom(
                new Room(
                        202,
                        Room.RoomType.DELUXE,
                        4000
                )
        );

        hotel.addRoom(
                new Room(
                        301,
                        Room.RoomType.SUITE,
                        7000
                )
        );

        hotel.addRoom(
                new Room(
                        302,
                        Room.RoomType.SUITE,
                        7000
                )
        );
    }

    // ==========================================
    // MENU
    // ==========================================

    private static void displayMenu() {

        System.out.println();
        System.out.println(
                "=============== MAIN MENU ==============="
        );

        System.out.println(
                "1. View Available Rooms"
        );

        System.out.println(
                "2. Search Rooms"
        );

        System.out.println(
                "3. Book Room"
        );

        System.out.println(
                "4. Cancel Reservation"
        );

        System.out.println(
                "5. View Booking Details"
        );

        System.out.println(
                "6. View All Reservations"
        );

        System.out.println(
                "7. Exit"
        );

        System.out.println(
                "=========================================="
        );
    }

    // ==========================================
    // SEARCH ROOMS
    // ==========================================

    private static void searchRooms() {

        System.out.println();

        System.out.println(
                "Select Room Type:"
        );

        System.out.println(
                "1. Standard"
        );

        System.out.println(
                "2. Deluxe"
        );

        System.out.println(
                "3. Suite"
        );

        int choice =
                readInt(
                        "Enter type: "
                );

        Room.RoomType roomType;

        switch (choice) {

            case 1:
                roomType =
                        Room.RoomType.STANDARD;
                break;

            case 2:
                roomType =
                        Room.RoomType.DELUXE;
                break;

            case 3:
                roomType =
                        Room.RoomType.SUITE;
                break;

            default:
                throw new IllegalArgumentException(
                        "Invalid room type."
                );
        }

        hotel.searchRooms(roomType);
    }

    // ==========================================
    // BOOK ROOM
    // ==========================================

    private static void bookRoom() {

        System.out.println();

        System.out.println(
                "========== ROOM BOOKING =========="
        );

        String name =
                readString(
                        "Customer name: "
                );

        String phone =
                readString(
                        "Phone number: "
                );

        String email =
                readString(
                        "Email: "
                );

        Customer customer =
                new Customer(
                        (int) (Math.random() * 9000) + 1000,
                        name,
                        phone,
                        email
                );

        hotel.displayAvailableRooms();

        int roomNumber =
                readInt(
                        "Enter room number: "
                );

        LocalDate checkIn =
                readDate(
                        "Enter check-in date (yyyy-MM-dd): "
                );

        LocalDate checkOut =
                readDate(
                        "Enter check-out date (yyyy-MM-dd): "
                );

        Reservation reservation =
                hotel.bookRoom(
                        customer,
                        roomNumber,
                        checkIn,
                        checkOut
                );

        System.out.println();

        System.out.println(
                "Room booked successfully!"
        );

        reservation.displayReservation();

        // Payment

        System.out.println();

        System.out.println(
                "Select Payment Method:"
        );

        System.out.println(
                "1. Cash"
        );

        System.out.println(
                "2. Card"
        );

        System.out.println(
                "3. UPI"
        );

        int paymentChoice =
                readInt(
                        "Enter payment method: "
                );

        Payment.PaymentMethod method;

        switch (paymentChoice) {

            case 1:
                method =
                        Payment.PaymentMethod.CASH;
                break;

            case 2:
                method =
                        Payment.PaymentMethod.CARD;
                break;

            case 3:
                method =
                        Payment.PaymentMethod.UPI;
                break;

            default:
                throw new IllegalArgumentException(
                        "Invalid payment method."
                );
        }

        Payment payment =
                hotel.processPayment(
                        reservation,
                        method
                );

        payment.displayPayment();
    }

    // ==========================================
    // CANCEL RESERVATION
    // ==========================================

    private static void cancelReservation() {

        int reservationId =
                readInt(
                        "Enter reservation ID: "
                );

        hotel.cancelReservation(
                reservationId
        );
    }

    // ==========================================
    // VIEW RESERVATION
    // ==========================================

    private static void viewReservation() {

        int reservationId =
                readInt(
                        "Enter reservation ID: "
                );

        Reservation reservation =
                hotel.getReservation(
                        reservationId
                );

        if (reservation == null) {

            System.out.println(
                    "Reservation not found."
            );

            return;
        }

        reservation.displayReservation();
    }

    // ==========================================
    // READ STRING
    // ==========================================

    private static String readString(
            String message) {

        System.out.print(message);

        return scanner.nextLine().trim();
    }

    // ==========================================
    // READ INTEGER
    // ==========================================

    private static int readInt(
            String message) {

        while (true) {

            try {

                System.out.print(message);

                String input =
                        scanner.nextLine().trim();

                return Integer.parseInt(input);

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid number."
                );
            }
        }
    }

    // ==========================================
    // READ DATE
    // ==========================================

    private static LocalDate readDate(
            String message) {

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern(
                        "yyyy-MM-dd"
                );

        while (true) {

            try {

                System.out.print(message);

                String input =
                        scanner.nextLine().trim();

                return LocalDate.parse(
                        input,
                        formatter
                );

            } catch (DateTimeParseException e) {

                System.out.println(
                        "Invalid date. "
                                + "Use yyyy-MM-dd."
                );
            }
        }
    }
}