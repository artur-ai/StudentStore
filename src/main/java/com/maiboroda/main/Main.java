package com.maiboroda.main;

import com.maiboroda.datBase.StudentDao;
import com.maiboroda.servlets.AddStudentServlet;
import com.maiboroda.servlets.StudentServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.sql.Connection;
import java.sql.DriverManager;

public class Main {
    public static void main(String[] args) throws Exception {
        Server server = new Server(2112);

        Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/mybd"
        );


        StudentDao studentDao = new StudentDao(connection);
        studentDao.createTable();


        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new StudentServlet(studentDao)), "/index.html");
        context.addServlet(new ServletHolder(new AddStudentServlet(studentDao)), "/addStudent.html");

        server.setHandler(context);
        server.start();
        server.join();


    }
}
