/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schoolmanagement.view.component;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import schoolmanagement.commonlib.model.Course;

/**
 *
 * @author ivano
 */
public class AdminCourseSelectionTModel extends AbstractTableModel{

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
        return true;
    }

    @Override
    public String getColumnName(int column) {
        return header[column];
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
        fireTableDataChanged();
    }
    
}
