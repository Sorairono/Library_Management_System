package controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import core.SQLConnector;
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
	private TableView<Borrower> tv_borrowers;
	@FXML
	private TextField tx_search;

	private TableColumn<Borrower, Integer> tc_borrower_id = new TableColumn<Borrower, Integer>("Borrower ID");
	private TableColumn<Borrower, String> tc_ssn = new TableColumn<Borrower, String>("SSN");
	private TableColumn<Borrower, String> tc_first_name = new TableColumn<Borrower, String>("First Name");
	private TableColumn<Borrower, String> tc_last_name = new TableColumn<Borrower, String>("Last Name");
	private TableColumn<Borrower, String> tc_email = new TableColumn<Borrower, String>("E-mail");
	private TableColumn<Borrower, String> tc_address = new TableColumn<Borrower, String>("Address");
	private TableColumn<Borrower, String> tc_city = new TableColumn<Borrower, String>("City");
	private TableColumn<Borrower, String> tc_state = new TableColumn<Borrower, String>("State");
	private TableColumn<Borrower, String> tc_phone = new TableColumn<Borrower, String>("Phone");
//	private ContextMenu context_menu = new ContextMenu();
	
	ObservableList<Borrower> borrowers_list = null;
	SQLConnector sql_connector = null;

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		sql_connector = SingletonController.getInstance().getSql_connector();
		tv_borrowers.getColumns().addAll(tc_borrower_id, tc_ssn, tc_first_name, tc_last_name, tc_email, tc_address,
				tc_city, tc_state, tc_phone);
		tc_borrower_id.prefWidthProperty().bind(tv_borrowers.widthProperty().multiply(0.05));
		tc_borrower_id.setCellValueFactory(new PropertyValueFactory<Borrower, Integer>("borrower_id"));
		tc_ssn.prefWidthProperty().bind(tv_borrowers.widthProperty().multiply(0.1));
		tc_ssn.setCellValueFactory(new PropertyValueFactory<Borrower, String>("ssn"));
		tc_first_name.prefWidthProperty().bind(tv_borrowers.widthProperty().multiply(0.1));
		tc_first_name.setCellValueFactory(new PropertyValueFactory<Borrower, String>("first_name"));
		tc_last_name.prefWidthProperty().bind(tv_borrowers.widthProperty().multiply(0.1));
		tc_last_name.setCellValueFactory(new PropertyValueFactory<Borrower, String>("last_name"));
		tc_email.prefWidthProperty().bind(tv_borrowers.widthProperty().multiply(0.2));
		tc_email.setCellValueFactory(new PropertyValueFactory<Borrower, String>("email"));
		tc_address.prefWidthProperty().bind(tv_borrowers.widthProperty().multiply(0.2));
		tc_address.setCellValueFactory(new PropertyValueFactory<Borrower, String>("address"));
		tc_city.prefWidthProperty().bind(tv_borrowers.widthProperty().multiply(0.1));
		tc_city.setCellValueFactory(new PropertyValueFactory<Borrower, String>("city"));
		tc_state.prefWidthProperty().bind(tv_borrowers.widthProperty().multiply(0.05));
		tc_state.setCellValueFactory(new PropertyValueFactory<Borrower, String>("state"));
		tc_phone.prefWidthProperty().bind(tv_borrowers.widthProperty().multiply(0.1));
		tc_phone.setCellValueFactory(new PropertyValueFactory<Borrower, String>("phone"));
		tv_borrowers.setFixedCellSize(25);
		load_borrowers_list();
	}
	
	public void load_borrowers_list() {
		try {
			borrowers_list = FXCollections.observableArrayList(sql_connector.getBorrowerList());
			System.out.println("Imported Borrowers List");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Failed to import list");
		}
		tv_borrowers.setItems(borrowers_list);;
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
			dialogStage.showAndWait();
			SingletonController.getInstance().refresh_data();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Failed to open dialog to add");
		}
	}

}
