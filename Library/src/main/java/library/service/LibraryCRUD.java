package library.service;

import library.dao.DatabaseConnector;
import library.entity.Book;
import java.sql.*;
import java.util.*;


public class LibraryCRUD {

    private Scanner scanner;

    public LibraryCRUD(Scanner scanner) {
        this.scanner = scanner;
    }

    public void createBook() throws SQLException {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        try {
            Book book = new Book();
            System.out.println("Enter a name of the book, which you would like to add: \n");
            book.setName(scanner.nextLine());
            System.out.println("Enter author's name: \n");
            book.setAuthor(scanner.nextLine());
            if (checkIfExist(book)) {
                System.out.println("Book already exists");
                return;
            }
            PreparedStatement preparedStatement = databaseConnector.insertBook();
            preparedStatement.setString(1, book.getName());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.executeUpdate();
            System.out.println("Book " + book.getName() + " by " + book.getAuthor() + " was added successfully!");
        } finally {
            databaseConnector.closeConnection();
        }
    }

    public void readBooks() throws SQLException {

        DatabaseConnector databaseConnector = new DatabaseConnector();
        try {
            ResultSet books = databaseConnector.getBooks();
            System.out.println("Book list:\n");
            while (books.next()) {
                System.out.println("Title: " + books.getString(1) + "\n Author: " + books.getString(2) + "\n-----");
            }
        }
        finally {
            databaseConnector.closeConnection();
        }
        }


    public void updateBook() throws SQLException {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        try {
            System.out.println("What book would you like to update?");
            String oldTitle = scanner.nextLine();
            Book bookToUpdate = selectBook(oldTitle);
            if (bookToUpdate != null) {
                PreparedStatement stmt = databaseConnector.updateBook();
                System.out.println(bookToUpdate.getName());
                System.out.println("New Title:");
                String newTitle = scanner.nextLine();
                stmt.setString(1, newTitle);
                stmt.setString(2, bookToUpdate.getName());
                stmt.setString(3, bookToUpdate.getAuthor());
                stmt.executeUpdate();
                System.out.println("Book '" + bookToUpdate.getName() + "' by '" + bookToUpdate.getAuthor() + "' successfully renamed to '" + newTitle + "'");
            } else {
                System.out.println("There is no such a book in a library");
            }
        }
        finally {
            databaseConnector.closeConnection();
        }
    }

    public void deleteBook() throws SQLException {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        try {
            Book book = new Book();
            PreparedStatement stmt = databaseConnector.deleteBook();
            System.out.println("Enter a name of the book, which you would like to delete: \n");
            book.setName(scanner.nextLine());
            Book bookToDelete = selectBook(book.getName());
            stmt.setString(1, bookToDelete.getName());
            stmt.setString(2, bookToDelete.getAuthor());
            stmt.executeUpdate();
            System.out.println("Book " + bookToDelete.getName() + " by " + bookToDelete.getAuthor() + " was deleted successfully!");
        } finally {
            databaseConnector.closeConnection();
        }
    }
    private Book selectBook(String bookTitle) throws SQLException {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        try {
            PreparedStatement stmt = databaseConnector.selectBook();
            stmt.setString(1, bookTitle);
            ResultSet resultSet = stmt.executeQuery();
            List<Book> matchingBooks = new ArrayList<>();
            while (resultSet.next()) {
                matchingBooks.add(new Book(resultSet.getString(1), resultSet.getString(2)));
            }
            if (matchingBooks.size() == 1) {
                return matchingBooks.get(0);
            }
            if (matchingBooks.size() > 1) {
                System.out.println("Book of which author you would like to pick? \n");
                for (Book book : matchingBooks) {
                    System.out.println(book.getAuthor());
                }
                String author = scanner.nextLine();
                for (Book book : matchingBooks) {
                    if (book.getAuthor().equals(author)) {
                        return book;
                    }
                }
            } else return null;
        } finally {
            databaseConnector.closeConnection();
        }
        return null;
    }

    private boolean checkIfExist(Book book) throws SQLException {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        try {
            PreparedStatement preparedStatement1 = databaseConnector.findBook();
            preparedStatement1.setString(1, book.getName());
            preparedStatement1.setString(2, book.getAuthor());
            ResultSet resultSet = preparedStatement1.executeQuery();
            if (resultSet.next()) {
                System.out.println("Book already exists");
                return true;
            }
        } finally {
            databaseConnector.closeConnection();
        }
        return false;
    }

}
