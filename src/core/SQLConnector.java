package core;

import model.Book;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import java.util.List;
import java.util.ArrayList;

public class SQLConnector {
	private Connection connection;

	public SQLConnector(String driverClassName, String dbURL, String user, String password)
			throws SQLException, ClassNotFoundException {
		Class.forName(driverClassName);
		connection = DriverManager.getConnection(dbURL, user, password);
	}

	public void shutdown() throws SQLException {
		if (connection != null) {
			connection.close();
		}
	}

	public List<Book> getBookList() throws SQLException {
		try (Statement stmnt = connection.createStatement();
				ResultSet rs = stmnt.executeQuery(
						"SELECT BOOK_AUTHORS.ISBN10, BOOK_AUTHORS.ISBN13, Title,  Checked_out, Pages, Publisher,  Cover, GROUP_CONCAT(Author SEPARATOR ', ') AS Authors FROM BOOKS NATURAL JOIN BOOK_AUTHORS GROUP BY BOOK_AUTHORS.ISBN10, BOOK_AUTHORS.ISBN13");) {
			List<Book> bookList = new ArrayList<>();
			while (rs.next()) {
				String ISBN10 = rs.getString("ISBN10");
				String ISBN13 = rs.getString("ISBN13");
				String Title = rs.getString("Title");
				String Author = rs.getString("Authors");
				String Cover = rs.getString("Cover");
				String Publisher = rs.getString("Publisher");
				int Pages = rs.getInt("Pages");
				boolean Checked_out = rs.getBoolean("Checked_out");

				Book book = new Book(ISBN10, ISBN13, Title, Cover, Publisher, Author, Pages, Checked_out);

				bookList.add(book);
			}
			return bookList;
		}
	}

	public List<Book> searchBookList(String search_command) throws SQLException {
		try (Statement stmnt = connection.createStatement(); 
				ResultSet rs = stmnt.executeQuery(search_command);) {
			List<Book> bookList = new ArrayList<>();
			while (rs.next()) {
				String ISBN10 = rs.getString("ISBN10");
				String ISBN13 = rs.getString("ISBN13");
				String Title = rs.getString("Title");
				String Author = rs.getString("Authors");
				String Cover = rs.getString("Cover");
				String Publisher = rs.getString("Publisher");
				int Pages = rs.getInt("Pages");
				boolean Checked_out = rs.getBoolean("Checked_out");

				Book book = new Book(ISBN10, ISBN13, Title, Cover, Publisher, Author, Pages, Checked_out);

				bookList.add(book);
			}
			return bookList;
		}
	}

	public void insertLoan(Book current_book, int borrower_id) throws SQLException {
		String query = "INSERT INTO BOOK_LOANS (ISBN10, ISBN13, Borrower_id, Date_out, Due_date) VALUES ('"
				+ current_book.getISBN10() + "' , '" + current_book.getISBN13() + "' ," + borrower_id + ","
				+ " NOW(), DATE_ADD(NOW(), INTERVAL  14 DAY))";
		try {
			Statement stmnt = connection.prepareStatement(query);
			stmnt.execute(query);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}
