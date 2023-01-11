/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package schoolmanagement.main;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.JFrame;
import schoolmanagement.controller.login.LoginController;
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

        // to set FlatLaft Look and Feel for JFrames
        FlatLightLaf.setup();

        LoginController loginController = new LoginController();
    }

}
