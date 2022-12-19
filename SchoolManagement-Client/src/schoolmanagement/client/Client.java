/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package schoolmanagement.client;

import javax.swing.JFrame;
import schoolmanagement.view.login.LoginView;
import schoolmanagement.view.student.StudentHomeView;

/**
 *
 * @author ivano
 */
public class Client {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame mainFrame = new StudentHomeView();
        mainFrame.setVisible(true);
        
        JFrame loginFrame = new LoginView();
        loginFrame.setVisible(true);
    }
    
}
