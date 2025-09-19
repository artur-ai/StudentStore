package com.maiboroda.servlets;

import com.maiboroda.StudentFactory.Student;
import com.maiboroda.datBase.StudentDao;
import com.maiboroda.templater.PageGenerator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentServlet extends HttpServlet {
    public final StudentDao studentDao;

    public StudentServlet(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF8");

        try {
            List<Student> students = studentDao.getAllStudents();
            Map<String, Object> params = new HashMap<>();
            params.put("students", students);

            String page = PageGenerator.instance().getPage("index.html", params);
            response.getWriter().println(page);
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }


}
