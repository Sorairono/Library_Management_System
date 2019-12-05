package controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import core.SQLConnector;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ContextMenuEvent;
import model.*;
import singleton.SingletonController;

public class TabFinesController implements Initializable {

	@FXML
	private TableView<Fine> tv_fines;
	@FXML
	private TableView<Borrower> tv_borrowers;
	@FXML
	private JFXTextField tf_search;
	@FXML
	private JFXComboBox<String> cb_filter;
	@FXML
	private Label lb_total;
	private TableColumn<Borrower, Integer> tc_card_id = new TableColumn<Borrower, Integer>("Borrower's card ID");
	private TableColumn<Borrower, String> tc_name = new TableColumn<Borrower, String>("Name");
	private TableColumn<Fine, Integer> tc_loan_id = new TableColumn<Fine, Integer>("Loan ID");
	private TableColumn<Fine, String> tc_isbn = new TableColumn<Fine, String>("ISBN");
	private TableColumn<Fine, Double> tc_fine_amt = new TableColumn<Fine, Double>("Fine Amount");
	private TableColumn<Fine, String> tc_paid = new TableColumn<Fine, String>("Is Paid");
	private ContextMenu contextMenu = new ContextMenu();
	final private MenuItem checkoutMenu = new MenuItem("Fines Details");

	SQLConnector sql_connector = null;
	ObservableList<Fine> fines_list = null;
	ObservableList<Borrower> borrower_list = null;
	ObservableList<String> cb_options = FXCollections.observableArrayList("Display all fines", "Display not-paid fines",
			"Display fines can be paid");

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		sql_connector = SingletonController.getInstance().getSql_connector();
		contextMenu.getItems().addAll(checkoutMenu);
		checkoutMenu.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				load_fines_list();
			}
		});
		cb_filter.setItems(cb_options);
		cb_filter.getSelectionModel().selectFirst();
		cb_filter.getSelectionModel().selectedItemProperty().addListener(c -> {
			if (tv_borrowers.getSelectionModel().getSelectedIndex() >= 0) {
				load_fines_list();
			}
		});
		tv_borrowers.getColumns().addAll(tc_card_id, tc_name);
		tv_fines.getColumns().addAll(tc_loan_id, tc_isbn, tc_fine_amt, tc_paid);
		tc_card_id.setCellValueFactory(new PropertyValueFactory<Borrower, Integer>("Card_id"));
		tc_card_id.prefWidthProperty().bind(tv_borrowers.widthProperty().multiply(0.3));
		tc_name.setCellValueFactory(new PropertyValueFactory<Borrower, String>("Bname"));
		tc_name.prefWidthProperty().bind(tv_borrowers.widthProperty().multiply(0.7));
		tc_loan_id.setCellValueFactory(new PropertyValueFactory<Fine, Integer>("Loan_id"));
		tc_loan_id.prefWidthProperty().bind(tv_fines.widthProperty().multiply(0.1));
		tc_isbn.setCellValueFactory(new PropertyValueFactory<Fine, String>("Isbn"));
		tc_isbn.prefWidthProperty().bind(tv_fines.widthProperty().multiply(0.4));
		tc_fine_amt.setCellValueFactory(new PropertyValueFactory<Fine, Double>("Fine_amt"));
		tc_fine_amt.prefWidthProperty().bind(tv_fines.widthProperty().multiply(0.3));
		tc_paid.setCellValueFactory(c -> {
			boolean is_paid = c.getValue().isPaid();
			String is_paid_string;
			if (is_paid) {
				is_paid_string = "Yes";
			} else {
				is_paid_string = "No";
			}
			return new ReadOnlyStringWrapper(is_paid_string);
		});
		tc_paid.prefWidthProperty().bind(tv_fines.widthProperty().multiply(0.2));
		tv_borrowers.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
			@Override
			public void handle(ContextMenuEvent event) {
				// TODO Auto-generated method stub
				contextMenu.show(tv_borrowers, event.getScreenX(), event.getScreenY());
			}
		});
		tf_search.setOnAction(e -> {
			try {
				borrower_list = FXCollections
						.observableArrayList(sql_connector.searchBorrowerList(tf_search.getText()));
				tv_borrowers.setItems(borrower_list);
				tv_fines.getItems().clear();
				lb_total.setText("");
				System.out.println("Success borrower search");
			} catch (Exception e2) {
				// TODO: handle exception
			}
		});
		load_borrower_list();
	}

	@FXML
	private void on_pay_fines() {
		if (cb_filter.getSelectionModel().getSelectedIndex() == 2
				&& tv_borrowers.getSelectionModel().getSelectedIndex() >= 0) {
			int current_borrower_id = tv_borrowers.getSelectionModel().getSelectedItem().getCard_id();
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirm Fine Payment");
			alert.setHeaderText("Confirmation of Paying all the available fines");
			alert.setContentText("Are you sure borrower with card number " + current_borrower_id + " has paid $"
					+ lb_total.getText() + "?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				try {
					sql_connector.borrower_pay_fine(current_borrower_id);
					SingletonController.getInstance().refresh_data();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else if (tv_borrowers.getSelectionModel().getSelectedIndex() == -1) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Fine Payment");
			alert.setHeaderText("Error in paying the fines");
			alert.setContentText("Please choose a borrower to pay the fines first");
			alert.show();
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Fine Payment");
			alert.setHeaderText("Error in paying the fines");
			alert.setContentText("Please set the filter to the last option to display the fines borrower has to pay.");
			alert.show();
		}
	}

	public void load_fines_list() {
		try {
			fines_list = FXCollections.observableArrayList(
					sql_connector.getFineList(tv_borrowers.getSelectionModel().getSelectedItem().getCard_id(),
							cb_filter.getSelectionModel().getSelectedIndex()));
			tv_fines.setItems(fines_list);
		} catch (Exception e) {
			// TODO: handle exception

		}
		double total_fine = 0;
		for (int i = 0; i < fines_list.size(); i++) {
			total_fine += fines_list.get(i).getFine_amt();
		}
		lb_total.setText("$ " + total_fine);
	}

	public void load_borrower_list() {
		tf_search.setText("");
		tv_fines.getItems().clear();
		try {
			borrower_list = FXCollections.observableArrayList(sql_connector.getBorrowerList());
			tv_borrowers.setItems(borrower_list);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
