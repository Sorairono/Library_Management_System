package model;

public class Book {
	private String ISBN10;
	private String ISBN13;
	private String Title;
	private String Author;
	private String Cover;
	private String Publisher;
	private String Pages;

	public Book() {

	}

	public Book(String iSBN10, String iSBN13, String title, String author, String cover, String publisher,
			String pages) {
		super();
		ISBN10 = iSBN10;
		ISBN13 = iSBN13;
		Title = title;
		Author = author;
		Cover = cover;
		Publisher = publisher;
		Pages = pages;
	}

	public String getISBN10() {
		return ISBN10;
	}

	public void setISBN10(String iSBN10) {
		ISBN10 = iSBN10;
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

	public String getAuthor() {
		return Author;
	}

	public void setAuthor(String author) {
		Author = author;
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

	public String getPages() {
		return Pages;
	}

	public void setPages(String pages) {
		Pages = pages;
	}

}
