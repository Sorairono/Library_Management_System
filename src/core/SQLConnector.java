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
						"SELECT BOOK_AUTHORS.Isbn, BOOK_AUTHORS.ISBN13, Title,  Checked_out, Pages, Publisher, Cover, GROUP_CONCAT(AUTHORS.Name SEPARATOR ', ') AS Authors FROM BOOK NATURAL JOIN BOOK_AUTHORS NATURAL JOIN AUTHORS GROUP BY BOOK_AUTHORS.Isbn, BOOK_AUTHORS.ISBN13");) {
			List<Book> bookList = new ArrayList<>();
			while (rs.next()) {
				String Isbn = rs.getString("Isbn");
				String ISBN13 = rs.getString("ISBN13");
				String Title = rs.getString("Title");
				String Author = rs.getString("Authors");
				String Cover = rs.getString("Cover");
				String Publisher = rs.getString("Publisher");
				int Pages = rs.getInt("Pages");
				boolean Checked_out = rs.getBoolean("Checked_out");

				Book book = new Book(Isbn, ISBN13, Title, Cover, Publisher, Author, Pages, Checked_out);

				bookList.add(book);
			}
			return bookList;
		}
	}

	public List<Book> searchBookList(String search_command) throws SQLException {
		try (Statement stmnt = connection.createStatement(); ResultSet rs = stmnt.executeQuery(search_command);) {
			List<Book> bookList = new ArrayList<>();
			while (rs.next()) {
				String Isbn = rs.getString("Isbn");
				String ISBN13 = rs.getString("ISBN13");
				String Title = rs.getString("Title");
				String Author = rs.getString("Authors");
				String Cover = rs.getString("Cover");
				String Publisher = rs.getString("Publisher");
				int Pages = rs.getInt("Pages");
				boolean Checked_out = rs.getBoolean("Checked_out");

				Book book = new Book(Isbn, ISBN13, Title, Cover, Publisher, Author, Pages, Checked_out);

				bookList.add(book);
			}
			return bookList;
		}
	}

	public boolean insertLoan(Book current_book, int borrower_id) throws SQLException {
		String insert = "INSERT INTO BOOK_LOANS (Isbn, ISBN13, Card_id, Date_out, Due_date)" + "VALUES (?, ?, ?, NOW(), DATE_ADD(NOW(), INTERVAL  14 DAY))";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(insert);
			preparedStatement.setString(1, current_book.getIsbn());
			preparedStatement.setString(2, current_book.getISBN13());
			preparedStatement.setInt(3, borrower_id);
			preparedStatement.execute();
			return true;
		} catch (SQLException e) {
			// TODO: handle exception
			Singleton.getInstance().setDecline_message(e.getMessage());
			return false;
		}
	}

	public boolean insertBorrower(Borrower new_borrower) throws SQLException {
		String insert = "INSERT INTO BORROWER (Ssn, Bname, Email, Address, Phone)" + "VALUES (?, ?, ?, ?, ?)";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(insert);
			preparedStatement.setString(1, new_borrower.getSsn());
			preparedStatement.setString(2, new_borrower.getBname());
			preparedStatement.setString(3, new_borrower.getEmail());
			preparedStatement.setString(4, new_borrower.getAddress());
			preparedStatement.setString(5, new_borrower.getPhone());
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
				ResultSet rs = stmnt.executeQuery(
						"SELECT Bname, Loan_id, Isbn, ISBN13, Card_id, Date_out, Due_date, Date_in FROM BOOK_LOANS NATURAL JOIN BORROWER");) {
			List<Loan> loanList = new ArrayList<>();
			while (rs.next()) {
				int Loan_id = rs.getInt("Loan_id");
				String Isbn = rs.getString("Isbn");
				String ISBN13 = rs.getString("ISBN13");
				int Card_id = rs.getInt("Card_id");
				Timestamp Date_out = rs.getTimestamp("Date_out");
				Timestamp Due_date = rs.getTimestamp("Due_date");
				Timestamp Date_in = rs.getTimestamp("Date_in");
				String borrower_name = rs.getString("Bname");
				loanList.add(new Loan(Loan_id, Isbn, ISBN13, Card_id, Date_out, Due_date, Date_in, borrower_name));
			}
			return loanList;
		}
	}

	public List<Loan> searchLoanList(String search_command) throws SQLException {
		try (Statement stmnt = connection.createStatement(); ResultSet rs = stmnt.executeQuery(search_command);) {
			List<Loan> loan_list = new ArrayList<>();
			while (rs.next()) {
				int Loan_id = rs.getInt("Loan_id");
				String ISBN10 = rs.getString("Isbn");
				String ISBN13 = rs.getString("ISBN13");
				int Borrower_id = rs.getInt("Card_id");
				Timestamp Date_out = rs.getTimestamp("Date_out");
				Timestamp Due_date = rs.getTimestamp("Due_date");
				Timestamp Date_in = rs.getTimestamp("Date_in");
				String borrower_name = rs.getString("Bname");
				loan_list.add(
						new Loan(Loan_id, ISBN10, ISBN13, Borrower_id, Date_out, Due_date, Date_in, borrower_name));
			}
			return loan_list;
		}
	}
	
	public void check_in_book(int loan_id) throws SQLException {
		try {
			Statement stmnt = connection.createStatement();
			String s1 = "UPDATE BOOK_LOANS SET Date_in = NOW() WHERE Loan_id = " + loan_id;
			String s2 = "UPDATE BOOK SET Checked_out = 0 WHERE Isbn = (SELECT Isbn FROM BOOK_LOANS WHERE Loan_id = " + loan_id + ")";
			stmnt.addBatch(s1);
			stmnt.addBatch(s2);
			stmnt.executeBatch();
			System.out.println("Check in successfully");
		} catch (SQLException e) {
			// TODO: handle exception
			System.out.println("Failed to check in");
		}
	}

	public List<Borrower> getBorrowerList() throws SQLException {
		try (Statement stmnt = connection.createStatement();
				ResultSet rs = stmnt.executeQuery("SELECT * FROM BORROWER");) {
			List<Borrower> borrower_list = new ArrayList<>();
			while (rs.next()) {
				int Card_id = rs.getInt("Card_id");
				String ssn = rs.getString("Ssn");
				String Bname = rs.getString("Bname");
				String email = rs.getString("Email");
				String address = rs.getString("Address");
				String phone = rs.getString("Phone");
				borrower_list.add(new Borrower(Card_id, ssn, Bname, email, address, phone));
			}
			return borrower_list;
		}
	}

	public List<Borrower> searchBorrowerList(String search_command) throws SQLException {
		try (Statement stmnt = connection.createStatement();
				ResultSet rs = stmnt
						.executeQuery("SELECT * FROM BORROWER WHERE Bname LIKE '%" + search_command + "%'");) {
			List<Borrower> borrowerSearch_list = new ArrayList<>();
			while (rs.next()) {
				int Card_id = rs.getInt("Card_id");
				String Ssn = rs.getString("Ssn");
				String Bname = rs.getString("Bname");
				String Email = rs.getString("Email");
				String Address = rs.getString("Address");
				String Phone = rs.getString("Phone");
				borrowerSearch_list.add(new Borrower(Card_id, Ssn, Bname, Email, Address, Phone));
			}
			return borrowerSearch_list;
		}
	}

	public List<Fine> getFineList(int card_id, int option) throws SQLException {
		try {
			Statement stmnt = connection.createStatement();
			ResultSet rs = null;
			if (option == 0) {
				rs = stmnt.executeQuery("SELECT * FROM BOOK_LOANS NATURAL JOIN FINES WHERE Card_id = " + card_id);
			} else if (option == 1) {
				rs = stmnt.executeQuery(
						"SELECT * FROM BOOK_LOANS NATURAL JOIN FINES WHERE Card_id = " + card_id + " AND Paid = 0");
			} else {
				rs = stmnt.executeQuery("SELECT * FROM BOOK_LOANS NATURAL JOIN FINES WHERE Card_id = " + card_id
						+ " AND Paid = 0 AND Date_in IS NOT NULL");
			}
			List<Fine> fine_list = new ArrayList<>();
			while (rs.next()) {
				int Loan_id = rs.getInt("Loan_id");
				String Isbn = rs.getString("Isbn");
				double Fine_amt = rs.getDouble("Fine_amt");
				boolean Paid = rs.getBoolean("Paid");
				fine_list.add(new Fine(Loan_id, Isbn, Fine_amt, Paid));
			}
			return fine_list;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	
	public void borrower_pay_fine(int card_id) throws SQLException {
		String update = "UPDATE FINES NATURAL JOIN BOOK_LOANS  SET Paid = TRUE WHERE Card_id = ? AND Date_in IS NOT NULL";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(update);
			preparedStatement.setInt(1, card_id);
			preparedStatement.execute();
		} catch (SQLException e) {
			// TODO: handle exception
		}
	}
}
