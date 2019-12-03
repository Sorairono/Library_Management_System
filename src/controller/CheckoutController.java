package controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import model.*;
import singleton.*;

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
		Book current_book = SingletonChoice.getInstance().getCurrent_book_choice();
		isbn_10.setText(current_book.getISBN10());
		isbn_13.setText(current_book.getISBN13());
		title.setText(current_book.getTitle());
		author.setText(current_book.getAuthor());
		publisher.setText(current_book.getPublisher());
		if (current_book.isChecked_out()) {
			availability.setText("No");
		} else {
			availability.setText("Yes");
		}
		card_no_input.setOnAction(e -> {
			try {
				if (SingletonController.getInstance().getSql_connector().insertLoan(current_book,
						Integer.parseInt(card_no_input.getText()))) {
					Singleton.getInstance().setDecline_message("Successfully checkout this book");
					try {
						FXMLLoader fourthLoader = new FXMLLoader(
								getClass().getResource("/fxml_document/MessagePopup.fxml"));
						Parent fourthUI = fourthLoader.load();
						Stage dialogStage = new Stage();
						dialogStage.setTitle("Checkout book successful");
						dialogStage.initOwner(Main.getInstance().getPrimaryStage());
						dialogStage.initModality(Modality.WINDOW_MODAL);
						Scene scene = new Scene(fourthUI);
						dialogStage.setScene(scene);
						dialogStage.show();
					} catch (Exception ex) {

					}
				} else {
					try {
						FXMLLoader fourthLoader = new FXMLLoader(
								getClass().getResource("/fxml_document/MessagePopup.fxml"));
						Parent fourthUI = fourthLoader.load();
						Stage dialogStage = new Stage();
						dialogStage.setTitle("Checkout book failed");
						dialogStage.initOwner(Main.getInstance().getPrimaryStage());
						dialogStage.initModality(Modality.WINDOW_MODAL);
						Scene scene = new Scene(fourthUI);
						dialogStage.setScene(scene);
						dialogStage.show();
					} catch (Exception ex) {

					}
				}
			} catch (SQLException ex) {

			}
		});
	}

}
