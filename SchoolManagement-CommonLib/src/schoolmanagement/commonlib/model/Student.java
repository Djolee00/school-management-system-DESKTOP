/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schoolmanagement.commonlib.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author ivano
 */
public class Student extends User implements Serializable {

    private String firstName;
    private String lastName;
    private LocalDate birthdate;
    private LocalDate creationDate;
    private List<CourseEnrollment> courseEnrollments;
    private List<CourseGroup> courseGroups;

    public Student(Long id, String username, String password, String firstName, String lastName, LocalDate birthdate, LocalDate creationDate) {
        super(id, username, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.creationDate = creationDate;
    }

    public Student(String username, String password) {
        super(username, password);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public List<CourseEnrollment> getCoursesEnrollments() {
        return courseEnrollments;
    }

    public void setCourseEnrollments(List<CourseEnrollment> courseEnrollments) {
        this.courseEnrollments = courseEnrollments;
    }

    public List<CourseGroup> getCourseGroups() {
        return courseGroups;
    }

    public void setCourseGroups(List<CourseGroup> courseGroups) {
        this.courseGroups = courseGroups;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Student other = (Student) obj;
        return Objects.equals(this.id, other.id);
    }
    
    
    

}
