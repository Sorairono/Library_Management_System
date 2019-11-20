package MySQLToJava;

import model.Book;

import java.sql.Connection ;
import java.sql.DriverManager ;
import java.sql.SQLException ;
import java.sql.Statement ;
import java.sql.ResultSet ;


import java.util.List ;
import java.util.ArrayList ;

public class MySQLToJava {
	private Connection connection;
	
	
	public MySQLToJava(String driverClassName, String dbURL, String user, String password) throws SQLException, ClassNotFoundException{
		Class.forName(driverClassName);
		connection = DriverManager.getConnection(dbURL, user, password);
	}
	
	public void shutdown() throws SQLException {
		if (connection != null) {
			connection.close();
		}
	}
	    
	public List<Book> getBookList() throws SQLException {
		try (
				Statement stmnt = connection.createStatement();
				ResultSet rs = stmnt.executeQuery("select * from BOOKS");
		){
				List<Book> bookList = new ArrayList<>();
				while (rs.next()) {
					String ISBN10 = rs.getString("ISBN10");
					String ISBN13 = rs.getString("ISBN13");
					String Title = rs.getString("Title");
					String Author = rs.getString("Author");
					String Cover = rs.getString("Cover");
					String Publisher = rs.getString("Publisher");
					int Pages = rs.getInt("Pages");
					boolean Checked_out = rs.getBoolean("Checked_out");
              
					Book book = new Book(ISBN10, ISBN13, Title, Author, Cover, Publisher, Pages, Checked_out);
              
					bookList.add(book);
          }
          return bookList ;
      } 
  }
}

