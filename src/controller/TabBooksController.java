package controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Book;

public class TabBooksController implements Initializable {

	@FXML
	private TableView<Book> tb_books;
	@FXML
	private JFXTextField tx_search;
	private TableColumn<Book, String> tc_isbn = new TableColumn<Book, String>("ISBN");
	private TableColumn<Book, String> tc_title = new TableColumn<Book, String>("Title");
	private TableColumn<Book, String> tc_author = new TableColumn<Book, String>("Author");
	private TableColumn<Book, String> tc_availability = new TableColumn<Book, String>("Availability");
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		tb_books.getColumns().addAll(tc_isbn, tc_title, tc_author, tc_availability);
		tc_isbn.prefWidthProperty().bind(tb_books.widthProperty().multiply(0.1));
		tc_title.prefWidthProperty().bind(tb_books.widthProperty().multiply(0.4));
		tc_author.prefWidthProperty().bind(tb_books.widthProperty().multiply(0.4));
		tc_availability.prefWidthProperty().bind(tb_books.widthProperty().multiply(0.1));
		
	}

}
