package controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import model.*;
import singleton.*;

import com.jfoenix.controls.JFXTextField;

import application.Main;
import core.SQLConnector;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ContextMenuEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TabBooksController implements Initializable {

	@FXML
	private TableView<Book> tb_books;
	@FXML
	private JFXTextField tx_search;
	private TableColumn<Book, String> tc_isbn = new TableColumn<Book, String>("ISBN");
	private TableColumn<Book, String> tc_title = new TableColumn<Book, String>("Title");
	private TableColumn<Book, String> tc_author = new TableColumn<Book, String>("Author");
	private TableColumn<Book, String> tc_availability = new TableColumn<Book, String>("Availability");
	private ContextMenu contextMenu = new ContextMenu();
	final private MenuItem checkoutMenu = new MenuItem("Check Out");
	ObservableList<Book> books_list = null;
	SQLConnector sql_connector = null;

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
				SingletonChoice.getInstance().setCurrent_book_choice(tb_books.getSelectionModel().getSelectedItem());
				try {
					FXMLLoader thirdLoader = new FXMLLoader(
							getClass().getResource("/fxml_document/CheckoutPopup.fxml"));
					Parent thirdUI = thirdLoader.load();
					Scene scene = new Scene(thirdUI);
					Stage dialogStage = new Stage();
					dialogStage.setScene(scene);
					dialogStage.setTitle("Checkout Book");
					dialogStage.initOwner(Main.getInstance().getPrimaryStage());
					dialogStage.initModality(Modality.WINDOW_MODAL);
					Singleton.getInstance().setDialogStage(dialogStage);
//					dialogStage.setOnHiding(ev -> {
//						load_books_list();
//						System.out.println("Refreshed book list");
//					});
					dialogStage.showAndWait();
					load_books_list();
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
		tb_books.getColumns().addAll(tc_isbn, tc_title, tc_author, tc_availability);
		tc_isbn.prefWidthProperty().bind(tb_books.widthProperty().multiply(0.1));
		tc_title.prefWidthProperty().bind(tb_books.widthProperty().multiply(0.4));
		tc_author.prefWidthProperty().bind(tb_books.widthProperty().multiply(0.4));
		tc_availability.prefWidthProperty().bind(tb_books.widthProperty().multiply(0.1));
		tc_isbn.setCellValueFactory(new PropertyValueFactory<Book, String>("ISBN10"));
		tc_title.setCellValueFactory(new PropertyValueFactory<Book, String>("Title"));
		tc_author.setCellValueFactory(new PropertyValueFactory<Book, String>("Author"));
		tc_availability.setCellValueFactory(c -> {
			boolean is_checked_out = c.getValue().isChecked_out();
			String availability_string;
			if (is_checked_out) {
				availability_string = "No";
			} else {
				availability_string = "Yes";
			}
			return new ReadOnlyStringWrapper(availability_string);
		});
		load_books_list();
		tb_books.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
			@Override
			public void handle(ContextMenuEvent event) {
				// TODO Auto-generated method stub
				contextMenu.show(tb_books, event.getScreenX(), event.getScreenY());
			}
		});
		tx_search.setOnAction(e -> {
			System.out.println("entered");
			System.out.println(tx_search.getText());
			try {
				books_list = FXCollections
						.observableArrayList(sql_connector.searchBookList(search_command_parser(tx_search.getText())));
				System.out.println("Search successfully");
				tb_books.setItems(books_list);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				System.out.println("Failed to search");
				e1.printStackTrace();
			}
		});
	}

	public void load_books_list() {
		try {
			books_list = FXCollections.observableArrayList(sql_connector.getBookList());
		} catch (SQLException e) {
			System.out.println("Failed to import books list");
		}
		tb_books.setItems(books_list);
	}

	public String search_command_parser(String search_string) {
		String[] str = search_string.split(" ");
		ArrayList<String> searchThese = new ArrayList<>(); // these are the values that will be put into a query
		ArrayList<String> combinations = new ArrayList<>();

		for (int i = 0; i < str.length; i++) {
			if (str[i].length() == 10 || str[i].length() == 13) {
				if (str[i].matches("\\d\\d\\d\\d\\d\\d\\d\\d\\d[\\d]+")) {
					searchThese.add(0, "%" + str[i] + "%");
				}
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
		System.out.println(combinations);
		String search_command = "";

		if (searchThese.get(0).matches("%[\\d]+%")) {
			search_command += "(SELECT BOOK_AUTHORS.ISBN10, BOOK_AUTHORS.ISBN13, Title, Checked_out, Cover, Publisher, Pages, GROUP_CONCAT(Author SEPARATOR ', ') AS Authors FROM BOOKS NATURAL JOIN BOOK_AUTHORS  WHERE ISBN10 LIKE '"
					+ searchThese.get(0) + "' OR ISBN13 LIKE '" + searchThese.get(0)
					+ "' GROUP BY BOOK_AUTHORS.ISBN10, BOOK_AUTHORS.ISBN13 ) ";
		} else {
			search_command += "(SELECT BOOK_AUTHORS.ISBN10, BOOK_AUTHORS.ISBN13, Title, Checked_out, Cover, Publisher, Pages, GROUP_CONCAT(Author SEPARATOR ', ') AS Authors FROM BOOKS NATURAL JOIN BOOK_AUTHORS  WHERE Title LIKE '"
					+ searchThese.get(0) + "' OR Author LIKE '" + searchThese.get(0)
					+ "' GROUP BY BOOK_AUTHORS.ISBN10, BOOK_AUTHORS.ISBN13 ) ";
		}
		for (int i = 1; i < searchThese.size(); i++) {
			if (searchThese.get(i).matches("%[\\d]+%")) {
				search_command += " UNION (SELECT BOOK_AUTHORS.ISBN10, BOOK_AUTHORS.ISBN13, Title, Checked_out, Cover, Publisher, Pages,  GROUP_CONCAT(Author SEPARATOR ', ') AS Authors FROM BOOKS NATURAL JOIN BOOK_AUTHORS  WHERE ISBN10 LIKE '"
						+ searchThese.get(i) + "' OR ISBN13 LIKE '" + searchThese.get(i)
						+ "' GROUP BY BOOK_AUTHORS.ISBN10, BOOK_AUTHORS.ISBN13 ) ";
			} else {
				search_command += " UNION (SELECT BOOK_AUTHORS.ISBN10, BOOK_AUTHORS.ISBN13, Title, Checked_out, Cover, Publisher, Pages, GROUP_CONCAT(Author SEPARATOR ', ') AS Authors FROM BOOKS NATURAL JOIN BOOK_AUTHORS  WHERE Title LIKE '"
						+ searchThese.get(i) + "' OR Author LIKE '" + searchThese.get(i)
						+ "' GROUP BY BOOK_AUTHORS.ISBN10, BOOK_AUTHORS.ISBN13 ) ";
			}
		}
		System.out.println(search_command);
		return search_command;
	}

}
