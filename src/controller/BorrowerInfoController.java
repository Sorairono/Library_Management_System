package controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import application.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Borrower;
import singleton.Singleton;
import singleton.SingletonController;

public class BorrowerInfoController implements Initializable {

	@FXML
	private JFXTextField tf_first_name;
	@FXML
	private JFXTextField tf_last_name;
	@FXML
	private JFXTextField tf_ssn;
	@FXML
	private JFXTextField tf_email;
	@FXML
	private JFXTextField tf_address;
	@FXML
	private JFXTextField tf_city;
	@FXML
	private JFXTextField tf_state;
	@FXML
	private JFXTextField tf_phone;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		tf_first_name.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				if (!newValue.matches("[a-zA-Z\\s]*")) {
					tf_first_name.setText(newValue.replaceAll("[^a-zA-Z\\s]", ""));
				} else if (newValue.length() > 40) {
					tf_first_name.setText(oldValue);
				}
			}
		});
		tf_last_name.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				if (!newValue.matches("[a-zA-Z\\s]*")) {
					tf_last_name.setText(newValue.replaceAll("[^a-zA-Z\\s]", ""));
				} else if (newValue.length() > 40) {
					tf_last_name.setText(oldValue);
				}
			}
		});
		tf_email.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				if (newValue.length() > 100) {
					tf_email.setText(oldValue);
				}
			}
		});
		tf_address.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				if (newValue.length() > 100) {
					tf_address.setText(oldValue);
				}
			}
		});
		tf_city.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				if (!newValue.matches("[a-zA-Z\\s]*")) {
					tf_city.setText(newValue.replaceAll("[^a-zA-Z\\s]", ""));
				} else if (newValue.length() > 20) {
					tf_city.setText(oldValue);
				}
			}
		});
		tf_state.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				if (!newValue.matches("[A-Z\\s]*")) {
					tf_state.setText(newValue.replaceAll("[^A-Z\\s]", ""));
				} else if (newValue.length() > 10) {
					tf_state.setText(oldValue);
				}
			}
		});
		tf_ssn.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				if (!newValue.matches("\\d*")) {
					tf_ssn.setText(newValue.replaceAll("[^\\d]", ""));
				} else if (newValue.length() > 9) {
					tf_ssn.setText(oldValue);
				}
			}
		});
		tf_phone.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				if (!newValue.matches("\\d*")) {
					tf_phone.setText(newValue.replaceAll("[^\\d]", ""));
				} else if (newValue.length() > 10) {
					tf_phone.setText(oldValue);
				}
			}
		});

	}

	private String correct_format_phone(String phone_no) {
		int size = phone_no.length();
		String formatted_no = "(";
		for (int i = 0; i < size; i++) {

			if (i == 3) {
				formatted_no += ")" + " ";
			}
			if (i == 6) {
				formatted_no += "-";
			}
			formatted_no += phone_no.charAt(i);
		}
		return formatted_no;
	}

	private String correct_format_ssn(String ssn) {
		int size = ssn.length();
		String formatted_ssn = "";
		for (int i = 0; i < size; i++) {

			if (i == 3) {
				formatted_ssn += "-";
			}
			if (i == 5) {
				formatted_ssn += "-";
			}
			formatted_ssn += ssn.charAt(i);
		}
		return formatted_ssn;
	}

	@FXML
	private void on_cancel() {
		Singleton.getInstance().getDialogStage().close();
	}

	@FXML
	private void on_ok() {
		if (textfield_validity_check()) {
			String ssn = correct_format_ssn(tf_ssn.getText());
			String first_name = tf_first_name.getText();
			String last_name = tf_last_name.getText();
			String email = tf_email.getText();
			String address = tf_address.getText();
			String city = tf_city.getText();
			String state = tf_state.getText();
			String phone = correct_format_phone(tf_phone.getText());
			Borrower new_borrower = new Borrower(0, ssn, first_name, last_name, email, address, city, state, phone);
			try {
				if (SingletonController.getInstance().getSql_connector().insertBorrower(new_borrower)) {
					Singleton.getInstance().setDecline_message("Successfully added new borrower");
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
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private boolean textfield_validity_check() {
		if (tf_ssn.getText().length() != 9) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(Singleton.getInstance().getDialogStage());
			alert.initModality(Modality.WINDOW_MODAL);
			alert.setTitle("Invalid Fields");
			alert.setHeaderText("Please correct invalid fields");
			alert.setContentText("Please enter a 9-digit SSN");
			alert.showAndWait();
			return false;
		}
		if (tf_phone.getText().length() != 10) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(Singleton.getInstance().getDialogStage());
			alert.initModality(Modality.WINDOW_MODAL);
			alert.setTitle("Invalid Fields");
			alert.setHeaderText("Please correct invalid fields");
			alert.setContentText("Please enter a 10-digit phone number");
			alert.showAndWait();
			return false;
		}
		return true;
	}

}
