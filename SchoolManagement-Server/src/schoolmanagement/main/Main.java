/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package schoolmanagement.main;


import java.time.LocalDate;
import javax.swing.JFrame;
import schoolmanagement.commonlib.model.Student;
import schoolmanagement.controller.Controller;


/**
 *
 * @author ivano
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        JFrame mainForm = new MainForm();
//        mainForm.setVisible(true);

        Student s1 = new Student("djole123", "Djole123!", "Djordje", "Ivanovic", LocalDate.now(), LocalDate.now());

        Controller controller = new Controller();
        controller.createStudent(s1);
    }

}
