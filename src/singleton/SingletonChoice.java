package singleton;

import model.*;

public class SingletonChoice {
	private static SingletonChoice instance;

	private SingletonChoice() {

	}

	public static SingletonChoice getInstance() {
		if (instance == null) {
			synchronized (SingletonChoice.class) {
				if (instance == null) {
					instance = new SingletonChoice();
				}
			}
		}
		return instance;
	}

	private Book current_book_choice;
	private Loan current_loan_choice;
	private Borrower current_borrower_choice;

	public Book getCurrent_book_choice() {
		return current_book_choice;
	}

	public void setCurrent_book_choice(Book current_book_choice) {
		this.current_book_choice = current_book_choice;
	}

	public Loan getCurrent_loan_choice() {
		return current_loan_choice;
	}

	public void setCurrent_loan_choice(Loan current_loan_choice) {
		this.current_loan_choice = current_loan_choice;
	}

	public Borrower getCurrent_borrower_choice() {
		return current_borrower_choice;
	}

	public void setCurrent_borrower_choice(Borrower current_borrower_choice) {
		this.current_borrower_choice = current_borrower_choice;
	}

}
