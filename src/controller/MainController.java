package controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTabPane;

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
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		SingletonController.getInstance().setMain_controller(this);
		tab_pane_main.getTabs().addAll(tab_books, tab_borrowers, tab_loans);
		tab_pane_main.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		
		try {
			FXMLLoader secondLoader = new FXMLLoader(getClass().getResource("/fxml_document/TabBooksView.fxml"));
			Parent secondUI = secondLoader.load();
			SingletonController.getInstance().setTab_books_controller(secondLoader.getController());
			tab_books.setContent(secondUI);
		} catch (Exception e) {

		}
		
		try {
			FXMLLoader secondLoader = new FXMLLoader(getClass().getResource("/fxml_document/TabBorrowersView.fxml"));
			Parent secondUI = secondLoader.load();
			SingletonController.getInstance().setTab_borrowers_controller(secondLoader.getController());
			tab_borrowers.setContent(secondUI);
		} catch (Exception e) {

		}
		
		try {
			FXMLLoader secondLoader = new FXMLLoader(getClass().getResource("/fxml_document/TabLoansView.fxml"));
			Parent secondUI = secondLoader.load();
			SingletonController.getInstance().setTab_loans_controller(secondLoader.getController());
			tab_loans.setContent(secondUI);
		} catch (Exception e) {

		}
	}

}
