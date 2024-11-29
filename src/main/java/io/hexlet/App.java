package io.hexlet;

import io.hexlet.repository.BookRepository;
import io.hexlet.repository.ReaderRepository;

import java.sql.Date;

public class App {
    public static void main(String[] args) {
        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.openConnection();

        System.out.println(BookRepository.getAllBooks());
        System.out.println(BookRepository.addBook("First", "Bill", Date.valueOf("2000-01-10"), "3984969856"));
        System.out.println(BookRepository.addBook("Second", "Winston", Date.valueOf("1999-02-13"), "8429466142"));
        System.out.println(BookRepository.addBook("Third", "Terry", Date.valueOf("2003-11-25"), "1445646899"));
        System.out.println(BookRepository.getAllBooks());
        System.out.println(BookRepository.findBookByTitle("Second"));
        System.out.println(BookRepository.deleteBook(2L));
        System.out.println(BookRepository.getAllBooks());
        System.out.println(BookRepository.findBookByTitle("Second"));

        System.out.println(ReaderRepository.getAllReaders());
        System.out.println(ReaderRepository.addReader("Alex", "alexemail@email.com"));
        System.out.println(ReaderRepository.addReader("Jack", "jackemail@email.com"));
        System.out.println(ReaderRepository.addReader("Mickael", "mickaelemail@email.com"));
        System.out.println(ReaderRepository.getAllReaders());
        System.out.println(ReaderRepository.findReaderByEmail("alexemail@email.com"));
        System.out.println(ReaderRepository.deleteReader(1L));
        System.out.println(ReaderRepository.getAllReaders());
        System.out.println(ReaderRepository.findReaderByEmail("alexemail@email.com"));

        databaseManager.closeConnection();
    }
}
