/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schoolmanagement.controller.admin;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
