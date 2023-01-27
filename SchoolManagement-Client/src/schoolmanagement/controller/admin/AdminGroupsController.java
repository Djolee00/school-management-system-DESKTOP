/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schoolmanagement.controller.admin;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
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
    private List<Student> groupStudents;
    private List<Tutor> groupTutors;

    public AdminGroupsController() {
        selectedCourse = (Course) Session.getInstance().get("course");
        groupsView = new AdminGroupsView();
        groupsView.setVisible(true);
        initView();
        tableModel = (AdminGroupsSelectionTModel) groupsView.getTblGroups().getModel();
    }

    private void initView() {
        initListeners();
        populateTableOfGroups();
        initLists();
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

    private void groupSelection() {
        int rowIndex = groupsView.getTblGroups().getSelectedRow();
        CourseGroup tempCourseGroup = tableModel.getCourseGroup(rowIndex);

        groupsView.getListAttendingStudents().setListData(tempCourseGroup.getStudents().toArray(new Student[0]));
        groupsView.getListDeleagatedTutors().setListData(tempCourseGroup.getTutors().toArray(new Tutor[0]));

        groupsView.getListAttendingStudents().updateUI();
        groupsView.getListDeleagatedTutors().updateUI();
    }

    private void initLists() {
        groupsView.getListAttendingStudents().setModel(new DefaultComboBoxModel<>());
        groupsView.getListDeleagatedTutors().setModel(new DefaultComboBoxModel<>());
    }
}
