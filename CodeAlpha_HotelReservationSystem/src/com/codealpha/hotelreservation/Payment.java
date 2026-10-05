package com.codealpha.hotelreservation;

import java.time.LocalDateTime;

public class Payment {

    public enum PaymentMethod {
        CASH,
        CARD,
        UPI
    }

    private int paymentId;
    private int reservationId;
    private double amount;
    private PaymentMethod paymentMethod;
    private String status;
    private LocalDateTime paymentTime;

    public Payment(
            int paymentId,
            int reservationId,
            double amount,
            PaymentMethod paymentMethod) {

        this.paymentId = paymentId;
        this.reservationId = reservationId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;

        this.status = "SUCCESS";

        this.paymentTime =
                LocalDateTime.now();
    }

    public void displayPayment() {

        System.out.println();
        System.out.println(
                "========== PAYMENT DETAILS =========="
        );

        System.out.println(
                "Payment ID     : " + paymentId
        );

        System.out.println(
                "Reservation ID : " + reservationId
        );

        System.out.printf(
                "Amount         : INR %.2f%n",
                amount
        );

        System.out.println(
                "Payment Method : " + paymentMethod
        );

        System.out.println(
                "Status         : " + status
        );

        System.out.println(
                "Payment Time   : " + paymentTime
        );

        System.out.println(
                "====================================="
        );
    }
}