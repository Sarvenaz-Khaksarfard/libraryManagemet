package library;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

public class BorrowReturnDAO {

	Connection con;

	public BorrowReturnDAO() {
		Properties prop;
		try {
			prop = new Properties();
			prop.load(new FileInputStream("src/db.properties"));
			String url = prop.getProperty("url");
			String username = prop.getProperty("username");
			String password = prop.getProperty("password");
			con = DriverManager.getConnection(url, username, password);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addBorrowDetails(int student_id, String first_name, String last_name, int book_id, String title) {
		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement(
					"INSERT INTO student_book (student_id,first_name,last_name,book_id,title) VALUES(?,?,?,?,?)");
			stmt.setInt(1, student_id);
			stmt.setString(2, first_name);
			stmt.setString(3, last_name);
			stmt.setInt(4, book_id);
			stmt.setString(5, title);
			stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<Integer> getStudentsBooksId(int id) {
		ArrayList<Integer> books = new ArrayList<Integer>();
		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement("SELECT book_id FROM student_book WHERE student_id=?");
			stmt.setInt(1, id);
			ResultSet result = stmt.executeQuery();
			while (result.next()) {
				books.add(result.getInt("book_id"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return books;
	}

	public Object[][] getStudentBooksInfo(ArrayList<Integer> book_ids) {
		PreparedStatement stmt;
		Object[][] booksInfo = new Object[book_ids.size()][6];
		try {
			for (int i = 0; i < book_ids.size(); i++) {
				stmt = con.prepareStatement("SELECT book_id, title,author,edition,year,isbn FROM book WHERE book_id=?");
				stmt.setInt(1, book_ids.get(i));
				ResultSet result = stmt.executeQuery();

				while (result.next()) {
					int cols = 0;
					while (cols < 6) {
						booksInfo[i][cols] = result.getObject(cols + 1);
						cols++;
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return booksInfo;
	}

	public void returnBook(int book_id) {
		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement("DELETE FROM student_book WHERE book_id=?");
			stmt.setInt(1, book_id);
			stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Object[][] getAllBorrowdBooks() {
		Statement stm;
		PreparedStatement stmt;
		int row=0;
		try {
			stm = con.createStatement();
			ResultSet res = stm.executeQuery("SELECT COUNT(*) FROM student_book");
			if(res.next()){
				row = res.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Object[][] rowValues = new Object[row][4];
		try {
			stmt = con.prepareStatement(
					"SELECT student_book.book_id, student_book.title, book.author, student_book.student_id, student_book.first_name, student_book.last_name FROM student_book INNER JOIN book WHERE student_book.book_id = book.book_id");
			ResultSet result = stmt.executeQuery();
			int rowCount=0;
			while(result.next()){
				rowValues[rowCount][0] = result.getInt("book_id");
				rowValues[rowCount][1] = result.getString("title");
				rowValues[rowCount][2] = result.getString("author");
				rowValues[rowCount][3] = result.getInt("student_id") + " " + result.getString("first_name") + " " + result.getString("last_name");
				rowCount++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rowValues;
	}
	
	public Object[][] sortBorrowedBooks(String sort_column) {
		Statement stm;
		PreparedStatement stmt;
		int row=0;
		try {
			stm = con.createStatement();
			ResultSet res = stm.executeQuery("SELECT COUNT(*) FROM student_book");
			if(res.next()){
				row = res.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Object[][] rowValues = new Object[row][4];
		try {
			stmt = con.prepareStatement(
					"SELECT student_book.book_id, student_book.title, book.author, student_book.student_id, student_book.first_name, student_book.last_name FROM student_book INNER JOIN book WHERE student_book.book_id = book.book_id ORDER BY " + sort_column);
			ResultSet result = stmt.executeQuery();
			int rowCount=0;
			while(result.next()){
				rowValues[rowCount][0] = result.getInt("book_id");
				rowValues[rowCount][1] = result.getString("title");
				rowValues[rowCount][2] = result.getString("author");
				rowValues[rowCount][3] = result.getInt("student_id") + " " + result.getString("first_name") + " " + result.getString("last_name");
				rowCount++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rowValues;
	}
}
