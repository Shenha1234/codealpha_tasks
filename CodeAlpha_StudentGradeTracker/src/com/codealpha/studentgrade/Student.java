package com.codealpha.studentgrade;

import java.util.ArrayList;
import java.util.List;

public class Student {

    private int studentId;
    private String name;
    private List<Double> marks;

    public Student(int studentId, String name) {
        this.studentId = studentId;
        this.name = name;
        this.marks = new ArrayList<>();
    }

    public int getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Double> getMarks() {
        return marks;
    }

    public void addMark(double mark) {
        marks.add(mark);
    }

    public void clearMarks() {
        marks.clear();
    }

    public double getTotal() {

        double total = 0;

        for (double mark : marks) {
            total += mark;
        }

        return total;
    }

    public double getAverage() {

        if (marks.isEmpty()) {
            return 0;
        }

        return getTotal() / marks.size();
    }

    public double getHighestMark() {

        if (marks.isEmpty()) {
            return 0;
        }

        double highest = marks.get(0);

        for (double mark : marks) {
            if (mark > highest) {
                highest = mark;
            }
        }

        return highest;
    }

    public double getLowestMark() {

        if (marks.isEmpty()) {
            return 0;
        }

        double lowest = marks.get(0);

        for (double mark : marks) {
            if (mark < lowest) {
                lowest = mark;
            }
        }

        return lowest;
    }

    public String getGrade() {

        double average = getAverage();

        if (average >= 90) {
            return "A+";
        } else if (average >= 80) {
            return "A";
        } else if (average >= 70) {
            return "B";
        } else if (average >= 60) {
            return "C";
        } else if (average >= 50) {
            return "D";
        } else {
            return "F";
        }
    }

    public String getMarksAsString() {

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < marks.size(); i++) {

            if (i > 0) {
                result.append(";");
            }

            result.append(marks.get(i));
        }

        return result.toString();
    }

    public void setMarksFromString(String marksString) {

        marks.clear();

        if (marksString == null || marksString.trim().isEmpty()) {
            return;
        }

        String[] values = marksString.split(";");

        for (String value : values) {

            try {
                marks.add(Double.parseDouble(value));
            } catch (NumberFormatException e) {
                // Ignore invalid mark
            }
        }
    }
}