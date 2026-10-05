package com.codealpha.studentgrade;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            StudentGradeGUI gui =
                    new StudentGradeGUI();

            gui.setVisible(true);
        });
    }
}