package com.maiboroda.datBase;

import com.maiboroda.StudentFactory.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDao {
    private Connection connection;

    public StudentDao(Connection connection) {
        this.connection = connection;
    }

    public void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS students (" +
                "id SERIAL PRIMARY KEY, " +
                "firstName VARCHAR(100) NOT NULL, " +
                "lastName VARCHAR(100) NOT NULL, " +
                "course INT NOT NULL, " +
                "birthday DATE NOT NULL" +
                ")";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        }
    }


    public void saveStudents(Student student) throws SQLException {
        String sql = "INSERT INTO students(firstName, lastName, course, birthday) VALUES (?, ?, ?, ?)";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, student.getFirstName() );
            statement.setString(2, student.getLastName());
            statement.setInt(3, student.getCourse());
            statement.setDate(4, java.sql.Date.valueOf(student.getBirthday()));

            statement.executeUpdate();
        }
    }

    public List<Student> getAllStudents() throws SQLException {
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
        }
        return students;
    }

}
