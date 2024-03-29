package singleton;

import controller.*;
import core.SQLConnector;

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
	private TabFinesController tab_fines_controller;

	public TabFinesController getTab_fines_controller() {
		return tab_fines_controller;
	}

	public void setTab_fines_controller(TabFinesController tab_fines_controller) {
		this.tab_fines_controller = tab_fines_controller;
	}

	private SQLConnector sql_connector;
	private CheckoutController checkout_controller;

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

	public SQLConnector getSql_connector() {
		return sql_connector;
	}

	public void setSql_connector(SQLConnector sql_connector) {
		this.sql_connector = sql_connector;
	}

	public CheckoutController getCheckout_controller() {
		return checkout_controller;
	}

	public void setCheckout_controller(CheckoutController checkout_controller) {
		this.checkout_controller = checkout_controller;
	}

	public void refresh_data() {
		tab_books_controller.load_books_list();
		tab_borrowers_controller.load_borrowers_list();
		tab_loans_controller.load_loans_list();
		tab_fines_controller.load_borrower_list();
	}
}
