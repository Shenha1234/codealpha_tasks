package com.codealpha.studentgrade;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StudentManager {

    private final List<Student> students;

    private static final String DATA_FOLDER = "data";
    private static final String DATA_FILE =
            DATA_FOLDER + File.separator + "students.csv";

    public StudentManager() {

        students = new ArrayList<>();

        loadFromFile();
    }

    public boolean addStudent(Student student) {

        if (student == null) {
            return false;
        }

        if (findStudentById(student.getStudentId()) != null) {
            return false;
        }

        students.add(student);

        saveToFile();

        return true;
    }

    public boolean updateStudent(
            int studentId,
            String name,
            List<Double> marks) {

        Student student = findStudentById(studentId);

        if (student == null) {
            return false;
        }

        student.setName(name);
        student.clearMarks();

        if (marks != null) {

            for (double mark : marks) {
                student.addMark(mark);
            }
        }

        saveToFile();

        return true;
    }

    public boolean deleteStudent(int studentId) {

        Student student = findStudentById(studentId);

        if (student == null) {
            return false;
        }

        students.remove(student);

        saveToFile();

        return true;
    }

    public Student findStudentById(int studentId) {

        for (Student student : students) {

            if (student.getStudentId() == studentId) {
                return student;
            }
        }

        return null;
    }

    public List<Student> getStudents() {

        return students;
    }

    public void saveToFile() {

        File folder = new File(DATA_FOLDER);

        if (!folder.exists()) {
            folder.mkdirs();
        }

        try (PrintWriter writer =
                     new PrintWriter(
                             new FileWriter(DATA_FILE))) {

            writer.println("studentId,name,marks");

            for (Student student : students) {

                String name =
                        student.getName().replace(",", " ");

                writer.println(
                        student.getStudentId()
                                + ","
                                + name
                                + ","
                                + student.getMarksAsString()
                );
            }

        } catch (IOException e) {

            System.out.println(
                    "Error saving student data: "
                            + e.getMessage()
            );
        }
    }

    private void loadFromFile() {

        File file = new File(DATA_FILE);

        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader =
                     new BufferedReader(
                             new FileReader(file))) {

            String line;

            boolean firstLine = true;

            while ((line = reader.readLine()) != null) {

                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split(",", 3);

                if (parts.length < 2) {
                    continue;
                }

                try {

                    int id =
                            Integer.parseInt(
                                    parts[0].trim()
                            );

                    String name =
                            parts[1].trim();

                    Student student =
                            new Student(id, name);

                    if (parts.length == 3) {

                        student.setMarksFromString(
                                parts[2].trim()
                        );
                    }

                    students.add(student);

                } catch (NumberFormatException e) {

                    System.out.println(
                            "Invalid student record skipped."
                    );
                }
            }

        } catch (IOException e) {

            System.out.println(
                    "Error loading student data: "
                            + e.getMessage()
            );
        }
    }
}