/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schoolmanagement.commonlib.model;

import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author ivano
 */
public class Course {
    
    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer groupCapacity;
    private Language language;
    private List<CourseEnrollment> courseEnrollments;
    private List<CourseGroup> courseGroups;

    public Course(String name, LocalDate startDate, LocalDate endDate, Integer groupCapacity, Language language) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.groupCapacity = groupCapacity;
        this.language = language;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Integer getGroupCapacity() {
        return groupCapacity;
    }

    public void setGroupCapacity(Integer groupCapacity) {
        this.groupCapacity = groupCapacity;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public List<CourseEnrollment> getCourseEnrollments() {
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
        return "Course{" + "id=" + id + ", name=" + name + ", startDate=" + startDate + ", endDate=" + endDate + ", groupCapacity=" + groupCapacity + ", language=" + language + '}';
    }
    
    
    
}
