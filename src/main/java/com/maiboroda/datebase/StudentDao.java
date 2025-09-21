package com.maiboroda.datebase;

import com.maiboroda.studentModel.Student;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StudentDao {
    private Connection connection;

    public StudentDao(Connection connection) {
        this.connection = connection;
    }

    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS students (" +
                "id SERIAL PRIMARY KEY, " +
                "firstName VARCHAR(100) NOT NULL, " +
                "lastName VARCHAR(100) NOT NULL, " +
                "course INT NOT NULL, " +
                "birthday DATE NOT NULL" +
                ")";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException("Error with creating dataBase");
        }
    }


    public void saveStudents(Student student) {
        String sql = "INSERT INTO students(firstName, lastName, course, birthday) VALUES (?, ?, ?, ?)";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, student.getFirstName() );
            statement.setString(2, student.getLastName());
            statement.setInt(3, student.getCourse());
            statement.setDate(4, java.sql.Date.valueOf(student.getBirthday()));
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException("Error with saving students to bd");
        }
    }

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Student student = new Student(
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getInt("course"),
                        resultSet.getDate("birthday").toString()
                );
                students.add(student);
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Error with getting students");
        }
        return students;
    }

}
