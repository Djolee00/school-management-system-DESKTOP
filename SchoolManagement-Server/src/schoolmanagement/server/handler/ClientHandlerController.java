/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schoolmanagement.server.handler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import schoolmanagement.commonlib.communication.Request;
import schoolmanagement.commonlib.communication.Response;
import schoolmanagement.commonlib.communication.ResponseType;
import schoolmanagement.commonlib.model.Course;
import schoolmanagement.commonlib.model.CourseEnrollment;
import schoolmanagement.commonlib.model.Student;
import schoolmanagement.commonlib.model.User;
import schoolmanagement.service.StudentService;
import schoolmanagement.service.UserService;
import schoolmanagement.service.provider.ServiceProvider;

/**
 *
 * @author ivano
 */
public class ClientHandlerController {

    public Response loginUser(Request request) throws IOException, SQLException {
        Response response = new Response();
        User temp = (User) request.getObject();
        User user = ((UserService) ServiceProvider.getInstance().getRequiredService(UserService.class)).login(temp.getUsername(), temp.getPassword());
        if (user == null) {
            response.setResponseType(ResponseType.FAILURE);
        } else {
            response.setResponseType(ResponseType.SUCCESS);
            response.setObject(user);
        }

        return response;
    }

    public Response getStudentCourses(Request request) throws IOException, SQLException {
        Response response = new Response();
        Student student = (Student) request.getObject();

        List<CourseEnrollment> courses = ((StudentService) ServiceProvider.getInstance().getRequiredService(StudentService.class)).getStudentCourses(student.getId());
        if (courses == null) {
            response.setResponseType(ResponseType.FAILURE);
        } else {
            response.setResponseType(ResponseType.SUCCESS);
            response.setObject(courses);
        }

        return response;
    }

    Response getStudentUnselectedCourses(Request request) throws IOException, SQLException {
        Response response = new Response();
        Student student = (Student) request.getObject();

        List<Course> courses = ((StudentService) ServiceProvider.getInstance().getRequiredService(StudentService.class)).getStudentUnselectedCourses(student.getId());
        if (courses == null) {
            response.setResponseType(ResponseType.FAILURE);
        } else {
            response.setResponseType(ResponseType.SUCCESS);
            response.setObject(courses);
        }

        return response;
    }

}
