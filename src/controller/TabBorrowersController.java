package controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import core.SQLConnector;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Borrower;
import singleton.Singleton;
import singleton.SingletonController;

public class TabBorrowersController implements Initializable {

	@FXML
	private TableView<Borrower> tb_borrowers;
	@FXML
	private TextField tf_search;

	private TableColumn<Borrower, Integer> tc_borrower_id = new TableColumn<Borrower, Integer>("Borrower ID");
	private TableColumn<Borrower, String> tc_ssn = new TableColumn<Borrower, String>("SSN");
	private TableColumn<Borrower, String> tc_name = new TableColumn<Borrower, String>("Name");
	private TableColumn<Borrower, String> tc_email = new TableColumn<Borrower, String>("E-mail");
	private TableColumn<Borrower, String> tc_address = new TableColumn<Borrower, String>("Address");
	private TableColumn<Borrower, String> tc_phone = new TableColumn<Borrower, String>("Phone");
//	private ContextMenu context_menu = new ContextMenu();

	ObservableList<Borrower> borrowers_list = null;
	SQLConnector sql_connector = null;

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		sql_connector = SingletonController.getInstance().getSql_connector();
		tb_borrowers.getColumns().addAll(tc_borrower_id, tc_ssn, tc_name, tc_email, tc_address, tc_phone);
		tc_borrower_id.prefWidthProperty().bind(tb_borrowers.widthProperty().multiply(0.07));
		tc_borrower_id.setCellValueFactory(new PropertyValueFactory<Borrower, Integer>("Card_id"));
		tc_ssn.prefWidthProperty().bind(tb_borrowers.widthProperty().multiply(0.13));
		tc_ssn.setCellValueFactory(new PropertyValueFactory<Borrower, String>("Ssn"));
		tc_name.prefWidthProperty().bind(tb_borrowers.widthProperty().multiply(0.2));
		tc_name.setCellValueFactory(new PropertyValueFactory<Borrower, String>("Bname"));
		tc_email.prefWidthProperty().bind(tb_borrowers.widthProperty().multiply(0.2));
		tc_email.setCellValueFactory(new PropertyValueFactory<Borrower, String>("Email"));
		tc_address.prefWidthProperty().bind(tb_borrowers.widthProperty().multiply(0.2));
		tc_address.setCellValueFactory(new PropertyValueFactory<Borrower, String>("Address"));
		tc_phone.prefWidthProperty().bind(tb_borrowers.widthProperty().multiply(0.2));
		tc_phone.setCellValueFactory(new PropertyValueFactory<Borrower, String>("Phone"));
		tb_borrowers.setFixedCellSize(25);
		load_borrowers_list();
		tf_search.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				if (!newValue.matches("[a-zA-Z\\s]*")) {
					tf_search.setText(newValue.replaceAll("[^a-zA-Z\\s]", ""));
				} else if (newValue.length() > 40) {
					tf_search.setText(oldValue);
				}
			}
		});
		tf_search.setOnAction(e -> {
			try {
				borrowers_list = FXCollections
						.observableArrayList(sql_connector.searchBorrowerList(tf_search.getText()));
				tb_borrowers.setItems(borrowers_list);
				System.out.println("Success borrower search");
			} catch (Exception e2) {
				// TODO: handle exception
			}
		});
	}

	public void load_borrowers_list() {
		try {
			borrowers_list = FXCollections.observableArrayList(sql_connector.getBorrowerList());
			System.out.println("Imported Borrowers List");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Failed to import list");
		}
		tb_borrowers.setItems(borrowers_list);
		;
	}

	@FXML
	private void add_new_borrower() {
//		Borrower new_borrower = new Borrower(0, "", "", "", "", "", "", "", "");
//		SingletonChoice.getInstance().setCurrent_borrower_choice(new_borrower);
		try {
			FXMLLoader fourthLoader = new FXMLLoader(getClass().getResource("/fxml_document/BorrowerInfo.fxml"));
			Parent fourthUI = fourthLoader.load();
			Stage dialogStage = new Stage();
			Scene scene = new Scene(fourthUI);
			dialogStage.setScene(scene);
			dialogStage.setTitle("Add New Borrower");
			dialogStage.initOwner(Main.getInstance().getPrimaryStage());
			dialogStage.initModality(Modality.WINDOW_MODAL);
			Singleton.getInstance().setDialogStage(dialogStage);
			dialogStage.show();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Failed to open dialog to add");
		}
	}

}
