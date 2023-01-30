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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.TableColumn;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import schoolmanagement.commonlib.communication.Operation;
import schoolmanagement.commonlib.communication.Request;
import schoolmanagement.commonlib.communication.Response;
import schoolmanagement.commonlib.communication.ResponseType;
import schoolmanagement.commonlib.model.Course;
import schoolmanagement.commonlib.model.Language;
import schoolmanagement.commonlib.model.enums.Level;
import schoolmanagement.communication.Communication;
import schoolmanagement.session.Session;
import schoolmanagement.view.admin.AdminCoursesView;
import schoolmanagement.view.component.table.celleditor.JDateChooserCellEditor;
import schoolmanagement.view.component.table.tmodel.AdminCourseSelectionTModel;

/**
 *
 * @author ivano
 */
public class AdminCoursesController {

    private final AdminCoursesView coursesView;
    private final AdminCourseSelectionTModel tableModel;
    private List<Course> courses;
    private List<Course> backupCourses;
    private List<Language> languages;

    public AdminCoursesController() {
        coursesView = new AdminCoursesView();
        coursesView.setVisible(true);
        initView();
        tableModel = (AdminCourseSelectionTModel) coursesView.getTblCourses().getModel();
    }

    private void initView() {
        initListeners();
        initLanguages();
        initLevels();
        populateTable();
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
        Course temp = makeDummyCourse();
        backupCourses.add(temp);
        resetCourses();
        JOptionPane.showMessageDialog(coursesView, "Please fill in information and click update button", "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    private void deleteSelectedCourse() {
        if (coursesView.getTblCourses().getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(coursesView, "Please select course you want to delete", "Message", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (JOptionPane.showConfirmDialog(coursesView, "Are you sure you want to delete this course?", "Confirmation", JOptionPane.YES_NO_OPTION)
                == JOptionPane.YES_OPTION) {
            Course temp = (Course) tableModel.getCourse(coursesView.getTblCourses().getSelectedRow());
            if (temp.getId() != null) {
                boolean status = sendDeleteRequest(temp);
                if (status == false) {
                    JOptionPane.showMessageDialog(coursesView, "There are active groups, this course can't be deleted", "Success", JOptionPane.WARNING_MESSAGE);
                    return;
                } else {
                    courses.remove(temp);
                    backupCourses.remove(temp);
                    tableModel.setCourses(courses);
                }
            } else {
                courses.remove(temp);
                backupCourses.remove(temp);
                tableModel.setCourses(courses);
            }
            JOptionPane.showMessageDialog(coursesView, "Course's data successfully deleted", "Success", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    private void updateSelectedCourse() {
        if (coursesView.getTblCourses().getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(coursesView, "Please select course you want to update", "Message", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (JOptionPane.showConfirmDialog(coursesView, "Are you sure you want to update this course?", "Confirmation", JOptionPane.YES_NO_OPTION)
                == JOptionPane.YES_OPTION) {
            Course temp = (Course) tableModel.getCourse(coursesView.getTblCourses().getSelectedRow());
            if (temp.getId() != null) {
                sendUpdateRequest(temp);
                JOptionPane.showMessageDialog(coursesView, "Course's data successfully updated", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                Long generatedId = sendSaveRequest(temp);
                temp.setId(generatedId);
                JOptionPane.showMessageDialog(coursesView, "Course's data successfully saved", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        }
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
        if (coursesView.getTblCourses().getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(coursesView, "Please select course, whose groups you want to manage", "Message", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Course temp = (Course) tableModel.getCourse(coursesView.getTblCourses().getSelectedRow());
        if (temp.getId() == null) {
            JOptionPane.showMessageDialog(coursesView, "Please select course, click Update course firt", "Message", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Session.getInstance().add("course", temp);
        new AdminGroupsController();
        coursesView.dispose();
    }

    private void populateTable() {
        courses = getAllCourses();
        backupCourses = courses;
        coursesView.getTblCourses().setModel(new AdminCourseSelectionTModel(courses));
        prepareLanguageColumn();
        prepareDateColumns();
        prepareCapacityColumn();
    }

    private void initLanguages() {
        getAllLanguages();
        coursesView.getJcbLanguage().setModel(new DefaultComboBoxModel(languages.toArray()));
        coursesView.getJcbLanguage().setSelectedIndex(-1);
    }

    private void initLevels() {
        coursesView.getJcbLevel().setModel(new DefaultComboBoxModel(Level.values()));
        coursesView.getJcbLevel().setSelectedIndex(-1);
    }

    private void getAllLanguages() {

        try {
            Communication.getInstance().send(new Request(Operation.GET_ALL_LANGUAGES, null));

            Response response = Communication.getInstance().receive();

            if (response.getResponseType() == ResponseType.SUCCESS) {
                languages = (List<Language>) response.getObject();
            } else {
                throw new IOException("Error getting languages' data");
            }

        } catch (ClassNotFoundException | IOException ex) {
            JOptionPane.showMessageDialog(coursesView, "Error getting languages' data. Try again later!", "Error", JOptionPane.ERROR_MESSAGE);
            coursesView.dispose();
            System.exit(0);
        }

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

    private void prepareLanguageColumn() {
        JComboBox comboBox = new JComboBox();
        TableColumn languageColumn = coursesView.getTblCourses().getColumn("Language");
        comboBox.setModel(new DefaultComboBoxModel(languages.toArray()));
        languageColumn.setCellEditor(new DefaultCellEditor(comboBox));
    }

    private void prepareDateColumns() {
        coursesView.getTblCourses().getColumnModel().getColumn(1).setCellEditor(new JDateChooserCellEditor(new JCheckBox()));
        coursesView.getTblCourses().getColumnModel().getColumn(2).setCellEditor(new JDateChooserCellEditor(new JCheckBox()));
    }

    private void prepareCapacityColumn() {
        coursesView.getTblCourses().getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(createTextFieldForCapacityCell()));
    }

    private JTextField createTextFieldForCapacityCell() {
        // text field for capacity sell, to allow numbers from 0
        JTextField field = new JTextField();
        ((AbstractDocument) field.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int off, String str, AttributeSet attr)
                    throws BadLocationException {
                fb.insertString(off, str.replaceAll("\\D++", ""), attr);  // remove non-digits
            }

            @Override
            public void replace(FilterBypass fb, int off, int len, String str, AttributeSet attr)
                    throws BadLocationException {
                fb.replace(off, len, str.replaceAll("\\D++", ""), attr);  // remove non-digits
            }
        });
        return field;
    }

    private Course makeDummyCourse() {
        // creating dummy course
        Course temp = new Course();
        temp.setId(null);
        temp.setCourseEnrollments(new ArrayList<>());
        temp.setCourseGroups(new ArrayList<>());
        temp.setStartDate(LocalDate.now().minusDays(1l));
        temp.setEndDate(LocalDate.now());
        temp.setGroupCapacity(0);
        temp.setLanguage(languages.get(0));
        temp.setName("");
        return temp;
    }

    private void sendUpdateRequest(Course temp) {
        try {
            Communication.getInstance().send(new Request(Operation.UPDATE_COURSE, temp));

            Response response = Communication.getInstance().receive();

            if (response.getResponseType() == ResponseType.FAILURE) {
                throw new IOException("Course update failed");
            }

        } catch (ClassNotFoundException | IOException ex) {
            JOptionPane.showMessageDialog(coursesView, "Error updating course's data. Try again later!", "Error", JOptionPane.ERROR_MESSAGE);
            coursesView.dispose();
            System.exit(0);
        }
    }

    private boolean sendDeleteRequest(Course temp) {
        try {
            Communication.getInstance().send(new Request(Operation.DELETE_COURSE, temp));

            Response response = Communication.getInstance().receive();

            if (response.getResponseType() == ResponseType.FAILURE) {
                if (response.getObject() != null) {
                    throw new IOException("Error deleting course's data");
                } else {
                    return false;
                }
            }
            return true;
        } catch (ClassNotFoundException | IOException ex) {
            JOptionPane.showMessageDialog(coursesView, "Error deleting course's data. Try again later!", "Error", JOptionPane.ERROR_MESSAGE);
            coursesView.dispose();
            System.exit(0);
            return false;
        }
    }

    private Long sendSaveRequest(Course temp) {
        try {
            Communication.getInstance().send(new Request(Operation.ADD_COURSE, temp));

            Response response = Communication.getInstance().receive();

            if (response.getResponseType() == ResponseType.FAILURE) {
                throw new IOException("Course couldn't be saved");
            }

            return (Long) response.getObject();
        } catch (ClassNotFoundException | IOException ex) {
            JOptionPane.showMessageDialog(coursesView, "Error adding course's data. Try again later!", "Error", JOptionPane.ERROR_MESSAGE);
            coursesView.dispose();
            System.exit(0);
            return null;
        }
    }

}
