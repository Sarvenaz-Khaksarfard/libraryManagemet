package library;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import org.jasypt.util.password.StrongPasswordEncryptor;

public class StudentDAO {

	Properties prop;
	Connection con;

	public StudentDAO() {
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

			// Statement stmt = con.createStatement();
			// stmt.executeUpdate("CREATE TABLE student (id INTEGER NOT NULL,
			// first_name VARCHAR(40), last_name VARCHAR(40), PRIMARY
			// KEY(id))");

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

	public int AddStudent(Student student) {

		try {
			PreparedStatement stmt = con.prepareStatement(
					"INSERT INTO student (id,first_name,last_name,program,username,password,email,tel,address,plain_password,image) VALUES (?,?,?,?,?,?,?,?,?,?,?)");
			stmt.setInt(1, student.getId());
			stmt.setString(2, student.getfname());
			stmt.setString(3, student.getlname());
			stmt.setString(4, student.getProgram());
			stmt.setString(5, student.getUsername());
			stmt.setString(6, student.getPassword());
			stmt.setString(7, student.getEmail());
			stmt.setString(8, student.getTel());
			stmt.setString(9, student.getAddress());
			stmt.setString(10, student.getPlainPassword());
			if (student.getImage() == null) {
				stmt.setBinaryStream(11, null);
			} else {
				FileInputStream input = new FileInputStream(student.getImage());
				stmt.setBinaryStream(11, input);
			}
			stmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return student.getId();
	}

	public boolean checkStudentId(int id) {
		Statement stmt;
		try {
			stmt = con.createStatement();
			ResultSet result = stmt.executeQuery("SELECT id FROM student");
			while (result.next()) {
				if (result.getInt("id") == id) {
					JOptionPane.showMessageDialog(null, "This student id belongs to another student");
					return false;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public boolean checkStudentUserPassUniqueness(String current_username, String current_password, String username,
			String password) {
		PreparedStatement stmt;

		try {
			stmt = con.prepareStatement(
					"SELECT username, plain_password FROM student WHERE username NOT IN(?) AND plain_password NOT IN(?)");
			stmt.setString(1, current_username);
			stmt.setString(2, current_password);
			ResultSet result = stmt.executeQuery();

			while (result.next()) {
				if (result.getString("username").equals(username)) {

					JOptionPane.showMessageDialog(null,
							"This username belongs to another student, please choose a new username");
					return false;
				}

				if (result.getString("plain_password").equals(password)) {
					JOptionPane.showMessageDialog(null,
							"This password belongs to another student, please choose a new password");
					return false;
				}
			}
			return true;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public Object[][] getAllStudents() throws SQLException {

		Statement stm = con.createStatement();
		ResultSet res = stm.executeQuery("SELECT COUNT(*) FROM student");
		int rows = 0;
		while (res.next()) {
			rows = res.getInt(1);
		}

		Statement stmt;
		stmt = con.createStatement();
		ResultSet result = stmt.executeQuery("SELECT id,first_name,last_name,program,email,tel,address FROM student");
		ResultSetMetaData rsmd = result.getMetaData();
		int colNum = rsmd.getColumnCount();
		Object[][] students = new Object[rows][colNum];
		int row = 0;

		while (result.next()) {
			int col = 0;
			while (col < colNum) {
				students[row][col] = result.getObject(col + 1);
				col++;
			}
			row++;
		}

		return students;
	}

	public Object[][] sortStudents(String sort_column) throws SQLException{
		Statement stm = con.createStatement();
		ResultSet res = stm.executeQuery("SELECT COUNT(*) FROM student");
		int rows = 0;
		while (res.next()) {
			rows = res.getInt(1);
		}

		Statement stmt;
		stmt = con.createStatement();
		ResultSet result = stmt.executeQuery("SELECT id,first_name,last_name,program,email,tel,address FROM student ORDER BY " + sort_column);
		ResultSetMetaData rsmd = result.getMetaData();
		int colNum = rsmd.getColumnCount();
		Object[][] students = new Object[rows][colNum];
		int row = 0;

		while (result.next()) {
			int col = 0;
			while (col < colNum) {
				students[row][col] = result.getObject(col + 1);
				col++;
			}
			row++;
		}

		return students;
	}
	public Object[] getStudent(int id) {
		PreparedStatement stmt;
		Object[] student = new Object[9];
		try {
			stmt = con.prepareStatement(
					"SELECT id,first_name,last_name,program,username,plain_password,email,tel,address FROM student WHERE id=?");
			stmt.setInt(1, id);
			ResultSet res = stmt.executeQuery();

			while (res.next()) {
				int c = 0;
				while (c < 9) {
					student[c] = res.getObject(c + 1);
					c++;
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return student;
	}

	public ImageIcon getStudentPhoto(int id) {
		PreparedStatement stm;
		// InputStream input;
		ImageIcon icon = null;
		try {
			stm = con.prepareStatement("SELECT image FROM student WHERE id=?");
			stm.setInt(1, id);
			ResultSet res = stm.executeQuery();
			if (res.next()) {
				// input = res.getBinaryStream("image");
				byte[] buffer = res.getBytes("image");
				if (buffer != null) {
					Image image = new ImageIcon(buffer).getImage();
					icon = new ImageIcon(image);
				} else
					return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return icon;
	}

	public int deleteStudent(int id) {
		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement("DELETE FROM student WHERE id = ?");
			stmt.setInt(1, id);
			stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}

	public int updateStudent(HashMap<Integer, String> map, Object id) {
		PreparedStatement stmt;
		StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
		String encryptedPassword = passwordEncryptor.encryptPassword(map.get(6));
		try {
			stmt = con.prepareStatement(
					"UPDATE student SET id=?, first_name =?, last_name=?, program=?, username=?, password=?,email=?,tel=?,address=?, plain_password=? "
							+ "WHERE id=? AND (id != ? OR first_name != ? OR last_name != ? OR program != ? OR username != ? OR "
							+ "plain_password != ? OR email != ? OR tel != ? OR address != ?)");
			stmt.setInt(1, Integer.parseInt(map.get(1)));
			stmt.setString(2, map.get(2));
			stmt.setString(3, map.get(3));
			stmt.setString(4, map.get(4));
			stmt.setString(5, map.get(5));
			stmt.setString(6, encryptedPassword);
			stmt.setString(7, map.get(7));
			stmt.setString(8, map.get(8));
			stmt.setString(9, map.get(9));
			stmt.setString(10, map.get(6));
			stmt.setInt(11, (Integer)id);
			stmt.setInt(12, Integer.parseInt(map.get(1)));
			for (int i = 2; i <= map.size(); i++) {
				stmt.setString(i + 11, map.get(i).toString());
			}
			 int row_affected = stmt.executeUpdate();
			 if(row_affected>0){
				 return (Integer)id;
			 }
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;

	}

	public boolean updateStudentImage(FileInputStream input, int id) {
		PreparedStatement stm;
		try {
			stm = con.prepareStatement("UPDATE student SET image=? WHERE id=?");
			stm.setBinaryStream(1, input);
			stm.setInt(2, id);
			//stm.setBinaryStream(3, input);
			int row = stm.executeUpdate();
			if (row > 0)
				return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public Object[][] searchStudent(int id) {
		Object[] cols = { "Student ID", "First Name", "Last Name", "Program", "Email", "Telephone", "Address" };
		PreparedStatement stm;
		int c = 0;
		try {
			stm = con.prepareStatement("SELECT COUNT(*) FROM student WHERE id LIKE ?");
			stm.setString(1, id + "%");
			ResultSet res = stm.executeQuery();
			while (res.next()) {
				c = res.getInt(1);
			}

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Object[][] search_result = new Object[c][cols.length];
		PreparedStatement stmt;
		try {
			int row = 0;
			stmt = con.prepareStatement(
					"SELECT id,first_name,last_name,program,email,tel,address FROM student WHERE id LIKE ?");
			stmt.setString(1, id + "%");
			ResultSet result = stmt.executeQuery();
			while (result.next()) {
				int col = 0;
				while (col < cols.length) {
					search_result[row][col] = result.getObject(col + 1);
					col++;
				}
				row++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return search_result;
	}

	public boolean checkStudent(String username, String password) {
		int c = 0;
		try {
			Statement stmt = con.createStatement();
			ResultSet res2 = stmt.executeQuery("SELECT COUNT(*) FROM student");
			int studentNum = 0;
			while (res2.next()) {
				studentNum = res2.getInt(1);
			}

			ResultSet result = stmt.executeQuery("SELECT username, password FROM student");
			while (result.next()) {
				String encryptedPassword = result.getString("password");
				StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
				if (username.equals(result.getString("username"))
						&& passwordEncryptor.checkPassword(password, encryptedPassword)) {
					// new BorrowReturnBookGUI();
					System.out.println("student is correct");
					return true;

				} else
					c++;
			}
			if (c == studentNum) {
				JOptionPane.showMessageDialog(null, "Invalid username password, please try again");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}

	public Student getCurrentStudent(String username, String password) {
		PreparedStatement stmt;
		Student student = new Student();
		try {
			stmt = con.prepareStatement(
					"SELECT id,first_name,last_name,program,email,tel,address FROM student WHERE username =? AND plain_password =?");
			stmt.setString(1, username);
			stmt.setString(2, password);
			ResultSet result = stmt.executeQuery();
			while (result.next()) {
				student.setId(result.getInt("id"));
				student.setfname(result.getString("first_name"));
				student.setlname(result.getString("last_name"));
				student.setProgram(result.getString("program"));
				student.setEmail(result.getString("email"));
				student.setTel(result.getString("tel"));
				student.setAddress(result.getString("address"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return student;
	}

	public void insertLogHistory(String admin_name, String action, int student_id, String student_name) {
		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement(
					"INSERT INTO admin_student(admin_name, action, student_id, student_name, date) VALUES (?,?,?,?,?)");
			stmt.setString(1, admin_name);
			stmt.setString(2, action);
			stmt.setInt(3, student_id);
			stmt.setString(4, student_name);
			stmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
			stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
