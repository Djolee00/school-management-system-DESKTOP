/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schoolmanagement.controller.student;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.swing.JOptionPane;
import schoolmanagement.commonlib.communication.Operation;
import schoolmanagement.commonlib.communication.Request;
import schoolmanagement.commonlib.model.Student;
import schoolmanagement.communication.Communication;
import schoolmanagement.controller.login.LoginController;
import schoolmanagement.session.Session;
import schoolmanagement.view.student.StudentHomeView;

/**
 *
 * @author ivano
 */
public class StudentHomeController {

    private final StudentHomeView homeView;

    public StudentHomeController() {
        homeView = new StudentHomeView();
        homeView.setVisible(true);
        initView();
    }

    private void initView() {
        Student temp = (Student) Session.getInstance().get("user");
        homeView.getLblUser().setText(temp.getFirstName() + " " + temp.getLastName());
        initListeners();
    }

    private void initListeners() {
        homeView.getPanelCourseItem().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openCourseView();
            }
        });

        homeView.getPanelLogoutItem().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                logout();
            }

        });

        homeView.getPanelProfileItem().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openProfileView();
            }

        });
    }

    private void openCourseView() {
        new StudentCoursesController();
        homeView.dispose();
    }

    private void logout() {
        sendLogoutRequest();
        Student student = (Student) Session.getInstance().get("user");
        JOptionPane.showMessageDialog(homeView, "Bye " + student.getFirstName() + " " + student.getLastName() + "!");
        Session.getInstance().remove("user");
        new LoginController();
        homeView.dispose();
    }

    private void openProfileView() {
        new StudentProfileController();
        homeView.dispose();
    }

    private void sendLogoutRequest() {
        try {
            Communication.getInstance().send(new Request(Operation.LOG_OUT, null));
            Communication.getInstance().receive(); // we need it because server will send empty response
        } catch (ClassNotFoundException | IOException ex) {
            JOptionPane.showMessageDialog(homeView, "Error while logging out student!", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

}
