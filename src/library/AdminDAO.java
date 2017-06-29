package library;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JOptionPane;

import org.jasypt.util.password.StrongPasswordEncryptor;
import org.jasypt.util.text.BasicTextEncryptor;
import org.jasypt.util.text.StrongTextEncryptor;

import java.sql.*;

public class AdminDAO {

	Properties prop;
	Connection con;
	
	public AdminDAO() {

		try {
			prop = new Properties();
			FileInputStream input = new FileInputStream("src/db.properties");
			prop.load(input);
			String url = prop.getProperty("url");
			String username = prop.getProperty("username");
			String password = prop.getProperty("password");

			con = DriverManager.getConnection(url, username, password);

			if (con == null)
				System.out.println("system is not able to connect to database");

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

	public boolean checkAdmin(String username, String password) {
		int c = 0;
		try {
			Statement stmt = con.createStatement();
			ResultSet res2 = stmt.executeQuery("SELECT COUNT(*) FROM admin");
			int adminNum = 0;
			while (res2.next()) {
				adminNum = res2.getInt(1);
			}

			ResultSet result = stmt.executeQuery("SELECT username, password FROM admin");
			while (result.next()) {
				String encryptedPassword = result.getString("password");
				StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
				if (username.equals(result.getString("username"))
						&& passwordEncryptor.checkPassword(password, encryptedPassword)) {					
					return true;
					
				} else
					c++;
			}
			if (c == adminNum) {
				JOptionPane.showMessageDialog(null, "Invalid username password, please try again");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}

	public void addAdmin(String fname, String lname, String username, String password) {
		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement(
					"INSERT INTO admin (first_name, last_name, username, password, plain_password) VALUES (?,?,?,?,?)");
			stmt.setString(1, fname);
			stmt.setString(2, lname);
			stmt.setString(3, username);
			StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
			String encryptedPassword = passwordEncryptor.encryptPassword(password);
			stmt.setString(4, encryptedPassword);
			stmt.setString(5, password);
			stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int getAdminId(String username, String password){
		PreparedStatement stmt;
		int id = 0;
		try {
			stmt = con.prepareStatement("SELECT admin_id FROM admin WHERE username =? AND plain_password=?");
			stmt.setString(1, username);
			stmt.setString(2, password);
			ResultSet result = stmt.executeQuery();
			if(result.next()){
				id = result.getInt("admin_id");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;		
	}
	
	public String getAdminName(int id){
		PreparedStatement stmt;
		String admin_name ="";
		try {
			stmt = con.prepareStatement("SELECT first_name, last_name FROM admin WHERE admin_id=?");
			stmt.setInt(1, id);			
			ResultSet result = stmt.executeQuery();
			while(result.next()){
				admin_name+= result.getString("first_name");
				admin_name += " ";
				admin_name += result.getString("last_name");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return admin_name;		
	}
	
	public Object[][] getLog() throws SQLException{
		Statement stm = con.createStatement();
		ResultSet res = stm.executeQuery("SELECT COUNT(*) FROM admin_student");
		int rows = 0;
		while (res.next()) {
			rows = res.getInt(1);
		}

		Statement stmt;
		stmt = con.createStatement();
		ResultSet result = stmt.executeQuery("SELECT admin_name, action, student_id, student_name, date FROM admin_student");
		ResultSetMetaData rsmd = result.getMetaData();
		int colNum = rsmd.getColumnCount();
		Object[][] log = new Object[rows][colNum];
		int row = 0;

		while (result.next()) {
			int col = 0;
			while (col < colNum) {
				log[row][col] = result.getObject(col + 1);
				col++;
			}
			row++;
		}
		return log;

	}
	
	public Object[][] sortLog(String sort_column) throws SQLException{
		Statement stm = con.createStatement();
		ResultSet res = stm.executeQuery("SELECT COUNT(*) FROM admin_student");
		int rows = 0;
		while (res.next()) {
			rows = res.getInt(1);
		}

		Statement stmt;
		stmt = con.createStatement();
		ResultSet result = stmt.executeQuery("SELECT admin_name, action, student_id, student_name, date FROM admin_student ORDER BY " + sort_column);
		ResultSetMetaData rsmd = result.getMetaData();
		int colNum = rsmd.getColumnCount();
		Object[][] log = new Object[rows][colNum];
		int row = 0;

		while (result.next()) {
			int col = 0;
			while (col < colNum) {
				log[row][col] = result.getObject(col + 1);
				col++;
			}
			row++;
		}
		return log;

	}
}
