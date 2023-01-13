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
    private Tutor tutor;
    private List<Student> students;

    public CourseGroup(Course course, String name, Integer numOfStudents, Tutor tutor, List<Student> students) {
        this.course = course;
        this.name = name;
        this.numOfStudents = numOfStudents;
        this.tutor = tutor;
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

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
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
