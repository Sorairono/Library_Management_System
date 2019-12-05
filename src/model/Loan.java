package model;

import java.sql.Timestamp;

public class Loan {
	private int Loan_id;
	private String Isbn;
	private String ISBN13;
	private int Card_id;
	private Timestamp Date_out;
	private Timestamp Due_date;
	private Timestamp Date_in;
	private String borrower_name;

	public Loan() {

	}

	public Loan(int loan_id, String iSBN10, String iSBN13, int card_id, Timestamp date_out, Timestamp due_date,
			Timestamp date_in, String borrower_name) {
		super();
		Loan_id = loan_id;
		Isbn = iSBN10;
		ISBN13 = iSBN13;
		Card_id = card_id;
		Date_out = date_out;
		Due_date = due_date;
		Date_in = date_in;
		this.borrower_name = borrower_name;
	}

	public int getLoan_id() {
		return Loan_id;
	}

	public void setLoan_id(int loan_id) {
		Loan_id = loan_id;
	}

	public String getIsbn() {
		return Isbn;
	}

	public void setIsbn(String isbn) {
		Isbn = isbn;
	}

	public String getISBN13() {
		return ISBN13;
	}

	public void setISBN13(String iSBN13) {
		ISBN13 = iSBN13;
	}

	public int getCard_id() {
		return Card_id;
	}

	public void setCard_id(int card_id) {
		Card_id = card_id;
	}

	public Timestamp getDate_out() {
		return Date_out;
	}

	public void setDate_out(Timestamp date_out) {
		Date_out = date_out;
	}

	public Timestamp getDue_date() {
		return Due_date;
	}

	public void setDue_date(Timestamp due_date) {
		Due_date = due_date;
	}

	public Timestamp getDate_in() {
		return Date_in;
	}

	public void setDate_in(Timestamp date_in) {
		Date_in = date_in;
	}

	public String getBorrower_name() {
		return borrower_name;
	}

	public void setBorrower_name(String borrower_name) {
		this.borrower_name = borrower_name;
	}

}
