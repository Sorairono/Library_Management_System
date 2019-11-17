package singleton;

import controller.*;

public class SingletonController {
	private static SingletonController instance;
	
	private SingletonController() {
		
	}
	
	public static SingletonController getInstance() {
		if (instance == null) {
			synchronized (SingletonController.class) {
				if (instance == null) {
					instance = new SingletonController();
				}
			}
		}
		return instance;
	}
	
	private MainController main_controller;
	private TabBooksController tab_books_controller;
	private TabBorrowersController tab_borrowers_controller;
	private TabLoansController tab_loans_controller;
	
	public MainController getMain_controller() {
		return main_controller;
	}

	public void setMain_controller(MainController main_controller) {
		this.main_controller = main_controller;
	}

	public TabBooksController getTab_books_controller() {
		return tab_books_controller;
	}

	public void setTab_books_controller(TabBooksController tab_books_controller) {
		this.tab_books_controller = tab_books_controller;
	}

	public TabBorrowersController getTab_borrowers_controller() {
		return tab_borrowers_controller;
	}

	public void setTab_borrowers_controller(TabBorrowersController tab_borrowers_controller) {
		this.tab_borrowers_controller = tab_borrowers_controller;
	}

	public TabLoansController getTab_loans_controller() {
		return tab_loans_controller;
	}

	public void setTab_loans_controller(TabLoansController tab_loans_controller) {
		this.tab_loans_controller = tab_loans_controller;
	}
	
}
