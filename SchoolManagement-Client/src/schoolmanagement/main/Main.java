/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package schoolmanagement.main;

import com.formdev.flatlaf.FlatLightLaf;
import schoolmanagement.controller.login.LoginController;


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
