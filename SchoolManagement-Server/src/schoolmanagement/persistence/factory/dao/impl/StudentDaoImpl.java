/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schoolmanagement.persistence.factory.dao.impl;

import java.io.IOException;
import java.util.List;
import schoolmanagement.commonlib.model.Student;
import schoolmanagement.persistence.factory.dao.StudentDao;
import java.sql.*;
import java.time.LocalDateTime;
import schoolmanagement.persistence.factory.ConnectionFactory;

/**
 *
 * @author ivano
 */
public class StudentDaoImpl implements StudentDao {

    @Override
    public Student saveStudent(Student student) throws SQLException, IOException {
        try ( Connection conn = ConnectionFactory.getInstance().getConnection()) {
            try {
                conn.setAutoCommit(false);

                long userId = insertUser(conn, student.getUsername(), student.getPassword());
                student.setId(userId);

                Student newStudent = insertStudent(conn, student);

                conn.commit();
                
                return newStudent;
            } catch (SQLException e) {
                System.out.println("Error inserting student");
                conn.rollback();
                throw e;
            }
        }

    }

    @Override
    public void updateStudent() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Student> getAllStudents() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Student getStudentById(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private long insertUser(Connection conn, String username, String password) throws SQLException {
        final String sqlQuery = "INSERT INTO User(username,password) VALUES(?,?)";
        try ( PreparedStatement statement = conn.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, username);
            statement.setString(2, password);

            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            long key = rs.next() ? rs.getLong(1) : 0;
            return key;
        }

    }

    private Student insertStudent(Connection conn, Student student) throws SQLException {
        final String sqlQuery = "INSERT INTO Student(user_id,first_name,last_name,birthdate,creation_date) VALUES(?,?,?,?,?)";
        try ( PreparedStatement statement = conn.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)) {

            statement.setLong(1, student.getId());
            statement.setString(2, student.getFirstName());
            statement.setString(3, student.getLastName());
            statement.setDate(4, Date.valueOf(student.getBirthdate()));
            statement.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));

            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            long key = rs.next() ? rs.getLong(1) : 0;
            student.setId(key);
            
            return student;
        }
    }

}
