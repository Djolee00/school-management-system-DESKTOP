/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schoolmanagement.commonlib.model;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author ivano
 */
public class CourseGroup implements Serializable{
    
    private Long id;
    private Course course;
    private String name;
    private Integer numOfStudents;
    private List<Tutor> tutors;
    private List<Student> students;

    public CourseGroup(Course course, String name, Integer numOfStudents, List<Tutor> tutors, List<Student> students) {
        this.course = course;
        this.name = name;
        this.numOfStudents = numOfStudents;
        this.tutors = tutors;
        this.students = students;
    }

    public CourseGroup() {
    }
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumOfStudents() {
        return numOfStudents;
    }

    public void setNumOfStudents(Integer numOfStudents) {
        this.numOfStudents = numOfStudents;
    }

    public List<Tutor> getTutors() {
        return tutors;
    }

    public void setTutors(List<Tutor> tutors) {
        this.tutors = tutors;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "CourseGroup{" + "id=" + id + ", course=" + course + ", name=" + name + ", numOfStudents=" + numOfStudents + '}';
    }
    
    
    
    
}
