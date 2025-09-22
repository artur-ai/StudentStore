package com.maiboroda.main;

import com.maiboroda.datebase.StudentDao;
import com.maiboroda.servlets.AddStudentServlet;
import com.maiboroda.servlets.StudentServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws Exception {
        String portStr = System.getenv("PORT");
        int port = portStr != null ? Integer.parseInt(portStr) : 2112;

        logger.info("Starting server on port: " + port);

        Connection connection = null;
        Server server = null;

        try {
            connection = getDatabaseConnection();
            logger.info("Connected to database successfully");

            StudentDao studentDao = new StudentDao(connection);
            studentDao.createTable();
            logger.info("Database table created/verified");

            server = new Server(port);
            ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
            context.setContextPath("/");

            context.addServlet(new ServletHolder(new StudentServlet(studentDao)), "/students");
            context.addServlet(new ServletHolder(new AddStudentServlet(studentDao)), "/students/add");

            context.addServlet(new ServletHolder(new StudentServlet(studentDao)), "/");

            server.setHandler(context);

            final Connection finalConnection = connection;
            final Server finalServer = server;
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    if (finalServer != null) {
                        finalServer.stop();
                        logger.info("Server stopped");
                    }
                    if (finalConnection != null && !finalConnection.isClosed()) {
                        finalConnection.close();
                        logger.info("Database connection closed");
                    }
                } catch (Exception e) {
                    logger.log(Level.WARNING, "Error during shutdown", e);
                }
            }));

            server.start();
            logger.info("Server started successfully on port " + port);
            server.join();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Database connection error", e);
            throw new RuntimeException("Failed to connect to database", e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Server startup error", e);
            throw e;
        }
    }

    private static Connection getDatabaseConnection() throws SQLException {
        String databaseUrl = System.getenv("DATABASE_URL");

        if (databaseUrl != null) {
            Logger.getLogger(Main.class.getName()).info("Using Render DATABASE_URL");
            return DriverManager.getConnection(databaseUrl);
        } else {
            Logger.getLogger(Main.class.getName()).info("Using local database configuration");
            String url = "jdbc:postgresql://localhost:5432/mydb";
            String username = "postgres";
            String password = "postgres";
            return DriverManager.getConnection(url, username, password);
        }
    }
}