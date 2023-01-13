/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schoolmanagement.controller.student;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.ZoneId;
import java.util.Date;
import schoolmanagement.commonlib.model.Student;
import schoolmanagement.session.Session;
import schoolmanagement.view.student.StudentProfileView;

/**
 *
 * @author ivano
 */
public class StudentProfileController {

    private final StudentProfileView profileView;
    private final Student student;

    public StudentProfileController() {
        student = (Student) Session.getInstance().get("user");
        profileView = new StudentProfileView();
        profileView.setVisible(true);
        initView();
    }

    private void initView() {
        initListeners();
        populateFields();
    }

    private void initListeners() {
        profileView.getLblHome().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new StudentHomeController();
                profileView.dispose();
            }
        });
    }

    private void populateFields() {
        profileView.getTxtFirstname().setText(student.getFirstName());
        profileView.getTxtLastname().setText(student.getLastName());
        profileView.getTxtBirthDate().setValue(Date.from(student.getBirthdate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        profileView.getTxtCreationDate().setValue(Date.from(student.getCreationDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        profileView.getTxtUsername().setText(student.getUsername());
        profileView.getTxtPassword().setText(student.getPassword());
    }
}
