/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package schoolmanagement.main;

import com.formdev.flatlaf.FlatIntelliJLaf;
import javax.swing.JFrame;
import javax.swing.UIManager;
import schoolmanagement.view.admin.AdminCoursesView;
import schoolmanagement.view.admin.AdminGroupsView;
import schoolmanagement.view.admin.AdminHomeView;
import schoolmanagement.view.admin.AdminStudentsListView;
import schoolmanagement.view.login.LoginView;
import schoolmanagement.view.student.StudentCoursesView;
import schoolmanagement.view.student.StudentHomeView;
import schoolmanagement.view.student.StudentProfileView;

/**
 *
 * @author ivano
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // important for Look And Feel of JFrames
        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
        } catch (Exception ignored) {
        }

        JFrame mainFrame = new StudentHomeView();
        mainFrame.setVisible(true);

        JFrame loginFrame = new LoginView();
        loginFrame.setVisible(true);

        JFrame studentProfile = new StudentProfileView();
        studentProfile.setVisible(true);

        JFrame courses = new StudentCoursesView();
        courses.setVisible(true);

        JFrame admin = new AdminHomeView();
        admin.setVisible(true);

        JFrame studentList = new AdminStudentsListView();
        studentList.setVisible(true);

        JFrame coursesAdmin = new AdminCoursesView();
        coursesAdmin.setVisible(true);

        JFrame groups = new AdminGroupsView();
        groups.setVisible(true);

    }

}
