package model;

public class Book {
	private String Isbn;
	private String ISBN13;
	private String Title;
	private String Cover;
	private String Publisher;
	private String Author;
	private int Pages;
	private boolean Checked_out;

	public Book() {

	}

	public Book(String iSBN10, String iSBN13, String title, String cover, String publisher, String author, int pages,
			boolean checked_out) {
		super();
		Isbn = iSBN10;
		ISBN13 = iSBN13;
		Title = title;
		Cover = cover;
		Publisher = publisher;
		Author = author;
		Pages = pages;
		Checked_out = checked_out;
	}

	public String getIsbn() {
		return Isbn;
	}

	public void setIsbn(String isbn) {
		Isbn = isbn;
	}

	public String getISBN13() {
		return ISBN13;
	}

	public void setISBN13(String iSBN13) {
		ISBN13 = iSBN13;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getCover() {
		return Cover;
	}

	public void setCover(String cover) {
		Cover = cover;
	}

	public String getPublisher() {
		return Publisher;
	}

	public void setPublisher(String publisher) {
		Publisher = publisher;
	}

	public int getPages() {
		return Pages;
	}

	public void setPages(int pages) {
		Pages = pages;
	}

	public boolean isChecked_out() {
		return Checked_out;
	}

	public void setChecked_out(boolean checked_out) {
		Checked_out = checked_out;
	}

	public String getAuthor() {
		return Author;
	}

	public void setAuthor(String author) {
		Author = author;
	}

}
