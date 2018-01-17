package library.dao;

import library.service.*;
import java.sql.*;
import java.util.*;

public class LibraryService {

    public void actionChooser() throws SQLException {

        LibraryCRUD libraryCRUD = new LibraryCRUD(new Scanner(System.in));
        System.out.println("---------------\nChoose an action:\n" + "1 - Add book\n2 - Remove book\n3" +
                " - Edit book\n4 - Show book list" + "\n5 - Exit");
        Scanner scanner = new Scanner(System.in);
        int i = scanner.nextInt();
            switch (i) {
                case 1:
                    libraryCRUD.createBook();
                    break;
                case 2:
                    libraryCRUD.deleteBook();
                    break;
                case 3:
                    libraryCRUD.updateBook();
                    break;
                case 4:
                    libraryCRUD.readBooks();
                    break;
                case 5:
                    System.exit(0);
                    break;
                default:
                    System.err.println("Wrong input value. Try again.");
                    actionChooser();
                    break;
            }
            actionChooser();

        }
}
