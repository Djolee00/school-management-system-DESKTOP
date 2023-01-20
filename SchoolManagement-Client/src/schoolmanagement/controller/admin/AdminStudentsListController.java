/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schoolmanagement.controller.admin;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import schoolmanagement.commonlib.communication.Operation;
import schoolmanagement.commonlib.communication.Request;
import schoolmanagement.commonlib.communication.Response;
import schoolmanagement.commonlib.communication.ResponseType;
import schoolmanagement.commonlib.model.Course;
import schoolmanagement.commonlib.model.Language;
import schoolmanagement.commonlib.model.Student;
import schoolmanagement.communication.Communication;
import schoolmanagement.view.admin.AdminStudentsListView;
import schoolmanagement.view.component.StudentsListModel;

/**
 *
 * @author ivano
 */
public class AdminStudentsListController {

    private final AdminStudentsListView studentsView;
    private final StudentsListModel tableModel;
    private List<Student> students;
    private List<Student> backupStudents;

    public AdminStudentsListController() {
        studentsView = new AdminStudentsListView();
        initView();
        studentsView.setVisible(true);
        tableModel = (StudentsListModel) studentsView.getTblStudents().getModel();
    }

    private void initView() {
        initListeners();
        populateTable();
        initCourses();
        initLanguages();
    }

    private void initListeners() {
        studentsView.getLblHome().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new AdminHomeController();
                studentsView.dispose();
            }
        });

        studentsView.getBtnSearch().addActionListener(e -> searchStudents());
        studentsView.getBtnUpdate().addActionListener(e -> updateStudent());
    }

    private void populateTable() {
        students = getAllStudents();
        backupStudents = students;
        studentsView.getTblStudents().setModel(new StudentsListModel(students));
    }

    private void initCourses() {
        List<Course> courses = getAllCourses();
        studentsView.getJcbCourses().setModel(new DefaultComboBoxModel(courses.toArray()));
        studentsView.getJcbCourses().setSelectedIndex(-1);
    }

    private void initLanguages() {
        List<Language> languages = getAllLanguages();
        studentsView.getJcbLanguages().setModel(new DefaultComboBoxModel(languages.toArray()));
        studentsView.getJcbLanguages().setSelectedIndex(-1);
    }

    private void searchStudents() {

    }

    private void updateStudent() {

    }

    private List<Student> getAllStudents() {
        List<Student> tempStudents = null;

        try {
            Communication.getInstance().send(new Request(Operation.GET_ALL_STUDENTS, null));

            Response response = Communication.getInstance().receive();

            if (response.getResponseType() == ResponseType.SUCCESS) {
                tempStudents = (List<Student>) response.getObject();
            } else {
                JOptionPane.showMessageDialog(studentsView, "Error getting students' data. Try again later!", "Error", JOptionPane.ERROR_MESSAGE);
                studentsView.dispose();
                System.exit(0);
            }

        } catch (ClassNotFoundException | IOException ex) {
            JOptionPane.showMessageDialog(studentsView, "Error getting students' data. Try again later!", "Error", JOptionPane.ERROR_MESSAGE);
            studentsView.dispose();
            System.exit(0);
        }

        return tempStudents;
    }

    private List<Course> getAllCourses() {
        List<Course> tempCourses = null;

        try {
            Communication.getInstance().send(new Request(Operation.GET_ALL_COURSES, null));

            Response response = Communication.getInstance().receive();

            if (response.getResponseType() == ResponseType.SUCCESS) {
                tempCourses = (List<Course>) response.getObject();
            } else {
                JOptionPane.showMessageDialog(studentsView, "Error getting courses' data. Try again later!", "Error", JOptionPane.ERROR_MESSAGE);
                studentsView.dispose();
                System.exit(0);
            }

        } catch (ClassNotFoundException | IOException ex) {
            JOptionPane.showMessageDialog(studentsView, "Error getting courses' data. Try again later!", "Error", JOptionPane.ERROR_MESSAGE);
            studentsView.dispose();
            System.exit(0);
        }

        return tempCourses;
    }

    private List<Language> getAllLanguages() {
        List<Language> tempLanguages = null;

        try {
            Communication.getInstance().send(new Request(Operation.GET_ALL_LANGUAGES, null));

            Response response = Communication.getInstance().receive();

            if (response.getResponseType() == ResponseType.SUCCESS) {
                tempLanguages = (List<Language>) response.getObject();
            } else {
                JOptionPane.showMessageDialog(studentsView, "Error getting languages' data. Try again later!", "Error", JOptionPane.ERROR_MESSAGE);
                studentsView.dispose();
                System.exit(0);
            }

        } catch (ClassNotFoundException | IOException ex) {
            JOptionPane.showMessageDialog(studentsView, "Error getting languages' data. Try again later!", "Error", JOptionPane.ERROR_MESSAGE);
            studentsView.dispose();
            System.exit(0);
        }

        return tempLanguages;
    }

}
