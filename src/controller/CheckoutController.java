package controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import model.*;
import singleton.*;

import com.jfoenix.controls.JFXTextField;

import application.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
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
		isbn_10.setText(current_book.getIsbn());
		isbn_13.setText(current_book.getISBN13());
		title.setText(current_book.getTitle());
		author.setText(current_book.getAuthor());
		publisher.setText(current_book.getPublisher());
		if (current_book.isChecked_out()) {
			availability.setText("No");
		} else {
			availability.setText("Yes");
		}
		card_no_input.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				if (!newValue.matches("\\d*")) {
					card_no_input.setText(newValue.replaceAll("[^\\d]", ""));
				} else if (newValue.length() > 6) {
					card_no_input.setText(oldValue);
				}
			}
		});
		card_no_input.setOnAction(e -> {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirm Checkout");
			alert.setHeaderText("Confirmation of Checking out this book");
			alert.setContentText("Are you sure you want to check out this book?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				try {
					if (SingletonController.getInstance().getSql_connector().insertLoan(current_book,
							Integer.parseInt(card_no_input.getText()))) {
						Singleton.getInstance().setDecline_message("Successfully checkout this book");
						SingletonController.getInstance().refresh_data();
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
			}
		});
	}

}
