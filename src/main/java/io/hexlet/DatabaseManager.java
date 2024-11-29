package io.hexlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.stream.Collectors;

public class DatabaseManager {
    public static Connection connection;
    private static final String URL = "jdbc:h2:mem:library_db;DB_CLOSE_DELAY=-1;";

    public void openConnection() {
        try {
            connection = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String sql = loadDatabaseSchema();
        try {
            connection.prepareStatement(sql).execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static String loadDatabaseSchema() {
        try (var inputStream = DatabaseManager.class.getClassLoader().getResourceAsStream("h2.sql")) {
            try (var reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                return reader.lines().collect(Collectors.joining("\n"));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
