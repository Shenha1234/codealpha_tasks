package com.codealpha.studentgrade;

import java.util.List;

public class GradeCalculator {

    public static double calculateClassAverage(List<Student> students) {

        if (students == null || students.isEmpty()) {
            return 0;
        }

        double total = 0;
        int count = 0;

        for (Student student : students) {

            if (!student.getMarks().isEmpty()) {
                total += student.getAverage();
                count++;
            }
        }

        if (count == 0) {
            return 0;
        }

        return total / count;
    }

    public static double findHighestScore(List<Student> students) {

        if (students == null || students.isEmpty()) {
            return 0;
        }

        double highest = 0;

        for (Student student : students) {

            if (student.getHighestMark() > highest) {
                highest = student.getHighestMark();
            }
        }

        return highest;
    }

    public static double findLowestScore(List<Student> students) {

        if (students == null || students.isEmpty()) {
            return 0;
        }

        double lowest = Double.MAX_VALUE;
        boolean found = false;

        for (Student student : students) {

            if (!student.getMarks().isEmpty()) {

                if (student.getLowestMark() < lowest) {
                    lowest = student.getLowestMark();
                }

                found = true;
            }
        }

        return found ? lowest : 0;
    }

    public static String getSummary(List<Student> students) {

        if (students == null || students.isEmpty()) {

            return "No student records available.";
        }

        return String.format(
                "Total Students : %d%n" +
                "Class Average  : %.2f%n" +
                "Highest Score  : %.2f%n" +
                "Lowest Score   : %.2f",
                students.size(),
                calculateClassAverage(students),
                findHighestScore(students),
                findLowestScore(students)
        );
    }
}