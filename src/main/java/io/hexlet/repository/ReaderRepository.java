package io.hexlet.repository;

import io.hexlet.DatabaseManager;
import io.hexlet.model.Reader;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ReaderRepository {
    private static final Connection connection = DatabaseManager.connection;

    public static String addReader(String name, String email) {
        String sql = "INSERT INTO readers (name, email) VALUES (?, ?)";
        try (var ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, name);
            ps.setString(2, email);
            ps.executeUpdate();
            var generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getLong(1);
                Reader reader = new Reader(id, name, email);
                return String.format("This reader is created: %s", reader);
            }
            return String.format("Reader with email \"%s\" doesn't exist.", email);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static String getAllReaders() {
        String sql = "SELECT * FROM readers";
        try (var ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            var resultSet = ps.executeQuery();
            var readers = new ArrayList<Reader>();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                Reader reader = new Reader(id, name, email);
                readers.add(reader);
            }
            if (readers.isEmpty()) {
                return "Theres no readers.";
            }
            String output = readers.stream().map(Reader::toString).collect(Collectors.joining("\n    "));
            return String.format("""
                    List of readers:
                        %s""", output);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static String findReaderByEmail(String email) {
        var sql = "SELECT * FROM readers WHERE email = ?";
        try (var ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, email);
            var resultSet = ps.executeQuery();
            if (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                Reader reader = new Reader(id, name, email);
                return String.format("The reader is found: %s", reader);
            }
            return String.format("Reader with email \"%s\" doesn't exist.", email);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static String deleteReader(Long id) {
        String sql = "DELETE FROM readers WHERE id = ?";
        try (var ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
            return String.format("Reader with id \"%d\" is deleted.", id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
