/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schoolmanagement.controller.student;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import schoolmanagement.commonlib.communication.Operation;
import schoolmanagement.commonlib.communication.Request;
import schoolmanagement.commonlib.communication.Response;
import schoolmanagement.commonlib.communication.ResponseType;
import schoolmanagement.commonlib.model.CourseEnrollment;
import schoolmanagement.commonlib.model.CourseGroup;
import schoolmanagement.commonlib.model.Student;
import schoolmanagement.commonlib.model.Tutor;
import schoolmanagement.communication.Communication;
import schoolmanagement.session.Session;
import schoolmanagement.view.component.table.tmodel.StudentProfileMyCoursesTModel;
import schoolmanagement.view.student.StudentProfileView;

/**
 *
 * @author ivano
 */
public class StudentProfileController {

    private final StudentProfileView profileView;
    private final StudentProfileMyCoursesTModel model;
    private final Student student;

    public StudentProfileController() {
        student = (Student) Session.getInstance().get("user");
        profileView = new StudentProfileView();
        profileView.setVisible(true);
        initView();
        model = (StudentProfileMyCoursesTModel) profileView.getTblCourses().getModel();
    }

    private void initView() {
        initListeners();
        populateFields();
        populateTable();
    }

    private void initListeners() {
        profileView.getLblHome().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new StudentHomeController();
                profileView.dispose();
            }
        });

        profileView.getTblCourses().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                courseSelection();
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

        profileView.getLblMessage().setVisible(false);
    }

    private void populateTable() {
        List<CourseEnrollment> courses = getStudentCourses();
        profileView.getTblCourses().setModel(new StudentProfileMyCoursesTModel(courses));
    }

    private List<CourseEnrollment> getStudentCourses() {
        List<CourseEnrollment> courseEnrollments = null;

        try {
            Communication.getInstance().send(new Request(Operation.GET_STUDENT_COURSES, student));

            Response response = Communication.getInstance().receive();

            if (response.getResponseType() == ResponseType.SUCCESS) {
                courseEnrollments = (List<CourseEnrollment>) response.getObject();
            }

        } catch (ClassNotFoundException | IOException ex) {
            JOptionPane.showMessageDialog(profileView, "Error getting student's courses. Try again later!", "Error", JOptionPane.ERROR_MESSAGE);
            profileView.dispose();
        }

        return courseEnrollments;
    }

    private void courseSelection() {
        int rowIndex = profileView.getTblCourses().getSelectedRow();
        CourseEnrollment tempCourseEnrollment = model.getCourseEnrollment(rowIndex);
        CourseGroup tempGroup = tempCourseEnrollment.getCourse().getCourseGroups().get(0); //we only have group in which student exist (if he is added in any group)

        if (tempGroup != null) {
            profileView.setRowGreen();
            profileView.getLblMessage().setVisible(false);
            profileView.getTxtName().setText(tempGroup.getName());
            for (Tutor temp : tempGroup.getTutors()) {
                profileView.getTxtTutors().setText(profileView.getTxtTutors().getText() + temp.getFirstName() + " " + temp.getLastName() + "\n");
            }
            profileView.getTxtNumOfStudents().setText("" + tempGroup.getNumOfStudents());
        } else {
            profileView.setRowRed();
            profileView.getLblMessage().setVisible(true);
            profileView.getTxtName().setText("");
            profileView.getTxtTutors().setText("");
            profileView.getTxtNumOfStudents().setText("");
        }
    }

}
