/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schoolmanagement.commonlib.communication;

import java.io.Serializable;

/**
 *
 * @author ivano
 */
public enum Operation implements Serializable {
    LOGIN,
    GET_STUDENT_COURSES,
    GET_STUDENT_UNSELECTED_COURSES,
    ENROLL_STUDENT_IN_COURSES,
    GET_ALL_STUDENTS,
    GET_ALL_COURSES,
    GET_ALL_LANGUAGES,
    UPDATE_STUDENT_PERSONAL_INFO,
    ADD_NEW_STUDENT,
    UPDATE_COURSE,
    DELETE_COURSE,
    ADD_COURSE,
    GET_COURSE_GROUPS,
    GET_LANGUAGE_TUTORS,
    GET_COURSE_STUDENTS, 
    ADD_COURSE_GROUP,
    UPDATE_COURSE_GROUP
}
