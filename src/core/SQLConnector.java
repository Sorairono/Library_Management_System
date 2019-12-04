package core;

import model.*;
import singleton.Singleton;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.ResultSet;

import java.util.List;

import java.util.ArrayList;
import java.util.Calendar;

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
		try (Statement stmnt = connection.createStatement(); ResultSet rs = stmnt.executeQuery(search_command);) {
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

	public boolean insertLoan(Book current_book, int borrower_id) throws SQLException {
		String insert = "INSERT INTO BOOK_LOANS (ISBN10, ISBN13, Borrower_id, Date_out, Due_date)"
				+ "VALUES (?, ?, ?, ?, ?)";
		Calendar calendar = Calendar.getInstance();
		Date startDate = new Date(calendar.getTime().getTime());
		System.out.println(calendar.getTime().getTime());
		calendar.add(Calendar.DATE, 14);
		Date dueDate = new Date(calendar.getTime().getTime());
		System.out.println(calendar.getTime().getTime());
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(insert);
			preparedStatement.setString(1, current_book.getISBN10());
			preparedStatement.setString(2, current_book.getISBN13());
			preparedStatement.setInt(3, borrower_id);
			preparedStatement.setDate(4, startDate);
			preparedStatement.setDate(5, dueDate);
			preparedStatement.execute();
			return true;
		} catch (SQLException e) {
			// TODO: handle exception
			Singleton.getInstance().setDecline_message(e.getMessage());
			return false;
		}
	}
	
	public boolean insertBorrower(Borrower new_borrower) throws SQLException {
		String insert = "INSERT INTO BORROWER (ssn, first_name, last_name, email, address, city, state, phone)" + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(insert);
			preparedStatement.setString(1, new_borrower.getSsn());
			preparedStatement.setString(2, new_borrower.getFirst_name());
			preparedStatement.setString(3, new_borrower.getLast_name());
			preparedStatement.setString(4, new_borrower.getEmail());
			preparedStatement.setString(5, new_borrower.getAddress());
			preparedStatement.setString(6, new_borrower.getCity());
			preparedStatement.setString(7, new_borrower.getState());
			preparedStatement.setString(8, new_borrower.getPhone());
			preparedStatement.execute();
			return true;
		} catch (SQLException e) {
			// TODO: handle exception
			Singleton.getInstance().setDecline_message(e.getMessage());
			return false;
		}
	}

	public List<Loan> getLoanList() throws SQLException {
		try (Statement stmnt = connection.createStatement();
				ResultSet rs = stmnt.executeQuery("SELECT * FROM BOOK_LOANS");) {
			List<Loan> loanList = new ArrayList<>();
			while (rs.next()) {
				int Loan_id = rs.getInt("Loan_id");
				String ISBN10 = rs.getString("ISBN10");
				String ISBN13 = rs.getString("ISBN13");
				int Borrower_id = rs.getInt("Borrower_id");
				Timestamp Date_out = rs.getTimestamp("Date_out");
				Timestamp Due_date = rs.getTimestamp("Due_date");
				Timestamp Date_in = rs.getTimestamp("Date_in");
				loanList.add(new Loan(Loan_id, ISBN10, ISBN13, Borrower_id, Date_out, Due_date, Date_in));
			}
			return loanList;
		}
	}

	public List<Borrower> getBorrowerList() throws SQLException {
		try (Statement stmnt = connection.createStatement();
				ResultSet rs = stmnt.executeQuery("SELECT * FROM BORROWER");) {
			List<Borrower> borrower_list = new ArrayList<>();
			while (rs.next()) {
				int borrower_id = rs.getInt("borrower_id");
				String ssn = rs.getString("ssn");
				String first_name = rs.getString("first_name");
				String last_name = rs.getString("last_name");
				String email = rs.getString("email");
				String address = rs.getString("address");
				String city = rs.getString("city");
				String state = rs.getString("state");
				String phone = rs.getString("phone");
				borrower_list
						.add(new Borrower(borrower_id, ssn, first_name, last_name, email, address, city, state, phone));
			}
			return borrower_list;
		}
	}
	
//    public List<Borrower> searchBorrowerList(String search_command) throws SQLException {
//        try (Statement stmnt = connection.createStatement();
//                ResultSet rs = stmnt.executeQuery("SELECT * FROM BORROWER WHERE Bname IS LIKE" + search_command);) {
//            List<Borrower> borrowerSearch_list = new ArrayList<>();
//            while (rs.next()) {
//                int Card_id = rs.getInt("Card_id");
//                String Ssn = rs.getString("Ssn");
//                String Bname = rs.getString("Bname");
//                String Email = rs.getString("Email");
//                String Address = rs.getString("Address");
//                String Phone = rs.getString("Phone");
//                borrowerSearch_list.add(new Borrower(Card_id, Ssn, Bname, Email, Address, Phone));
//            }
//            return borrowerSearch_list;
//        }
//    }
}
