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
    ENROLL_STUDENT_IN_COURSES
}
