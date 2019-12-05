package model;

public class Fine {
	private int Loan_id;
	private String Isbn;
	private double Fine_amt;
	private boolean Paid;

	public Fine() {

	}

	public Fine(int loan_id, String isbn, double fine_amt, boolean paid) {
		super();
		Loan_id = loan_id;
		Isbn = isbn;
		Fine_amt = fine_amt;
		Paid = paid;
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

	public double getFine_amt() {
		return Fine_amt;
	}

	public void setFine_amt(double fine_amt) {
		Fine_amt = fine_amt;
	}

	public boolean isPaid() {
		return Paid;
	}

	public void setPaid(boolean paid) {
		Paid = paid;
	}

}
