package model;

public class Borrower {
	private int borrower_id;
	private String ssn;
	private String first_name;
	private String last_name;
	private String email;
	private String address;
	private String city;
	private String state;
	private String phone;

	public Borrower() {

	}

	public Borrower(int borrower_id, String ssn, String first_name, String last_name, String email, String address,
			String city, String state, String phone) {
		super();
		this.borrower_id = borrower_id;
		this.ssn = ssn;
		this.first_name = first_name;
		this.last_name = last_name;
		this.email = email;
		this.address = address;
		this.city = city;
		this.state = state;
		this.phone = phone;
	}

	public int getBorrower_id() {
		return borrower_id;
	}

	public void setBorrower_id(int borrower_id) {
		this.borrower_id = borrower_id;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
