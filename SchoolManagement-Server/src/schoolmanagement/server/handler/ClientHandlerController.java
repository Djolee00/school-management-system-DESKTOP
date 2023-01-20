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
import schoolmanagement.commonlib.model.Language;
import schoolmanagement.commonlib.model.Student;
import schoolmanagement.commonlib.model.User;
import schoolmanagement.service.CourseService;
import schoolmanagement.service.LanguageService;
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

    public Response getStudentUnselectedCourses(Request request) throws IOException, SQLException {
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

    public Response enrollStudentInCourses(Request request) throws IOException, SQLException {
        Response response = new Response();
        List<CourseEnrollment> selectedCourses = (List<CourseEnrollment>) request.getObject();

        boolean status = ((StudentService) ServiceProvider.getInstance().getRequiredService(StudentService.class)).enrollStudentInCourses(selectedCourses);
        if (status == false) {
            response.setResponseType(ResponseType.FAILURE);
        } else {
            response.setResponseType(ResponseType.SUCCESS);
        }

        return response;
    }

    public Response getAllStudents() throws IOException, SQLException {
        Response response = new Response();

        List<Student> students = ((StudentService) ServiceProvider.getInstance().getRequiredService(StudentService.class)).getAllStudents();
        if (students == null) {
            response.setResponseType(ResponseType.FAILURE);
        } else {
            response.setResponseType(ResponseType.SUCCESS);
            response.setObject(students);
        }

        return response;
    }

    public Response getAllCourses() throws IOException, SQLException {
        Response response = new Response();

        List<Course> courses = ((CourseService) ServiceProvider.getInstance().getRequiredService(CourseService.class)).getAllCourses();
        if (courses == null) {
            response.setResponseType(ResponseType.FAILURE);
        } else {
            response.setResponseType(ResponseType.SUCCESS);
            response.setObject(courses);
        }

        return response;
    }

    public Response getAllLanguages() throws IOException, SQLException {
        Response response = new Response();

        List<Language> languages = ((LanguageService) ServiceProvider.getInstance().getRequiredService(LanguageService.class)).getAllLanguages();
        if (languages == null) {
            response.setResponseType(ResponseType.FAILURE);
        } else {
            response.setResponseType(ResponseType.SUCCESS);
            response.setObject(languages);
        }

        return response;
    }

}
