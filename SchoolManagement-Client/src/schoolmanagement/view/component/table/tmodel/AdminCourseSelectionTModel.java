/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schoolmanagement.view.component.table.tmodel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import schoolmanagement.commonlib.model.Course;
import schoolmanagement.commonlib.model.Language;

/**
 *
 * @author ivano
 */
public class AdminCourseSelectionTModel extends AbstractTableModel {

    private List<Course> courses;
    private final String[] header = {"Name", "Start date", "End date", "Capacity", "Language", "Level"};

    public AdminCourseSelectionTModel(List<Course> courses) {
        this.courses = courses;
    }

    @Override
    public int getRowCount() {
        return courses.size();
    }

    @Override
    public int getColumnCount() {
        return header.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Course temp = courses.get(rowIndex);
        return switch (columnIndex) {
            case 0 ->
                temp.getName();
            case 1 ->
                temp.getStartDate();
            case 2 ->
                temp.getEndDate();
            case 3 ->
                temp.getGroupCapacity();
            case 4 ->
                temp.getLanguage().getName();
            case 5 ->
                temp.getLanguage().getLevel();
            default ->
                "N/A";
        };
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex != 5;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (rowIndex > courses.size() - 1) {
            return;
        }
        Course temp = courses.get(rowIndex);
        try {
            switch (columnIndex) {
                case 0 -> {
                    if (((String) aValue).equals("")) {
                        JOptionPane.showMessageDialog(null, "Please insert name of course", "Error", JOptionPane.WARNING_MESSAGE);
                    } else {
                        temp.setName((String) aValue);
                    }
                }
                case 1 -> {
                    LocalDate startDate = LocalDate.parse((String) aValue, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    if (startDate.isAfter(temp.getEndDate()) || startDate.isEqual(temp.getEndDate())) {
                        JOptionPane.showMessageDialog(null, "Start date can't be after end date", "Error", JOptionPane.WARNING_MESSAGE);
                    } else {
                        temp.setStartDate(startDate);
                    }
                }
                case 2 -> {
                    LocalDate endDate = LocalDate.parse((String) aValue, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    if (endDate.isBefore(temp.getStartDate()) || endDate.isEqual(temp.getStartDate())) {
                        JOptionPane.showMessageDialog(null, "End date can't be before start date", "Error", JOptionPane.WARNING_MESSAGE);
                    } else {
                        temp.setEndDate(endDate);
                    }
                }
                case 3 ->
                    temp.setGroupCapacity(Integer.valueOf((String) aValue));
                case 4 -> {
                    temp.setLanguage((Language) aValue);
                    fireTableDataChanged();
                }
            }
        } catch (NumberFormatException | DateTimeParseException ex) {
            // when user leaves empty capacity field
            // or enter manually date with wrong format
            // and try to select another row
            // old values will be automatically shown
        }
    }

    @Override
    public String getColumnName(int column
    ) {
        return header[column];
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
        fireTableDataChanged();
    }

    public Course getCourse(int index) {
        return courses.get(index);
    }
}
