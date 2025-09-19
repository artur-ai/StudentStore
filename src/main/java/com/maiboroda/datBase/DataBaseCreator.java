package com.maiboroda.datBase;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataBaseCreator {
    private static final Logger logger = Logger.getLogger(DataBaseCreator.class.getName());

    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/postgres";

        try (Connection connection = DriverManager.getConnection(url)) {
            String sql = "CREATE DATABASE mydb";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.executeUpdate();
                logger.info("Database 'mydb' created successfully");
            }
        } catch (SQLException exception) {
            logger.log(Level.SEVERE, "Error with connection to Postgres", exception);
        }
    }
}
