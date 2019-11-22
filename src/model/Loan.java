package model;

import java.sql.Timestamp;

public class Loan {
	private int Loan_id;
	private String ISBN10;
	private String ISBN13;
	private int Borrower_id;
	private Timestamp Date_out;
	private Timestamp Due_date;
	private Timestamp Date_in;

	public Loan() {

	}

	public Loan(int loan_id, String iSBN10, String iSBN13, int borrower_id, Timestamp date_out, Timestamp due_date,
			Timestamp date_in) {
		super();
		Loan_id = loan_id;
		ISBN10 = iSBN10;
		ISBN13 = iSBN13;
		Borrower_id = borrower_id;
		Date_out = date_out;
		Due_date = due_date;
		Date_in = date_in;
	}

	public int getLoan_id() {
		return Loan_id;
	}

	public void setLoan_id(int loan_id) {
		Loan_id = loan_id;
	}

	public String getISBN10() {
		return ISBN10;
	}

	public void setISBN10(String iSBN10) {
		ISBN10 = iSBN10;
	}

	public String getISBN13() {
		return ISBN13;
	}

	public void setISBN13(String iSBN13) {
		ISBN13 = iSBN13;
	}

	public int getBorrower_id() {
		return Borrower_id;
	}

	public void setBorrower_id(int borrower_id) {
		Borrower_id = borrower_id;
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

}
