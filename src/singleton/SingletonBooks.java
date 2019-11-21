package singleton;

import model.Book;

public class SingletonBooks {
	private static SingletonBooks instance;

	private SingletonBooks() {

	}

	public static SingletonBooks getInstance() {
		if (instance == null) {
			synchronized (SingletonBooks.class) {
				if (instance == null) {
					instance = new SingletonBooks();
				}
			}
		}
		return instance;
	}

	private Book current_book_choice;
	private String search_string;

	public Book getCurrent_book_choice() {
		return current_book_choice;
	}

	public void setCurrent_book_choice(Book current_book_choice) {
		this.current_book_choice = current_book_choice;
	}

	public String getSearch_string() {
		return search_string;
	}

	public void setSearch_string(String search_string) {
		this.search_string = search_string;
	}

}
