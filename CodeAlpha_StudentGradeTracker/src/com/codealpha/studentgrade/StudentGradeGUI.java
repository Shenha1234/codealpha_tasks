package com.codealpha.studentgrade;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class StudentGradeGUI extends JFrame {

    private final StudentManager studentManager;

    private JTextField idField;
    private JTextField nameField;
    private JTextField marksField;

    private JTable studentTable;
    private DefaultTableModel tableModel;

    private JLabel summaryLabel;

    public StudentGradeGUI() {

        studentManager = new StudentManager();

        initializeWindow();
        createInterface();
        refreshTable();
    }

    private void initializeWindow() {

        setTitle("CodeAlpha - Student Grade Tracker");

        setSize(1000, 650);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );
    }

    private void createInterface() {

        setLayout(new BorderLayout(10, 10));

        JLabel title =
                new JLabel(
                        "STUDENT GRADE TRACKER",
                        SwingConstants.CENTER
                );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        26
                )
        );

        add(title, BorderLayout.NORTH);

        JPanel inputPanel = createInputPanel();

        add(inputPanel, BorderLayout.WEST);

        createTable();

        JScrollPane scrollPane =
                new JScrollPane(studentTable);

        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel =
                createBottomPanel();

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel createInputPanel() {

        JPanel panel =
                new JPanel();

        panel.setLayout(
                new GridLayout(
                        0,
                        1,
                        5,
                        5
                )
        );

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        10,
                        10,
                        10
                )
        );

        panel.setPreferredSize(
                new Dimension(250, 0)
        );

        panel.add(new JLabel("Student ID"));

        idField = new JTextField();

        panel.add(idField);

        panel.add(new JLabel("Student Name"));

        nameField = new JTextField();

        panel.add(nameField);

        panel.add(
                new JLabel(
                        "Marks (e.g. 85,78,92)"
                )
        );

        marksField = new JTextField();

        panel.add(marksField);

        JButton addButton =
                new JButton("Add Student");

        addButton.addActionListener(
                e -> addStudent()
        );

        panel.add(addButton);

        JButton updateButton =
                new JButton("Update Student");

        updateButton.addActionListener(
                e -> updateStudent()
        );

        panel.add(updateButton);

        JButton deleteButton =
                new JButton("Delete Student");

        deleteButton.addActionListener(
                e -> deleteStudent()
        );

        panel.add(deleteButton);

        JButton searchButton =
                new JButton("Search Student");

        searchButton.addActionListener(
                e -> searchStudent()
        );

        panel.add(searchButton);

        JButton clearButton =
                new JButton("Clear");

        clearButton.addActionListener(
                e -> clearFields()
        );

        panel.add(clearButton);

        return panel;
    }

    private void createTable() {

        String[] columns = {
                "ID",
                "Name",
                "Marks",
                "Total",
                "Average",
                "Highest",
                "Lowest",
                "Grade"
        };

        tableModel =
                new DefaultTableModel(columns, 0) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column) {

                        return false;
                    }
                };

        studentTable =
                new JTable(tableModel);

        studentTable.setRowHeight(28);

        studentTable
                .getSelectionModel()
                .addListSelectionListener(
                        e -> loadSelectedStudent()
                );
    }

    private JPanel createBottomPanel() {

        JPanel panel =
                new JPanel(
                        new BorderLayout()
                );

        summaryLabel =
                new JLabel();

        summaryLabel.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        10,
                        10,
                        10
                )
        );

        panel.add(
                summaryLabel,
                BorderLayout.CENTER
        );

        JButton reportButton =
                new JButton("Class Summary");

        reportButton.addActionListener(
                e -> showSummary()
        );

        panel.add(
                reportButton,
                BorderLayout.EAST
        );

        return panel;
    }

    private void addStudent() {

        try {

            int id =
                    Integer.parseInt(
                            idField.getText().trim()
                    );

            String name =
                    nameField.getText().trim();

            if (name.isEmpty()) {

                showError(
                        "Student name is required."
                );

                return;
            }

            if (studentManager.findStudentById(id)
                    != null) {

                showError(
                        "Student ID already exists."
                );

                return;
            }

            Student student =
                    new Student(id, name);

            List<Double> marks =
                    parseMarks(
                            marksField.getText()
                    );

            for (double mark : marks) {
                student.addMark(mark);
            }

            studentManager.addStudent(student);

            refreshTable();

            clearFields();

            showMessage(
                    "Student added successfully."
            );

        } catch (NumberFormatException e) {

            showError(
                    "Student ID must be a valid number."
            );
        }
    }

    private void updateStudent() {

        try {

            int id =
                    Integer.parseInt(
                            idField.getText().trim()
                    );

            String name =
                    nameField.getText().trim();

            if (name.isEmpty()) {

                showError(
                        "Student name is required."
                );

                return;
            }

            List<Double> marks =
                    parseMarks(
                            marksField.getText()
                    );

            boolean updated =
                    studentManager.updateStudent(
                            id,
                            name,
                            marks
                    );

            if (!updated) {

                showError(
                        "Student not found."
                );

                return;
            }

            refreshTable();

            showMessage(
                    "Student updated successfully."
            );

        } catch (NumberFormatException e) {

            showError(
                    "Student ID must be a valid number."
            );
        }
    }

    private void deleteStudent() {

        try {

            int id =
                    Integer.parseInt(
                            idField.getText().trim()
                    );

            int answer =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Delete student " + id + "?",
                            "Confirm Delete",
                            JOptionPane.YES_NO_OPTION
                    );

            if (answer != JOptionPane.YES_OPTION) {
                return;
            }

            boolean deleted =
                    studentManager.deleteStudent(id);

            if (!deleted) {

                showError(
                        "Student not found."
                );

                return;
            }

            refreshTable();

            clearFields();

            showMessage(
                    "Student deleted successfully."
            );

        } catch (NumberFormatException e) {

            showError(
                    "Enter a valid Student ID."
            );
        }
    }

    private void searchStudent() {

        try {

            int id =
                    Integer.parseInt(
                            idField.getText().trim()
                    );

            Student student =
                    studentManager.findStudentById(id);

            if (student == null) {

                showError(
                        "Student not found."
                );

                return;
            }

            nameField.setText(
                    student.getName()
            );

            marksField.setText(
                    student.getMarksAsString()
                            .replace(";", ",")
            );

            selectStudentInTable(id);

        } catch (NumberFormatException e) {

            showError(
                    "Enter a valid Student ID."
            );
        }
    }

    private void loadSelectedStudent() {

        int row =
                studentTable.getSelectedRow();

        if (row < 0) {
            return;
        }

        int id =
                (Integer) tableModel
                        .getValueAt(row, 0);

        Student student =
                studentManager.findStudentById(id);

        if (student == null) {
            return;
        }

        idField.setText(
                String.valueOf(
                        student.getStudentId()
                )
        );

        nameField.setText(
                student.getName()
        );

        marksField.setText(
                student.getMarksAsString()
                        .replace(";", ",")
        );
    }

    private List<Double> parseMarks(
            String text) {

        List<Double> marks =
                new ArrayList<>();

        if (text == null ||
                text.trim().isEmpty()) {

            return marks;
        }

        String[] values =
                text.split(",");

        for (String value : values) {

            double mark;

            try {

                mark =
                        Double.parseDouble(
                                value.trim()
                        );

            } catch (NumberFormatException e) {

                throw new IllegalArgumentException(
                        "Invalid mark: " + value
                );
            }

            if (mark < 0 || mark > 100) {

                throw new IllegalArgumentException(
                        "Marks must be between 0 and 100."
                );
            }

            marks.add(mark);
        }

        return marks;
    }

    private void refreshTable() {

        tableModel.setRowCount(0);

        for (Student student :
                studentManager.getStudents()) {

            tableModel.addRow(
                    new Object[]{
                            student.getStudentId(),
                            student.getName(),
                            student.getMarksAsString()
                                    .replace(";", ","),
                            String.format(
                                    "%.2f",
                                    student.getTotal()
                            ),
                            String.format(
                                    "%.2f",
                                    student.getAverage()
                            ),
                            String.format(
                                    "%.2f",
                                    student.getHighestMark()
                            ),
                            String.format(
                                    "%.2f",
                                    student.getLowestMark()
                            ),
                            student.getGrade()
                    }
            );
        }

        updateSummaryLabel();
    }

    private void updateSummaryLabel() {

        String summary =
                GradeCalculator.getSummary(
                        studentManager.getStudents()
                );

        summaryLabel.setText(
                "<html>" +
                        summary.replace(
                                "\n",
                                "<br>"
                        ) +
                        "</html>"
        );
    }

    private void showSummary() {

        JOptionPane.showMessageDialog(
                this,
                GradeCalculator.getSummary(
                        studentManager.getStudents()
                ),
                "Class Summary Report",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void selectStudentInTable(int id) {

        for (int row = 0;
             row < tableModel.getRowCount();
             row++) {

            int tableId =
                    (Integer) tableModel
                            .getValueAt(row, 0);

            if (tableId == id) {

                studentTable
                        .setRowSelectionInterval(
                                row,
                                row
                        );

                break;
            }
        }
    }

    private void clearFields() {

        idField.setText("");
        nameField.setText("");
        marksField.setText("");

        studentTable.clearSelection();
    }

    private void showMessage(
            String message) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "Success",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void showError(
            String message) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}