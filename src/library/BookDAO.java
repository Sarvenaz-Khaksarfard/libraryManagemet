package library;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Properties;

import javax.swing.JOptionPane;

public class BookDAO {

	Connection con;

	Object[] cols = { "Book ID", "Title", "Author", "Edition", "Year", "ISBN", "Availability" };

	public BookDAO() {

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

	public void addBook(Book book) {
		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement(
					"INSERT INTO book (book_id, title, author,edition,year,isbn,availability) VALUES (?,?,?,?,?,?,?)");
			stmt.setInt(1, book.getId());
			stmt.setString(2, book.getTitle());
			stmt.setString(3, book.getAuthor());
			stmt.setInt(4, book.getEdition());
			stmt.setInt(5, book.getYear());
			stmt.setString(6, book.getIsbn());
			stmt.setString(7, book.getAvailability());
			stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean checkBookId(int id) {
		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement("SELECT book_id FROM book");
			ResultSet result = stmt.executeQuery();
			while (result.next()) {
				if (result.getInt(1) == id) {
					JOptionPane.showMessageDialog(null, "This book id belongs to another book");
					return false;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public Object[][] getAllBooks() {

		Statement stm1;

		int row = 0;
		int col = 0;
		try {
			stm1 = con.createStatement();
			ResultSet res1 = stm1.executeQuery("SELECT COUNT(*) FROM book");

			while (res1.next()) {
				row = res1.getInt(1);
			}
			ResultSet res2 = stm1.executeQuery("SELECT * FROM book");
			ResultSetMetaData rsmd = res2.getMetaData();
			col = rsmd.getColumnCount();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		Object[][] books = new Object[row][col];
		Statement stmt;
		try {
			stmt = con.createStatement();
			int rowNum = 0;
			ResultSet result = stmt.executeQuery("SELECT * FROM book");
			while (result.next()) {
				int colNum = 0;
				while (colNum < col) {
					books[rowNum][colNum] = result.getObject(colNum + 1);
					colNum++;
				}
				rowNum++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return books;

	}
	
	public Object[][] sortBooks(String sort_column){
		
		Statement stm1;

		int row = 0;
		int col = 0;
		try {
			stm1 = con.createStatement();
			ResultSet res1 = stm1.executeQuery("SELECT COUNT(*) FROM book");

			while (res1.next()) {
				row = res1.getInt(1);
			}
			ResultSet res2 = stm1.executeQuery("SELECT * FROM book");
			ResultSetMetaData rsmd = res2.getMetaData();
			col = rsmd.getColumnCount();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		Object[][] books = new Object[row][col];
		Statement stmt;
		try {
			stmt = con.createStatement();
			int rowNum = 0;
			ResultSet result = stmt.executeQuery("SELECT * FROM book ORDER BY " + sort_column);
			while (result.next()) {
				int colNum = 0;
				while (colNum < col) {
					books[rowNum][colNum] = result.getObject(colNum + 1);
					colNum++;
				}
				rowNum++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return books;

	}

	public void deleteBook(int id) {
		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement("DELETE FROM book WHERE book_id=?");
			stmt.setInt(1, id);
			stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public boolean updateBook(HashMap<Integer, String> map, Object id) {
		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement(
					"UPDATE book SET book_id = ?, title=?,author=?, edition=?,year=?,isbn=?,availability=? "
							+ "WHERE book_id=? AND (book_id != ? OR title != ? OR author != ? OR edition != ? OR year != ? OR "
							+ "isbn != ? OR availability != ?)");
			stmt.setInt(1, Integer.parseInt(map.get(1)));
			stmt.setString(2, map.get(2));
			stmt.setString(3, map.get(3));
			stmt.setInt(4, Integer.parseInt(map.get(4)));
			stmt.setInt(5, Integer.parseInt(map.get(5)));
			stmt.setString(6, map.get(6));
			stmt.setString(7, map.get(7));
			stmt.setInt(8, (Integer) id);
			stmt.setInt(9, Integer.parseInt(map.get(1)));
			stmt.setString(10, map.get(2));
			stmt.setString(11, map.get(3));
			stmt.setInt(12, Integer.parseInt(map.get(4)));
			stmt.setInt(13, Integer.parseInt(map.get(5)));
			stmt.setString(14, map.get(6));
			stmt.setString(15, map.get(7));
			int row = stmt.executeUpdate();
			if (row > 0)
				return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public Object[][] searchBook(String title) {
		PreparedStatement stmt;
		Object[][] updatedVals = null;
		try {
			int row = 0;
			stmt = con.prepareStatement("SELECT COUNT(*) FROM book WHERE title LIKE ?");
			stmt.setString(1, "%" + title + "%");
			ResultSet resu = stmt.executeQuery();
			while (resu.next()) {
				row = resu.getInt(1);
			}
			updatedVals = new Object[row][cols.length];
			stmt = con.prepareStatement("SELECT * FROM book WHERE title LIKE ?");
			stmt.setString(1, "%" + title + "%");
			ResultSet res = stmt.executeQuery();
			int rowNum = 0;
			while (res.next()) {
				int colNum = 0;
				while (colNum < cols.length) {
					updatedVals[rowNum][colNum] = res.getObject(colNum + 1);
					colNum++;
				}
				rowNum++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return updatedVals;

	}

	public boolean checkAvailability(int id) {
		PreparedStatement stmt;

		try {
			stmt = con.prepareStatement("SELECT availability FROM book WHERE book_id=?");
			stmt.setInt(1, id);
			ResultSet result = stmt.executeQuery();
			if (result.next() && result.getString(1).equals("Yes")) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public void updateAvailability(int id, String book_status) {
		PreparedStatement stmt;

		try {
			stmt = con.prepareStatement("UPDATE book SET availability =? WHERE book_id=?");
			stmt.setString(1, book_status);
			stmt.setInt(2, id);
			stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
