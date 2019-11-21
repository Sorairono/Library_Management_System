package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Book;
import singleton.Singleton;
import singleton.SingletonBooks;
import singleton.SingletonController;

public class CheckoutController implements Initializable {

	double x, y;

	@FXML
	void draged(MouseEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setX(event.getScreenX() - x);
		stage.setY(event.getScreenY() - y);
	}

	@FXML
	void pressed(MouseEvent event) {
		x = event.getSceneX();
		y = event.getSceneY();
	}

	@FXML
	private Label isbn_10;
	@FXML
	private Label isbn_13;
	@FXML
	private Label title;
	@FXML
	private Label author;
	@FXML
	private Label publisher;
	@FXML
	private Label availability;
	@FXML
	private JFXTextField card_no_input;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		Book current_book = SingletonBooks.getInstance().getCurrent_book_choice();
		isbn_10.setText(current_book.getISBN10());
		isbn_13.setText(current_book.getISBN13());
		title.setText(current_book.getTitle());
		publisher.setText(current_book.getPublisher());
		card_no_input.setOnAction(e -> {
			try {
				SingletonController.getInstance().getSql_connector().insertLoan(current_book, Integer.parseInt(card_no_input.getText()));
				System.out.println("Book successfully checked out");
			} catch (SQLException ex) {
				// TODO: handle exception
				System.out.println("AAAA");
			}
			if (card_no_input.getText().compareTo("0") == 0) {
				Singleton.getInstance().setDecline_message("This user already borrowed 3 books");
				try {
					FXMLLoader fourthLoader = new FXMLLoader(getClass().getResource("/fxml_document/DeclinePopup.fxml"));
					Parent fourthUI = fourthLoader.load();
					Stage dialogStage = new Stage();
					dialogStage.setTitle("Checkout book failed");
					dialogStage.initOwner(Main.getInstance().getPrimaryStage());
					dialogStage.initModality(Modality.WINDOW_MODAL);
					Scene scene = new Scene(fourthUI);
					dialogStage.setScene(scene);
					dialogStage.show();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}	
			} else {
				current_book.setChecked_out(true);
			}
		});
	}
	
}
