package controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTabPane;

import core.SQLConnector;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane.TabClosingPolicy;
import singleton.SingletonController;

public class MainController implements Initializable {

	@FXML
	private JFXTabPane tab_pane_main;
	private Tab tab_books = new Tab("Books");
	private Tab tab_borrowers = new Tab("Borrowers");;
	private Tab tab_loans = new Tab("Loans");
	private Tab tab_fines = new Tab("Fines");
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		try {
			SQLConnector sql_connector = new SQLConnector("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/Library?useSSL=false", "root", "89621139chan");
			SingletonController.getInstance().setSql_connector(sql_connector);
			System.out.println("Successful connection established");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Failed to connect");
		}
		SingletonController.getInstance().setMain_controller(this);
		tab_pane_main.getTabs().addAll(tab_books, tab_borrowers, tab_loans, tab_fines);
		tab_pane_main.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		
		try {
			FXMLLoader secondLoader = new FXMLLoader(getClass().getResource("/fxml_document/TabBooksView.fxml"));
			Parent secondUI = secondLoader.load();
			SingletonController.getInstance().setTab_books_controller(secondLoader.getController());
			tab_books.setContent(secondUI);
			System.out.println("Tab Books created");
		} catch (Exception e) {
			System.out.println("Failed 1");
		}
		
		try {
			FXMLLoader secondLoader = new FXMLLoader(getClass().getResource("/fxml_document/TabBorrowersView.fxml"));
			Parent secondUI = secondLoader.load();
			SingletonController.getInstance().setTab_borrowers_controller(secondLoader.getController());
			tab_borrowers.setContent(secondUI);
			System.out.println("Tab Borrowers created");
		} catch (Exception e) {
			System.out.println("Failed 2");

		}
		
		try {
			FXMLLoader secondLoader = new FXMLLoader(getClass().getResource("/fxml_document/TabLoansView.fxml"));
			Parent secondUI = secondLoader.load();
			SingletonController.getInstance().setTab_loans_controller(secondLoader.getController());
			tab_loans.setContent(secondUI);
			System.out.println("Tab Loans created");
		} catch (Exception e) {
			System.out.println("Failed 3");
		}
		
		try {
			FXMLLoader secondLoader = new FXMLLoader(getClass().getResource("/fxml_document/TabFinesView.fxml"));
			Parent secondUI = secondLoader.load();
			SingletonController.getInstance().setTab_fines_controller(secondLoader.getController());
			tab_fines.setContent(secondUI);
			System.out.println("Tab Fines created");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Failed 4");
		}
	}

}
