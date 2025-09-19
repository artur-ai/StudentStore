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
import java.util.Map;

public class AddStudentServlet extends HttpServlet {
    private final StudentDao studentDao;

    public AddStudentServlet(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset = UTF-8");

        Map<String, Object> params = new HashMap<>();
        String page = PageGenerator.instance().getPage("addStudent.html", params);
        response.getWriter().println(page);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        int course = Integer.parseInt(request.getParameter("course"));
        String birthday = request.getParameter("birthday");

        try {
            studentDao.saveStudents(new Student(firstName, lastName, course, birthday));
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }

        response.sendRedirect("/index.html");
    }
}
