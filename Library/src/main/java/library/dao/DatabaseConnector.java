package library.dao;

import java.sql.*;

public class DatabaseConnector {

    private static final String url = "jdbc:postgresql://localhost:5432/DB";
    private static final String user = "postgres";
    private static final String password = "0998870";
    private Connection conn;

    public DatabaseConnector() throws SQLException {
        this.conn = DriverManager.getConnection(url, user, password);
    }

    public void closeConnection() throws SQLException {
        conn.close();
    }

    public PreparedStatement insertBook() throws SQLException {
        return conn.prepareStatement("INSERT INTO book (name, author) VALUES (?,?)");
    }

    public PreparedStatement selectBook() throws SQLException {
        return conn.prepareStatement("SELECT name, author FROM book WHERE name=?");
    }

    public PreparedStatement updateBook() throws SQLException {
        return conn.prepareStatement("UPDATE book SET name=? WHERE name=? AND author=?");
    }

    public PreparedStatement deleteBook() throws SQLException {
        return conn.prepareStatement("DELETE FROM book WHERE name=? AND author=?");
    }

    public PreparedStatement findBook() throws SQLException {
        return conn.prepareStatement("SELECT name FROM book WHERE name=? AND author=?");
    }

    public ResultSet getBooks() throws SQLException {
        return conn.createStatement().executeQuery("SELECT name, author FROM book ORDER BY name");
    }
}
