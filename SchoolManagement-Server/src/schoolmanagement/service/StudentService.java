/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package schoolmanagement.service;

import java.io.IOException;
import java.util.List;
import schoolmanagement.commonlib.model.Student;
import validation.exception.ValidationException;
import java.sql.SQLException;
import schoolmanagement.commonlib.model.Course;
import schoolmanagement.commonlib.model.CourseEnrollment;
import schoolmanagement.validator.student.StudentValidator;

/**
 *
 * @author ivano
 */
public interface StudentService {
    
    public Student saveStudent(Student student,StudentValidator validator) throws ValidationException,IOException,SQLException;
    
    public List<CourseEnrollment> getStudentCourses(Long studentId) throws IOException,SQLException;

    public List<Course> getStudentUnselectedCourses(Long studentId) throws IOException,SQLException;

    public boolean enrollCourses(List<CourseEnrollment> selectedCourses) throws IOException,SQLException;

    public List<Student> getAllStudents() throws IOException,SQLException;

    public boolean updateStudent(Student student,StudentValidator validator) throws ValidationException,IOException,SQLException;
}
