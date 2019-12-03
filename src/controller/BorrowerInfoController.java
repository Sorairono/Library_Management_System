package controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import model.Borrower;
import singleton.Singleton;
import singleton.SingletonChoice;

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
				}
			}
		});
		tf_last_name.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				if (!newValue.matches("[a-zA-Z\\s]*")) {
					tf_last_name.setText(newValue.replaceAll("[^a-zA-Z\\s]", ""));
				}
			}
		});
		tf_email.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				if (!newValue.matches("[a-zA-Z\\s]*")) {
					tf_email.setText(newValue.replaceAll("[^a-zA-Z\\s]", ""));
				}
			}
		});
		tf_address.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				if (!newValue.matches("[a-zA-Z\\s]*")) {
					tf_address.setText(newValue.replaceAll("[^a-zA-Z\\s]", ""));
				}
			}
		});
		tf_city.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				if (!newValue.matches("[a-zA-Z\\s]*")) {
					tf_city.setText(newValue.replaceAll("[^a-zA-Z\\s]", ""));
				}
			}
		});
		tf_state.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				if (!newValue.matches("[A-Z\\s]*")) {
					tf_state.setText(newValue.replaceAll("[^A-Z\\s]", ""));
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
					tf_ssn.setText(newValue.replaceAll("[^\\d]", ""));
				} else if (newValue.length() > 10) {
					tf_ssn.setText(oldValue);
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

	}
	
//	private boolean textfield_validity_check() {
//		if ()
//	}4

}
