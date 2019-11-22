package controller;

import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import core.SQLConnector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Loan;
import singleton.SingletonController;

public class TabLoansController implements Initializable {

	@FXML
	private TableView<Loan> tb_loans;
	@FXML
	private JFXTextField tx_search;
	private TableColumn<Loan, Integer> tc_loanId = new TableColumn<Loan, Integer>("Loan ID");
	private TableColumn<Loan, Integer> tc_isbn10 = new TableColumn<Loan, Integer>("ISBN10");
	private TableColumn<Loan, Integer> tc_isbn13 = new TableColumn<Loan, Integer>("ISBN13");
	private TableColumn<Loan, Integer> tc_borrowerId= new TableColumn<Loan, Integer>("Borrower ID");
	private TableColumn<Loan, Timestamp> tc_date_out = new TableColumn<Loan, Timestamp>("Issue Date");
	private TableColumn<Loan, Timestamp> tc_due_date = new TableColumn<Loan, Timestamp>("Due Date");
	private TableColumn<Loan, Timestamp> tc_date_in = new TableColumn<Loan, Timestamp>("Return Date");
	private ContextMenu contextMenu = new ContextMenu();
	final private MenuItem checkinMenu = new MenuItem("Check In");
	ObservableList<Loan> loans_list = null;
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		SQLConnector sql_connector = SingletonController.getInstance().getSql_connector();
		try {
			loans_list = FXCollections.observableArrayList(sql_connector.getLoanList());
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Failed to get loan list");
		}
		contextMenu.getItems().add(checkinMenu);
		tb_loans.getColumns().addAll(tc_loanId, tc_isbn10, tc_isbn13, tc_borrowerId, tc_date_out, tc_due_date, tc_date_in);
		tc_loanId.setCellValueFactory(new PropertyValueFactory<Loan, Integer>("Loan_id"));
		tc_isbn10.setCellValueFactory(new PropertyValueFactory<Loan, Integer>("ISBN10"));
		tc_isbn13.setCellValueFactory(new PropertyValueFactory<Loan, Integer>("ISBN13"));
		tc_borrowerId.setCellValueFactory(new PropertyValueFactory<Loan, Integer>("Borrower_id"));
		tc_date_out.setCellValueFactory(new PropertyValueFactory<Loan, Timestamp>("Date_out"));
		tc_due_date.setCellValueFactory(new PropertyValueFactory<Loan, Timestamp>("Due_date"));
		tc_date_in.setCellValueFactory(new PropertyValueFactory<Loan, Timestamp>("Date_in"));
		tb_loans.setItems(loans_list);
	}

}
