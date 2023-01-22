/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schoolmanagement.view.component;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import schoolmanagement.commonlib.model.Student;

/**
 *
 * @author ivano
 */
public class StudentsListTModel extends AbstractTableModel {

    private List<Student> students;
    private String[] header = {"First name", "Last name", "Birthdate"};

    public StudentsListTModel(List<Student> students) {
        this.students = students;
    }

    @Override
    public int getRowCount() {
        return students.size();
    }

    @Override
    public int getColumnCount() {
        return header.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Student temp = students.get(rowIndex);
        return switch (columnIndex) {
            case 0 ->
                temp.getFirstName();
            case 1 ->
                temp.getLastName();
            case 2 ->
                temp.getBirthdate();
            default ->
                "N/A";
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

    public void setStudents(List<Student> temp) {
        students = temp;
        fireTableDataChanged();
    }

    public Student getStudent(int index){
        return students.get(index);
    }

}
