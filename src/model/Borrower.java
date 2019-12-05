package model;

public class Borrower {
	private int Card_id;
	private String Ssn;
	private String Bname;
	private String Email;
	private String Address;
	private String Phone;

	public Borrower() {

	}

	public Borrower(int card_id, String ssn, String bname, String email, String address, String phone) {
		super();
		Card_id = card_id;
		Ssn = ssn;
		Bname = bname;
		Email = email;
		Address = address;
		Phone = phone;
	}

	public int getCard_id() {
		return Card_id;
	}

	public void setCard_id(int card_id) {
		Card_id = card_id;
	}

	public String getSsn() {
		return Ssn;
	}

	public void setSsn(String ssn) {
		Ssn = ssn;
	}

	public String getBname() {
		return Bname;
	}

	public void setBname(String bname) {
		Bname = bname;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getPhone() {
		return Phone;
	}

	public void setPhone(String phone) {
		Phone = phone;
	}

}
