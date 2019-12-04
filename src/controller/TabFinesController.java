package controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import model.Fine;

public class TabFinesController implements Initializable {

	@FXML
	private TableView<Fine> tb_fines;
	@FXML
	private JFXTextField tx_search;
	@FXML
	private JFXComboBox<String> cb_filter;
	@FXML
	private Label lb_total;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}
	
	@FXML
	private void on_pay_fines() {
		
	}

}
