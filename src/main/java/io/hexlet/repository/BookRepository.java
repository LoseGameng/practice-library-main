package io.hexlet.repository;

import io.hexlet.DatabaseManager;
import io.hexlet.model.Book;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.stream.Collectors;

@AllArgsConstructor
public class BookRepository {
    private static final Connection connection = DatabaseManager.connection;

    public static String addBook(String title, String author, Date publishedDate, String isbn) {
        String sql = "INSERT INTO books (title, author, publishedDate, isbn) VALUES (?, ?, ?, ?)";
        try (var ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, title);
            ps.setString(2, author);
            ps.setDate(3, publishedDate);
            ps.setString(4, isbn);
            ps.executeUpdate();
            var generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getLong(1);
                Book book = new Book(id, title, author, publishedDate, isbn);
                return String.format("This book is created: %s", book);
            }
            return String.format("Book with title \"%s\" doesn't exist.", title);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getAllBooks() {
        String sql = "SELECT * FROM books";
        try (var ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            var resultSet = ps.executeQuery();
            var books = new ArrayList<Book>();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                Date publishedDate = resultSet.getDate("publishedDate");
                String isbn = resultSet.getString("isbn");
                Book book = new Book(id, title, author, publishedDate, isbn);
                books.add(book);
            }
            if (books.isEmpty()) {
                return "Theres no books.";
            }
            String output = books.stream().map(Book::toString).collect(Collectors.joining("\n    "));
            return String.format("""
                    List of books:
                        %s""", output);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String findBookByTitle(String title) {
        var sql = "SELECT * FROM books WHERE title = ?";
        try (var ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, title);
            var resultSet = ps.executeQuery();
            if (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String author = resultSet.getString("author");
                Date publishedDate = resultSet.getDate("publishedDate");
                String isbn = resultSet.getString("isbn");
                Book book = new Book(id, title, author, publishedDate, isbn);
                return String.format("The book is found: %s", book);
            }
            return String.format("Book with title \"%s\" doesn't exist.", title);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static String deleteBook(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (var ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
            return String.format("Book with id \"%d\" is deleted.", id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
