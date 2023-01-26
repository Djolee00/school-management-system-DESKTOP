/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schoolmanagement.controller.admin;

import java.awt.PopupMenu;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import schoolmanagement.commonlib.model.Course;
import schoolmanagement.commonlib.model.CourseGroup;
import schoolmanagement.session.Session;
import schoolmanagement.view.admin.AdminGroupsView;
import schoolmanagement.view.component.table.tmodel.AdminGroupsSelectionTModel;

/**
 *
 * @author ivano
 */
public class AdminGroupsController {
    
    private final AdminGroupsView groupsView;
    private Course selectedCourse;
    private List<CourseGroup> courseGroups;
    
    public AdminGroupsController() {
        selectedCourse = (Course) Session.getInstance().get("course");
        groupsView = new AdminGroupsView();
        groupsView.setVisible(true);
        initView();
    }
    
    private void initView() {
        initListeners();
        populateTableOfGroups();
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
        return null;
    }
}
