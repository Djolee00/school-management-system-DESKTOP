/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package schoolmanagement.main;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import schoolmanagement.commonlib.model.Student;
import schoolmanagement.persistence.factory.dao.StudentDao;
import schoolmanagement.persistence.factory.dao.impl.StudentDaoImpl;

/**
 *
 * @author ivano
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            //        JFrame mainForm = new MainForm();
//        mainForm.setVisible(true);

            StudentDao studentDao = new StudentDaoImpl();
            Student s1 = new Student("djole132", "djole123", "Djordje", "Ivanovic", LocalDate.now(), LocalDate.now());

            s1 = studentDao.saveStudent(s1);
            System.out.println(s1);
        } catch (SQLException | IOException ex) {
            System.out.println("Neuspesno");
        }
    }

}
