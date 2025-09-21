package com.maiboroda.servlets;

import com.maiboroda.studentModel.Student;
import com.maiboroda.datebase.StudentDao;
import com.maiboroda.templater.PageGenerator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
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
        response.setStatus(HttpServletResponse.SC_OK);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        int course = Integer.parseInt(request.getParameter("course"));
        if (course < 1 || course > 5) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Course must be from 1 to 5");
        }

        String birthday = request.getParameter("birthday");

        studentDao.saveStudents(new Student(firstName, lastName, course, birthday));


        response.sendRedirect("/students");
        response.setStatus(HttpServletResponse.SC_CREATED);
    }
}
