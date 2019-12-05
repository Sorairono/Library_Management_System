package controller;

import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import core.SQLConnector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ContextMenuEvent;
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
	private TableColumn<Loan, Integer> tc_borrowerId = new TableColumn<Loan, Integer>("Borrower ID");
	private TableColumn<Loan, String> tc_borrower_name = new TableColumn<Loan, String>("Borrower Name");
	private TableColumn<Loan, Timestamp> tc_date_out = new TableColumn<Loan, Timestamp>("Issue Date");
	private TableColumn<Loan, Timestamp> tc_due_date = new TableColumn<Loan, Timestamp>("Due Date");
	private TableColumn<Loan, Timestamp> tc_date_in = new TableColumn<Loan, Timestamp>("Return Date");
	private ContextMenu contextMenu = new ContextMenu();
	final private MenuItem checkinMenu = new MenuItem("Check In");
	ObservableList<Loan> loans_list = null;
	SQLConnector sql_connector = null;

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		sql_connector = SingletonController.getInstance().getSql_connector();
		contextMenu.getItems().add(checkinMenu);
		checkinMenu.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				int current_loan_id = tb_loans.getSelectionModel().getSelectedItem().getLoan_id();
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Confirm Check In");
				alert.setHeaderText("Confirmation of Checking In a book");
				alert.setContentText("Are you sure you want to check in loan_id " + current_loan_id + "?");
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {
					try {
						sql_connector.check_in_book(current_loan_id);
						SingletonController.getInstance().refresh_data();
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}
		});
		tb_loans.getColumns().addAll(tc_loanId, tc_isbn10, tc_isbn13, tc_borrowerId, tc_borrower_name, tc_date_out, tc_due_date,
				tc_date_in);
		tc_loanId.setCellValueFactory(new PropertyValueFactory<Loan, Integer>("Loan_id"));
		tc_isbn10.setCellValueFactory(new PropertyValueFactory<Loan, Integer>("Isbn"));
		tc_isbn13.setCellValueFactory(new PropertyValueFactory<Loan, Integer>("ISBN13"));
		tc_borrowerId.setCellValueFactory(new PropertyValueFactory<Loan, Integer>("Card_id"));
		tc_borrower_name.setCellValueFactory(new PropertyValueFactory<Loan, String>("borrower_name"));
		tc_date_out.setCellValueFactory(new PropertyValueFactory<Loan, Timestamp>("Date_out"));
		tc_due_date.setCellValueFactory(new PropertyValueFactory<Loan, Timestamp>("Due_date"));
		tc_date_in.setCellValueFactory(new PropertyValueFactory<Loan, Timestamp>("Date_in"));
		tc_loanId.prefWidthProperty().bind(tb_loans.widthProperty().multiply(0.07));
		tc_isbn10.prefWidthProperty().bind(tb_loans.widthProperty().multiply(0.13));
		tc_isbn13.prefWidthProperty().bind(tb_loans.widthProperty().multiply(0.13));
		tc_borrowerId.prefWidthProperty().bind(tb_loans.widthProperty().multiply(0.07));
		tc_borrower_name.prefWidthProperty().bind(tb_loans.widthProperty().multiply(0.15));
		tc_date_out.prefWidthProperty().bind(tb_loans.widthProperty().multiply(0.15));
		tc_due_date.prefWidthProperty().bind(tb_loans.widthProperty().multiply(0.15));
		tc_date_in.prefWidthProperty().bind(tb_loans.widthProperty().multiply(0.15));
		load_loans_list();
		tb_loans.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
			@Override
			public void handle(ContextMenuEvent event) {
				// TODO Auto-generated method stub
				contextMenu.show(tb_loans, event.getScreenX(), event.getScreenY());
			}
		});
		tx_search.setOnAction(e -> {
			try {
				loans_list = FXCollections.observableArrayList(sql_connector.searchLoanList(loan_search_command(tx_search.getText())));
				System.out.println("Search successfully");
				tb_loans.setItems(loans_list);
			} catch (Exception e2) {
				// TODO: handle exception
				System.out.println("Search Failed");
			}
		});
	}

	public void load_loans_list() {
		try {
			loans_list = FXCollections.observableArrayList(sql_connector.getLoanList());
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Failed to get loan list");
		}
		tb_loans.setItems(loans_list);
	}

	public static String loan_search_command(String search_string) {
		String[] str = search_string.split(" ");
		ArrayList<String> searchThese = new ArrayList<>(); // these are the values that will be put into a query
		ArrayList<String> combinations = new ArrayList<>();
		for (int i = 0; i < str.length; i++) {
			if (str[i].length() == 10 || str[i].length() == 13) {
				// to look for the book id (the isbn)
				if (str[i].matches("\\d\\d\\d\\d\\d\\d\\d\\d\\d[\\d]+")) {
					searchThese.add(0, "%" + str[i] + "%");
				}
			} else if (str[i].matches("[\\d]+")) {
				// to look for the borrower card #
				searchThese.add(0, "%" + str[i] + "%");
			} else {
				// if the value is not an isbn value
				combinations.add(str[i]);
			}

		}
		int j = combinations.size();
		while (j > 0) {
			for (int i = 0; i < combinations.size(); i++) {
				int size = combinations.size() - j + 1;
				for (int k = 0; k < size; k++) {
					String search_str = "";

					for (int l = 0; l < j; l++) {
						search_str = search_str + "%" + combinations.get(k + l);
					}
					search_str = search_str + "%";
					searchThese.add(search_str);
				}
				j--;
			}
		}
		System.out.println(searchThese);
		int s = searchThese.size();
		String command = "";
		if (searchThese.get(0).matches("%[\\d]+%") && searchThese.get(0).length() >= 10) {
			command += "(SELECT Bname, Loan_id, Isbn, ISBN13, Card_id, Date_out, Due_date, Date_in FROM BOOK_LOANS NATURAL JOIN BORROWER WHERE Isbn LIKE '"
					+ searchThese.get(0) + "' OR ISBN13 LIKE '" + searchThese.get(0) + "') ";
		} else if (searchThese.get(0).matches("%[\\d]+%")) {
			command += "(SELECT Bname, Loan_id, Isbn, ISBN13, Card_id, Date_out, Due_date, Date_in FROM BOOK_LOANS NATURAL JOIN BORROWER WHERE Card_id LIKE '"
					+ searchThese.get(0) + "') ";
		} else {
			command += "(SELECT Bname, Loan_id, Isbn, ISBN13, Card_id, Date_out, Due_date, Date_in FROM BOOK_LOANS NATURAL JOIN BORROWER WHERE Bname LIKE '"
					+ searchThese.get(0) + "') ";
		}
		for (int i = 1; i < searchThese.size(); i++) {
			if (searchThese.get(i).matches("%[\\d]+%") && searchThese.get(i).length() >= 10) {
				command += "UNION (SELECT Bname, Loan_id, Isbn, ISBN13, Card_id, Date_out, Due_date, Date_in FROM BOOK_LOANS NATURAL JOIN BORROWER WHERE Isbn LIKE '"
						+ searchThese.get(i) + "' OR ISBN13 LIKE '" + searchThese.get(i) + "') ";
			} else if (searchThese.get(i).matches("%[\\d]+%")) {
				command += "UNION (SELECT Bname, Loan_id, Isbn, ISBN13, Card_id, Date_out, Due_date, Date_in FROM BOOK_LOANS NATURAL JOIN BORROWER WHERE Card_id LIKE '"
						+ searchThese.get(i) + "') ";
			} else {
				command += "UNION (SELECT Bname, Loan_id, Isbn, ISBN13, Card_id, Date_out, Due_date, Date_in FROM BOOK_LOANS NATURAL JOIN BORROWER WHERE Bname LIKE '"
						+ searchThese.get(i) + "') ";
			}
		}
		System.out.println(command);
		return command;
	}
}
