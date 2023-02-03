/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schoolmanagement.controller.admin;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.swing.JOptionPane;
import schoolmanagement.commonlib.communication.Operation;
import schoolmanagement.commonlib.communication.Request;
import schoolmanagement.communication.Communication;
import schoolmanagement.controller.login.LoginController;
import schoolmanagement.session.Session;
import schoolmanagement.view.admin.AdminHomeView;

/**
 *
 * @author ivano
 */
public class AdminHomeController {

    private final AdminHomeView homeView;

    public AdminHomeController() {
        homeView = new AdminHomeView();
        homeView.setVisible(true);
        initListeners();
    }

    private void initListeners() {
        homeView.getPanelLogoutItem().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                logout();
            }
        });

        homeView.getPanelStudentsItem().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openStudentsView();
            }
        });

        homeView.getPanelCourseItem().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openCoursesView();
            }
        });

    }

    private void logout() {
        sendLogoutRequest();
        JOptionPane.showMessageDialog(homeView, "Bye Admin!");
        Session.getInstance().remove("user");
        new LoginController();
        homeView.dispose();
    }

    private void openStudentsView() {
        new AdminStudentsListController();
        homeView.dispose();
    }

    private void openCoursesView() {
        new AdminCoursesController();
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
