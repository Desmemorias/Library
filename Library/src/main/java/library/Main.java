package library;

import library.dao.LibraryService;

import java.sql.*;

public class Main {

    public static void main(String args[]) {
        LibraryService libraryService = new LibraryService();
        try {
            libraryService.actionChooser();
        } catch (SQLException e) {
            e.getMessage();
            e.getErrorCode();
            e.getNextException();
        }
    }
}