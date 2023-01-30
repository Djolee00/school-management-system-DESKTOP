/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schoolmanagement.controller.admin;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import schoolmanagement.commonlib.communication.Operation;
import schoolmanagement.commonlib.communication.Request;
import schoolmanagement.commonlib.communication.Response;
import schoolmanagement.commonlib.communication.ResponseType;
import schoolmanagement.commonlib.model.Course;
import schoolmanagement.commonlib.model.CourseGroup;
import schoolmanagement.commonlib.model.Student;
import schoolmanagement.commonlib.model.Tutor;
import schoolmanagement.communication.Communication;
import schoolmanagement.session.Session;
import schoolmanagement.view.admin.AdminGroupsView;
import schoolmanagement.view.component.table.tmodel.AdminGroupsSelectionTModel;

/**
 *
 * @author ivano
 */
public class AdminGroupsController {

    private final AdminGroupsView groupsView;
    private final Course selectedCourse;
    private final AdminGroupsSelectionTModel tableModel;
    private List<CourseGroup> courseGroups;
    private List<Tutor> languageTutors;
    private List<Student> courseStudents;

    public AdminGroupsController() {
        selectedCourse = (Course) Session.getInstance().get("course");
        groupsView = new AdminGroupsView();
        groupsView.setVisible(true);
        initView();
        tableModel = (AdminGroupsSelectionTModel) groupsView.getTblGroups().getModel();
    }

    private void initView() {
        initListeners();
        initLists();
        populateTableOfGroups();
        languageTutors = getCourseLanguageTutors();
        courseStudents = getCourseStudents();
        groupsView.getLblCourseName().setText(selectedCourse.getName());
    }

    private void initListeners() {
        groupsView.getLblHome().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new AdminCoursesController();
                groupsView.dispose();
            }
        });

        groupsView.getBtnAddGroup().addActionListener(e -> addEmptyGroup());
        groupsView.getBtnAddStudentToGroup().addActionListener(e -> addStudentInGroup());
        groupsView.getBtnRemoveStudentFromGroup().addActionListener(e -> removeStudentFromGroup());
        groupsView.getBtnAddTutorToGroup().addActionListener(e -> addTutorToGroup());
        groupsView.getBtnRemoveTutorFromGroup().addActionListener(e -> removeTutorFromGroup());
        groupsView.getBtnUpdateGroupInfo().addActionListener(e -> updateGroupInfo());
        groupsView.getBtnUpdateStudents().addActionListener(e -> updateGroupStudents());
        groupsView.getBtnUpdateTutors().addActionListener(e -> updateGroupTutors());

        groupsView.getTblGroups().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                groupSelection();
            }
        });
    }

    private void addEmptyGroup() {
        CourseGroup temp = makeDummyCourseGroup();
        courseGroups.add(temp);
        tableModel.fireTableDataChanged();
    }

    private void addStudentInGroup() {
    }

    private void removeStudentFromGroup() {
    }

    private void removeTutorFromGroup() {
    }

    private void addTutorToGroup() {
    }

    private void updateGroupInfo() {
        if (groupsView.getTblGroups().getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(groupsView, "Please select group you want to save/update", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (JOptionPane.showConfirmDialog(groupsView, "Are you sure you want to update this group?", "Confirmation", JOptionPane.YES_NO_OPTION)
                == JOptionPane.YES_OPTION) {
            CourseGroup temp = (CourseGroup) tableModel.getCourseGroup(groupsView.getTblGroups().getSelectedRow());
            if (temp.getName().equals("")) {
                JOptionPane.showMessageDialog(groupsView, "Please fill in the name of course group", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (temp.getId() != null) {

            } else {
                Long generatedId = sendSaveRequest(temp);
                temp.setId(generatedId);
                JOptionPane.showMessageDialog(groupsView, "Group's data successfully saved", "Success", JOptionPane.INFORMATION_MESSAGE);
            }

        }
    }

    private void updateGroupStudents() {
    }

    private void updateGroupTutors() {
    }

    private void populateTableOfGroups() {
        courseGroups = getGroupsOfCourse();
        groupsView.getTblGroups().setModel(new AdminGroupsSelectionTModel(courseGroups));
    }

    private List<CourseGroup> getGroupsOfCourse() {
        List<CourseGroup> temp = null;

        try {
            Communication.getInstance().send(new Request(Operation.GET_COURSE_GROUPS, selectedCourse));

            Response response = Communication.getInstance().receive();

            if (response.getResponseType() == ResponseType.FAILURE) {
                throw new IOException("Error getting groups' data");
            } else {
                temp = (List<CourseGroup>) response.getObject();
            }

        } catch (ClassNotFoundException | IOException ex) {
            JOptionPane.showMessageDialog(groupsView, "Error getting groups' data. Try again later!", "Error", JOptionPane.ERROR_MESSAGE);
            groupsView.dispose();
            System.exit(0);
        }

        return temp;
    }

    private List<Tutor> getCourseLanguageTutors() {
        List<Tutor> tempTutors = null;

        try {
            Communication.getInstance().send(new Request(Operation.GET_LANGUAGE_TUTORS, selectedCourse.getLanguage()));

            Response response = Communication.getInstance().receive();

            if (response.getResponseType() == ResponseType.FAILURE) {
                throw new IOException("Error getting languages' data");
            } else {
                tempTutors = (List<Tutor>) response.getObject();
            }

        } catch (ClassNotFoundException | IOException ex) {
            JOptionPane.showMessageDialog(groupsView, "Error getting language' data. Try again later!", "Error", JOptionPane.ERROR_MESSAGE);
            groupsView.dispose();
            System.exit(0);
        }

        return tempTutors;
    }

    private List<Student> getCourseStudents() {
        List<Student> tempStudents = null;

        try {
            Communication.getInstance().send(new Request(Operation.GET_COURSE_STUDENTS, selectedCourse));

            Response response = Communication.getInstance().receive();

            if (response.getResponseType() == ResponseType.FAILURE) {
                throw new IOException("Error getting students data");
            } else {
                tempStudents = (List<Student>) response.getObject();
            }

        } catch (ClassNotFoundException | IOException ex) {
            JOptionPane.showMessageDialog(groupsView, "Error getting students data. Try again later!", "Error", JOptionPane.ERROR_MESSAGE);
            groupsView.dispose();
            System.exit(0);
        }

        return tempStudents;
    }

    private void groupSelection() {
        int rowIndex = groupsView.getTblGroups().getSelectedRow();
        CourseGroup tempCourseGroup = tableModel.getCourseGroup(rowIndex);
        List<Tutor> availableTutors = languageTutors.stream().filter(lt -> !tempCourseGroup.getTutors().contains(lt)).collect(Collectors.toList());
        List<Student> availableStudents = getAvailableStudentsForCourseGroup();

        groupsView.getListAttendingStudents().setListData(tempCourseGroup.getStudents().toArray(new Student[0]));
        groupsView.getListDelegatedTutors().setListData(tempCourseGroup.getTutors().toArray(new Tutor[0]));
        groupsView.getListAvailableTutors().setListData(availableTutors.toArray(new Tutor[0]));
        groupsView.getListAvailableStudents().setListData(availableStudents.toArray(new Student[0]));

        groupsView.getListAvailableStudents().updateUI();
        groupsView.getListAvailableTutors().updateUI();
        groupsView.getListAttendingStudents().updateUI();
        groupsView.getListDelegatedTutors().updateUI();
    }

    private void initLists() {
        groupsView.getListAvailableStudents().setModel(new DefaultComboBoxModel<>());
        groupsView.getListAvailableTutors().setModel(new DefaultComboBoxModel<>());
        groupsView.getListAttendingStudents().setModel(new DefaultComboBoxModel<>());
        groupsView.getListDelegatedTutors().setModel(new DefaultComboBoxModel<>());
    }

    private List<Student> getAvailableStudentsForCourseGroup() {
        List<Student> students = new ArrayList<>();
        for (Student student : courseStudents) {
            boolean status = true;
            for (CourseGroup courseGroup : courseGroups) {
                if (courseGroup.getStudents().contains(student)) {
                    status = false;
                    break;
                }
            }
            if (status == true) {
                students.add(student);
            }
        }
        return students;
    }

    private CourseGroup makeDummyCourseGroup() {
        CourseGroup temp = new CourseGroup();
        temp.setId(null);
        temp.setCourse(selectedCourse);
        temp.setName("");
        temp.setNumOfStudents(0);
        temp.setStudents(new ArrayList<>());
        temp.setTutors(new ArrayList<>());
        return temp;
    }

    private Long sendSaveRequest(CourseGroup temp) {
         try {
            Communication.getInstance().send(new Request(Operation.ADD_COURSE_GROUP, temp));

            Response response = Communication.getInstance().receive();

            if (response.getResponseType() == ResponseType.FAILURE) {
                throw new IOException("Course group couldn't be saved");
            }

            return (Long) response.getObject();
        } catch (ClassNotFoundException | IOException ex) {
            JOptionPane.showMessageDialog(groupsView, "Error adding group's data. Try again later!", "Error", JOptionPane.ERROR_MESSAGE);
            groupsView.dispose();
            System.exit(0);
            return null;
        }
    }

}
