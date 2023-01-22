/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schoolmanagement.view.component;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import schoolmanagement.commonlib.model.CourseEnrollment;

/**
 *
 * @author ivano
 */
public class StudentProfileMyCoursesTModel extends AbstractTableModel{
    
    private final List<CourseEnrollment> courseEnrollments;
    private final String[] header = {"Name","Start date","End date","Capacity","Language","Level"};

    public StudentProfileMyCoursesTModel(List<CourseEnrollment> courseEnrollments) {
        this.courseEnrollments = courseEnrollments;
    }

    @Override
    public int getRowCount() {
        return courseEnrollments.size();
    }

    @Override
    public int getColumnCount() {
        return header.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        CourseEnrollment temp = courseEnrollments.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> temp.getCourse().getName();
            case 1 -> temp.getCourse().getStartDate();
            case 2 -> temp.getCourse().getEndDate();
            case 3 -> temp.getCourse().getGroupCapacity();
            case 4 -> temp.getCourse().getLanguage().getName();
            case 5 -> temp.getCourse().getLanguage().getLevel();
            default -> "N/A";
        };
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public String getColumnName(int column) {
        return header[column];
    }
    
    public CourseEnrollment getCourseEnrollment(int index){
        return courseEnrollments.get(index);
    }
    
}
