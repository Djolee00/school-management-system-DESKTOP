/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schoolmanagement.controller.student;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import schoolmanagement.commonlib.communication.Operation;
import schoolmanagement.commonlib.communication.Request;
import schoolmanagement.commonlib.communication.Response;
import schoolmanagement.commonlib.communication.ResponseType;
import schoolmanagement.commonlib.model.Course;
import schoolmanagement.commonlib.model.CourseEnrollment;
import schoolmanagement.commonlib.model.Language;
import schoolmanagement.commonlib.model.Student;
import schoolmanagement.commonlib.model.enums.Level;
import schoolmanagement.communication.Communication;
import schoolmanagement.session.Session;
import schoolmanagement.view.component.StudentCourseSelectionModel;
import schoolmanagement.view.student.StudentCoursesView;

/**
 *
 * @author ivano
 */
public class StudentCoursesController {

    private final StudentCoursesView coursesView;
    private final StudentCourseSelectionModel model;
    private List<Course> courses;
    private List<Course> backupCourses;
    private final Student student;

    public StudentCoursesController() {
        student = (Student) Session.getInstance().get("user");
        coursesView = new StudentCoursesView();
        coursesView.setVisible(true);
        initView();
        model = (StudentCourseSelectionModel) coursesView.getTblCourses().getModel();
    }

    private void initView() {
        initListeners();
        populateTable();
        initLanguages();
        initLevels();
    }

    private void initListeners() {
        coursesView.getLblHome().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new StudentHomeController();
                coursesView.dispose();
            }
        });

        coursesView.getBtnSort().addActionListener(e -> sortCourses());
        coursesView.getBtnSearch().addActionListener(e -> filterCourses());
        coursesView.getBtnChoose().addActionListener(e -> chooseCourses());
        coursesView.getBtnReset().addActionListener(e -> resetCourses());

    }

    private void sortCourses() {
        if (coursesView.getJrbLevel().isSelected()) {
            courses = courses.stream().sorted((c1, c2) -> c1.getLanguage().getLevel().toString().compareTo(c2.getLanguage().getLevel().toString())).collect(Collectors.toList());
        }
        if (coursesView.getJrbLanguage().isSelected()) {
            courses = courses.stream().sorted((c1, c2) -> c1.getLanguage().getName().compareTo(c2.getLanguage().getName())).collect(Collectors.toList());
        }
        if (coursesView.getJrbStartDate().isSelected()) {
            courses = courses.stream().sorted(Comparator.comparing(Course::getStartDate)).collect(Collectors.toList());
        }
        if (coursesView.getJrbEndDate().isSelected()) {
            courses = courses.stream().sorted(Comparator.comparing(Course::getEndDate)).collect(Collectors.toList());
        }

        model.setCourses(courses);
    }

    private void filterCourses() {
        List<Course> temp = this.backupCourses;
        if (coursesView.getjComboBoxLanguage().getSelectedIndex() != -1) {
            Language language = (Language) coursesView.getjComboBoxLanguage().getSelectedItem();
            temp = temp.stream().filter(c -> c.getLanguage().equals(language)).collect(Collectors.toList());
        }
        if (coursesView.getjComboBoxLevel().getSelectedIndex() != -1) {
            Level level = (Level) coursesView.getjComboBoxLevel().getSelectedItem();
            temp = temp.stream().filter(c -> c.getLanguage().getLevel() == level).collect(Collectors.toList());
        }
        if (coursesView.getDateBegin().getDate() != null) {
            LocalDate startDate = coursesView.getDateBegin().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            temp = temp.stream().filter(c -> c.getStartDate().isAfter(startDate) || c.getStartDate().isEqual(startDate)).collect(Collectors.toList());
        }

        if (coursesView.getDateEnd().getDate() != null) {
            LocalDate endDate = coursesView.getDateEnd().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            temp = temp.stream().filter(c -> c.getEndDate().isBefore(endDate) || c.getEndDate().isEqual(endDate)).collect(Collectors.toList());
        }

        courses = temp;
        model.setCourses(temp);
    }

    private void chooseCourses() {
        int[] selection = coursesView.getTblCourses().getSelectedRows();
        if (coursesView.getTblCourses().getSelectedRows().length == 0) {
            JOptionPane.showMessageDialog(coursesView, "Please select one or more courses.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (JOptionPane.showConfirmDialog(coursesView, "Are you sure you want to enroll in these courses?", "Confirmation", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            List<CourseEnrollment> selectedCourses = new ArrayList<>();
            for (int i = 0; i < selection.length; i++) {
                Course temp = courses.get(selection[i]);
                selectedCourses.add(new CourseEnrollment(student, temp, LocalDate.now()));
            }

            boolean status = enrollStudentToCourses(selectedCourses);

            if (status == true) {
                JOptionPane.showMessageDialog(coursesView, "You have been successfully added to selected courses!", "Success", JOptionPane.INFORMATION_MESSAGE);
                new StudentHomeController();
            } else {
                JOptionPane.showMessageDialog(coursesView, "Error while enrolling student in selected courses. Please try again!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            coursesView.dispose();

        }
    }

    private void resetCourses() {
        coursesView.getjComboBoxLanguage().setSelectedIndex(-1);
        coursesView.getjComboBoxLevel().setSelectedIndex(-1);
        coursesView.getDateBegin().setDate(null);
        coursesView.getDateEnd().setDate(null);
        courses = backupCourses;
        model.setCourses(backupCourses);
    }

    private void populateTable() {
        courses = getStudentsUnselectedCourses();
        backupCourses = courses;
        coursesView.getTblCourses().setModel(new StudentCourseSelectionModel(courses));
    }

    private List<Course> getStudentsUnselectedCourses() {
        List<Course> tempCourses = null;

        try {
            Communication.getInstance().send(new Request(Operation.GET_STUDENT_UNSELECTED_COURSES, student));

            Response response = Communication.getInstance().receive();

            if (response.getResponseType() == ResponseType.SUCCESS) {
                tempCourses = (List<Course>) response.getObject();
            } else {
                JOptionPane.showMessageDialog(coursesView, "Error getting student's courses. Try again later!", "Error", JOptionPane.ERROR_MESSAGE);
                coursesView.dispose();
                System.exit(0);
            }

        } catch (ClassNotFoundException | IOException ex) {
            JOptionPane.showMessageDialog(coursesView, "Error getting student's courses. Try again later!", "Error", JOptionPane.ERROR_MESSAGE);
            coursesView.dispose();
            System.exit(0);
        }

        return tempCourses;
    }

    private void initLanguages() {
        List<Language> languages = new ArrayList<>();
        for (Course course : courses) {
            if (!languages.contains(course.getLanguage())) {
                languages.add(course.getLanguage());
            }
        }

        coursesView.getjComboBoxLanguage().setModel(new DefaultComboBoxModel(languages.toArray()));
        coursesView.getjComboBoxLanguage().setSelectedIndex(-1);
    }

    private void initLevels() {
        coursesView.getjComboBoxLevel().setModel(new DefaultComboBoxModel(Level.values()));
        coursesView.getjComboBoxLevel().setSelectedIndex(-1);
    }

    private boolean enrollStudentToCourses(List<CourseEnrollment> selectedCourses) {

        try {
            Communication.getInstance().send(new Request(Operation.ENROLL_STUDENT_IN_COURSES, selectedCourses));

            Response response = Communication.getInstance().receive();

            return response.getResponseType() == ResponseType.SUCCESS;

        } catch (ClassNotFoundException | IOException ex) {
            JOptionPane.showMessageDialog(coursesView, "Error while enrolling student in selected courses. Please try again!", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
            return false;
        }

    }

}
