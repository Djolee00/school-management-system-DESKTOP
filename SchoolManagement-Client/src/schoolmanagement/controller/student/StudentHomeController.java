/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schoolmanagement.controller.student;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import schoolmanagement.commonlib.model.Student;
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

    }

    private void logout() {
        Student student = (Student) Session.getInstance().get("user");
        JOptionPane.showMessageDialog(homeView, "Bye " + student.getFirstName() + " " + student.getLastName() + "!");
        Session.getInstance().remove("user");
        new LoginController();
        homeView.dispose();
    }

    private void openProfileView() {

    }

}
