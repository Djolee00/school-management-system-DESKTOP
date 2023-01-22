/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schoolmanagement.controller.admin;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
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
import schoolmanagement.commonlib.model.Language;
import schoolmanagement.commonlib.model.enums.Level;
import schoolmanagement.communication.Communication;
import schoolmanagement.view.admin.AdminCoursesView;
import schoolmanagement.view.component.AdminCourseSelectionTModel;
import schoolmanagement.view.component.StudentCourseSelectionTModel;

/**
 *
 * @author ivano
 */
public class AdminCoursesController {

    private final AdminCoursesView coursesView;
    private final AdminCourseSelectionTModel tableModel;
    private List<Course> courses;
    private List<Course> backupCourses;

    public AdminCoursesController() {
        coursesView = new AdminCoursesView();
        coursesView.setVisible(true);
        initView();
        tableModel = (AdminCourseSelectionTModel) coursesView.getTblCourses().getModel();
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
                new AdminHomeController();
                coursesView.dispose();
            }
        });

        coursesView.getBtnAddCourse().addActionListener(e -> addEmptyCourse());
        coursesView.getBtnDeleteCourse().addActionListener(e -> deleteSelectedCourse());
        coursesView.getBtnUpdateCourse().addActionListener(e -> updateSelectedCourse());
        coursesView.getBtnSort().addActionListener(e -> sortCourses());
        coursesView.getBtnFilter().addActionListener(e -> filterCourses());
        coursesView.getBtnReset().addActionListener(e -> resetCourses());
        coursesView.getBtnManageGroups().addActionListener(e -> manageGroupsForSelectedCourse());
    }

    private void addEmptyCourse() {
    }

    private void deleteSelectedCourse() {
    }

    private void updateSelectedCourse() {
    }

    private void sortCourses() {
        if (coursesView.getJrbLevel().isSelected()) {
            courses = courses.stream().sorted((c1, c2) -> c1.getLanguage().getLevel().toString().compareTo(c2.getLanguage().getLevel().toString())).collect(Collectors.toList());
        }
        if (coursesView.getJrbLanguage().isSelected()) {
            courses = courses.stream().sorted((c1, c2) -> c1.getLanguage().getName().compareTo(c2.getLanguage().getName())).collect(Collectors.toList());
        }
        if (coursesView.getJrbBeginDate().isSelected()) {
            courses = courses.stream().sorted(Comparator.comparing(Course::getStartDate)).collect(Collectors.toList());
        }
        if (coursesView.getJrbEndDate().isSelected()) {
            courses = courses.stream().sorted(Comparator.comparing(Course::getEndDate)).collect(Collectors.toList());
        }

        tableModel.setCourses(courses);
    }

    private void filterCourses() {
        List<Course> temp = this.backupCourses;
        if (coursesView.getJcbLanguage().getSelectedIndex() != -1) {
            Language language = (Language) coursesView.getJcbLanguage().getSelectedItem();
            temp = temp.stream().filter(c -> c.getLanguage().equals(language)).collect(Collectors.toList());
        }
        if (coursesView.getJcbLevel().getSelectedIndex() != -1) {
            Level level = (Level) coursesView.getJcbLevel().getSelectedItem();
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
        tableModel.setCourses(temp);
    }

    private void resetCourses() {
        coursesView.getJcbLanguage().setSelectedIndex(-1);
        coursesView.getJcbLevel().setSelectedIndex(-1);
        coursesView.getDateBegin().setDate(null);
        coursesView.getDateEnd().setDate(null);
        courses = backupCourses;
        tableModel.setCourses(backupCourses);
    }

    private void manageGroupsForSelectedCourse() {
    }

    private void populateTable() {
        courses = getAllCourses();
        backupCourses = courses;
        coursesView.getTblCourses().setModel(new AdminCourseSelectionTModel(courses));
    }

    private void initLanguages() {
        List<Language> languages = getAllLanguages();
        coursesView.getJcbLanguage().setModel(new DefaultComboBoxModel(languages.toArray()));
        coursesView.getJcbLanguage().setSelectedIndex(-1);
    }

    private void initLevels() {
        coursesView.getJcbLevel().setModel(new DefaultComboBoxModel(Level.values()));
        coursesView.getJcbLevel().setSelectedIndex(-1);
    }

    private List<Language> getAllLanguages() {
        List<Language> tempLanguages = null;

        try {
            Communication.getInstance().send(new Request(Operation.GET_ALL_LANGUAGES, null));

            Response response = Communication.getInstance().receive();

            if (response.getResponseType() == ResponseType.SUCCESS) {
                tempLanguages = (List<Language>) response.getObject();
            } else {
                throw new IOException("Error getting languages' data");
            }

        } catch (ClassNotFoundException | IOException ex) {
            JOptionPane.showMessageDialog(coursesView, "Error getting languages' data. Try again later!", "Error", JOptionPane.ERROR_MESSAGE);
            coursesView.dispose();
            System.exit(0);
        }

        return tempLanguages;
    }

    private List<Course> getAllCourses() {
        List<Course> temp = null;

        try {
            Communication.getInstance().send(new Request(Operation.GET_ALL_COURSES, null));

            Response response = Communication.getInstance().receive();

            if (response.getResponseType() == ResponseType.FAILURE) {
                throw new IOException("Error getting courses' data");
            } else {
                temp = (List<Course>) response.getObject();
            }

        } catch (ClassNotFoundException | IOException ex) {
            JOptionPane.showMessageDialog(coursesView, "Error getting courses' data. Try again later!", "Error", JOptionPane.ERROR_MESSAGE);
            coursesView.dispose();
            System.exit(0);
        }

        return temp;
    }

}
