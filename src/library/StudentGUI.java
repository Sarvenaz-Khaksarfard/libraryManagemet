package library;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import org.jasypt.util.password.StrongPasswordEncryptor;

public class StudentGUI {

	int admin_id = 0;
	String admin_name = "";
	JPanel content;
	StudentDAO studentHandler = new StudentDAO();
	AdminDAO adminHandler = new AdminDAO();

	Object[] cols = { "Student ID", "First Name", "Last Name", "Program", "Email", "Telephone", "Address" };
	Object[] log_cols = { "Admin Name", "Action", "Student ID", "Student Name", "Date" };
	HashMap<Integer, String> updatedVals = new HashMap<Integer, String>();

	String current_StudentUsername = "";
	String current_StudentPassword = "";

	public JPanel studentgui(int admin_id, boolean search) {

		this.admin_id = admin_id;

		admin_name = adminHandler.getAdminName(admin_id);

		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		contentPane.setBounds(10, 10, 1050, 700);
		contentPane.setBackground(new Color(100, 170, 210));

		content = new JPanel();
		content.setLayout(null);
		content.setBounds(10, 10, 1000, 800);
		content.setBackground(new Color(100, 170, 210));
		Border border = BorderFactory.createLineBorder(Color.BLACK, 2, true);
		content.setBorder(border);
		JLabel bookImage = new JLabel();
		bookImage.setBounds(10, 5, 970, 590);
		ImageIcon image = new ImageIcon("src/students2.PNG");
		Image image_temp = image.getImage();
		Image newimg = image_temp.getScaledInstance(bookImage.getWidth(), bookImage.getHeight(),
				java.awt.Image.SCALE_SMOOTH);
		bookImage.setIcon(new ImageIcon(newimg));

		content.add(bookImage);
		contentPane.add(content);

		return contentPane;
	}

	public void addGUI() {
		current_StudentUsername = "";
		current_StudentPassword = "";
		Object[] textVals = new Object[9];
		for (int i = 0; i < 9; i++)
			textVals[i] = "";
		createForm(textVals, null, "Add");
	}

	public void updateGUI(String label, boolean check_search, boolean check_table) {
		Object[][] rows;
		try {
			rows = studentHandler.getAllStudents();
			displayStudentTable(label, check_search, check_table, rows, cols);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void logDisplay() {
		Object[][] rows;
		try {
			rows = adminHandler.getLog();
			displayStudentTable("", false, false, rows, log_cols);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void createForm(Object[] textValues, ImageIcon image, String btnLabel) {
		content.removeAll();
		content.revalidate();
		content.repaint();

		ArrayList<JTextField> textFields = new ArrayList<JTextField>();

		int c = 0;

		JLabel idlbl = new JLabel();
		idlbl.setText("Student ID");
		idlbl.setBounds(30, 30, 100, 30);
		idlbl.setFont(new Font("Arial", 20, 20));
		content.add(idlbl);
		JTextField idText = new JTextField();
		idText.setBounds(150, 30, 200, 35);
		idText.setFont(new Font("Arial", 20, 20));
		idText.setText((textValues[c].toString()));
		textFields.add(idText);
		content.add(idText);

		JLabel fnamelbl = new JLabel();
		fnamelbl.setText("First Name");
		fnamelbl.setBounds(30, 75, 100, 30);
		fnamelbl.setFont(new Font("Arial", 20, 20));
		content.add(fnamelbl);
		JTextField fnameText = new JTextField();
		fnameText.setBounds(150, 75, 200, 35);
		fnameText.setFont(new Font("Arial", 20, 20));
		fnameText.setText((textValues[++c].toString()));
		textFields.add(fnameText);
		content.add(fnameText);

		JLabel lnamelbl = new JLabel();
		lnamelbl.setText("Last Name");
		lnamelbl.setBounds(30, 120, 100, 30);
		lnamelbl.setFont(new Font("Arial", 20, 20));
		content.add(lnamelbl);
		JTextField lnameText = new JTextField();
		lnameText.setBounds(150, 120, 200, 35);
		lnameText.setFont(new Font("Arial", 20, 20));
		lnameText.setText((textValues[++c].toString()));
		textFields.add(lnameText);
		content.add(lnameText);

		JLabel programlbl = new JLabel();
		programlbl.setText("Program");
		programlbl.setBounds(30, 165, 100, 30);
		programlbl.setFont(new Font("Arial", 20, 20));
		content.add(programlbl);
		JTextField programText = new JTextField();
		programText.setBounds(150, 165, 200, 35);
		programText.setFont(new Font("Arial", 20, 20));
		programText.setText((textValues[++c].toString()));
		textFields.add(programText);
		content.add(programText);

		JLabel userlbl = new JLabel();
		userlbl.setText("Username");
		userlbl.setBounds(380, 30, 100, 30);
		userlbl.setFont(new Font("Arial", 20, 20));
		content.add(userlbl);
		JTextField userText = new JTextField();
		userText.setBounds(500, 30, 200, 35);
		userText.setFont(new Font("Arial", 20, 20));
		userText.setText((textValues[++c].toString()));
		textFields.add(userText);
		content.add(userText);

		JLabel passlbl = new JLabel();
		passlbl.setText("Password");
		passlbl.setBounds(380, 75, 100, 30);
		passlbl.setFont(new Font("Arial", 20, 20));
		content.add(passlbl);
		JTextField passText = new JTextField();
		passText.setBounds(500, 75, 200, 35);
		passText.setFont(new Font("Arial", 20, 20));
		passText.setText((textValues[++c].toString()));
		textFields.add(passText);
		content.add(passText);

		JLabel emailbl = new JLabel();
		emailbl.setText("Email");
		emailbl.setBounds(380, 120, 100, 30);
		emailbl.setFont(new Font("Arial", 20, 20));
		content.add(emailbl);
		JTextField emailText = new JTextField();
		emailText.setBounds(500, 120, 200, 35);
		emailText.setFont(new Font("Arial", 20, 20));
		emailText.setText((textValues[++c].toString()));
		textFields.add(emailText);
		content.add(emailText);

		JLabel tellbl = new JLabel();
		tellbl.setText("Telephone");
		tellbl.setBounds(380, 165, 100, 30);
		tellbl.setFont(new Font("Arial", 20, 20));
		content.add(tellbl);
		JTextField tellText = new JTextField();
		tellText.setBounds(500, 165, 200, 35);
		tellText.setFont(new Font("Arial", 20, 20));
		tellText.setText((textValues[++c].toString()));
		textFields.add(tellText);
		content.add(tellText);

		JLabel addresslbl = new JLabel();
		addresslbl.setText("Address");
		addresslbl.setBounds(30, 210, 100, 30);
		addresslbl.setFont(new Font("Arial", 20, 20));
		content.add(addresslbl);
		JTextField addressText = new JTextField();
		addressText.setBounds(150, 210, 550, 35);
		addressText.setFont(new Font("Arial", 20, 20));
		addressText.setText((textValues[++c].toString()));
		textFields.add(addressText);
		content.add(addressText);

		JLabel imagelbl = new JLabel();
		imagelbl.setForeground(Color.WHITE);
		imagelbl.setBounds(800, 20, 150, 200);
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		imagelbl.setBorder(border);
		Image newimg = null;
		if (image != null) {
			Image image_temp = image.getImage();
			newimg = image_temp.getScaledInstance(imagelbl.getWidth(), imagelbl.getHeight(),
					java.awt.Image.SCALE_SMOOTH);
			imagelbl.setIcon(new ImageIcon(newimg));
		} else {
			imagelbl.setIcon(image);
		}

		content.add(imagelbl);

		JButton imagebtn = new JButton("Upload Photo");
		imagebtn.setBounds(815, 230, 120, 50);
		imagebtn.setBackground(new Color(2, 90, 130));
		imagebtn.setForeground(Color.WHITE);
		content.add(imagebtn);

		Student student = new Student();

		imagebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Choose your photo");
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int value = fileChooser.showOpenDialog(content);
				if (value == fileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					student.setImage(file);
					BufferedImage bi;
					try {
						bi = ImageIO.read(file);
						Image dimg = bi.getScaledInstance(imagelbl.getWidth(), imagelbl.getHeight(),
								Image.SCALE_SMOOTH);
						imagelbl.setIcon(new ImageIcon(dimg));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		});

		JButton okbtn = new JButton(btnLabel);
		okbtn.setBounds(530, 270, 170, 60);
		okbtn.setFont(new Font("Arial", 20, 18));
		okbtn.setBackground(new Color(2, 90, 130));
		okbtn.setForeground(Color.WHITE);
		content.add(okbtn);

		content.revalidate();
		content.repaint();
		okbtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				StudentDAO studentHandler = new StudentDAO();
				StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
				String encryptedPassword = passwordEncryptor.encryptPassword(passText.getText());
				if (checkTextFields(textFields) && checkType(textFields.get(0), "Student Id")
						&& studentHandler.checkStudentUserPassUniqueness(current_StudentUsername,
								current_StudentPassword, userText.getText(), passText.getText())) {
					if (btnLabel.equals("Add") && studentHandler.checkStudentId(Integer.parseInt(idText.getText()))) {

						student.setId(Integer.parseInt(idText.getText()));
						student.setfname(fnameText.getText());
						student.setlname(lnameText.getText());
						student.setProgram(programText.getText());
						student.setUsername(userText.getText());
						student.setPassword(encryptedPassword);
						student.setEmail(emailText.getText());
						student.setTel(tellText.getText());
						student.setAddress(addressText.getText());
						student.setPlainPassword(passText.getText());

						int student_id = studentHandler.AddStudent(student);
						JOptionPane.showMessageDialog(null, "Student added successfully");
						Object[] name = studentHandler.getStudent(student_id).clone();
						String student_name = name[1] + " " + name[2];
						studentHandler.insertLogHistory(admin_name, "Added a new student", student_id, student_name);

					} else if (btnLabel.equals("Apply Changes")) {
						try {
							File file = student.getImage();
							if (file != null) {
								FileInputStream input = new FileInputStream(student.getImage());
								studentHandler.updateStudentImage(input, (Integer) textValues[0]);
								Object[] name = studentHandler.getStudent((Integer) textValues[0]).clone();
								String student_name = name[1] + " " + name[2];
								JOptionPane.showMessageDialog(null, "Student photo updated successfully");
								studentHandler.insertLogHistory(admin_name, "Updated student photo",
										(Integer) textValues[0], student_name);

							}
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						updatedVals.put(1, idText.getText());
						updatedVals.put(2, fnameText.getText());
						updatedVals.put(3, lnameText.getText());
						updatedVals.put(4, programText.getText());
						updatedVals.put(5, userText.getText());
						updatedVals.put(6, passText.getText());
						updatedVals.put(7, emailText.getText());
						updatedVals.put(8, tellText.getText());
						updatedVals.put(9, addressText.getText());
						int student_id = studentHandler.updateStudent(updatedVals, textValues[0]);
						if (student_id > 0) {
							JOptionPane.showMessageDialog(null, "Student updated successfully");
							Object[] name = studentHandler.getStudent(student_id).clone();
							String student_name = name[1] + " " + name[2];
							studentHandler.insertLogHistory(admin_name, "Update student", student_id, student_name);
						}
					}
				}
			}

		});
	}

	int row = -1;

	public void displayStudentTable(String label, Boolean check_search, Boolean check_table,  Object[][] rows, Object[] cols) {
		row = -1;
		content.removeAll();
		content.revalidate();
		content.repaint();

		if (check_search == true) {
			JLabel searchlbl = new JLabel("Search student by student id");
			searchlbl.setBounds(30, 5, 280, 40);
			searchlbl.setFont(new Font("Arial", 20, 20));
			content.add(searchlbl);

			JTextField searchtxt = new JTextField();
			searchtxt.setBounds(330, 5, 130, 40);
			searchtxt.setFont(new Font("Arial", 20, 20));
			content.add(searchtxt);
			JButton searchbtn = new JButton("Search");
			searchbtn.setBounds(500, 5, 140, 40);
			searchbtn.setFont(new Font("Arial", 20, 18));
			searchbtn.setBackground(new Color(2, 90, 130));
			searchbtn.setForeground(Color.WHITE);
			content.add(searchbtn);

			searchbtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (checkType(searchtxt, "Student Id")) {
						Object[][] rows = studentHandler.searchStudent(Integer.parseInt(searchtxt.getText()));

						displayStudentTable(label, check_search, check_table, rows, cols);
						if (rows.length == 0) {
							JOptionPane.showMessageDialog(null, "No student found");
						}
					}
				}
			});
		}
		if (label != "") {
			JLabel notelbl = new JLabel();
			notelbl.setText("Please select the student that you want to " + label);
			notelbl.setBounds(20, 50, 500, 30);
			notelbl.setFont(new Font("Arial", 20, 18));
			notelbl.setForeground(new Color(2, 60, 210));
			content.add(notelbl);
		}

		DefaultTableModel model = new DefaultTableModel(rows, cols) {

			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		JTable table = new JTable(model);

		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setBackground(Color.WHITE);
		JScrollPane scroll = new JScrollPane(table);
		if (label != "")
			scroll.setBounds(10, 90, 970, 300);
		else if (label == "")
			scroll.setBounds(10, 60, 970, 390);
		JTableHeader header = table.getTableHeader();
		header.setFont(new Font("Arial", Font.BOLD, 15));
		header.setBackground(Color.white);
		table.setFont(new Font("Arial", Font.PLAIN, 13));
		table.setRowHeight(25);

		table.addMouseListener(new java.awt.event.MouseAdapter() {

			public void mouseClicked(MouseEvent arg0) {
				row = table.getSelectedRow();
			}
		});

		table.getTableHeader().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int col = table.columnAtPoint(e.getPoint());
				String name = null;
				if (check_table == true) {
					switch (col) {
					case 0:
						name = "id";
						break;
					case 1:
						name = "first_name";
						break;
					case 2:
						name = "last_name";
						break;
					case 3:
						name = "program";
						break;
					case 4:
						name = "email";
						break;
					case 5:
						name = "tel";
						break;
					case 6:
						name = "address";
						break;
					}

					try {
						Object[][] rows = studentHandler.sortStudents(name);
						displayStudentTable(label, check_search, check_table, rows, cols);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

				else if (check_table == false) {
					switch (col) {
					case 0:
						name = "admin_name";
						break;
					case 1:
						name = "action";
						break;
					case 2:
						name = "student_id";
						break;
					case 3:
						name = "student_name";
						break;
					case 4:
						name = "date";
						break;
					}
					Object[][] rows;
					try {
						rows = adminHandler.sortLog(name);
						displayStudentTable(label, check_search, check_table, rows, cols);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
			}

		});

		if (label != "") {
			JButton okbtn = new JButton(label);
			okbtn.setBounds(550, 420, 170, 60);
			okbtn.setFont(new Font("Arial", 20, 18));
			okbtn.setBackground(new Color(2, 90, 130));
			okbtn.setForeground(Color.WHITE);
			content.add(okbtn);

			okbtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (label.equals("Delete") && row >= 0) {
						Object[] name = studentHandler.getStudent((Integer) table.getValueAt(row, 0)).clone();
						int student_id = studentHandler.deleteStudent((Integer) table.getValueAt(row, 0));
						String student_name = name[1] + " " + name[2];
						studentHandler.insertLogHistory(admin_name, "Deleted student", student_id, student_name);
						model.removeRow(row);
						JOptionPane.showMessageDialog(null, "Student deleted successfully");

					} else if (label.equals("Update") && row >= 0) {
						Object[] rowVals = studentHandler.getStudent((Integer) table.getModel().getValueAt(row, 0));
						current_StudentUsername = rowVals[4].toString();
						current_StudentPassword = rowVals[5].toString();
						ImageIcon image = studentHandler.getStudentPhoto((Integer) table.getModel().getValueAt(row, 0));
						createForm(rowVals, image, "Apply Changes");
					}
				}
			});
		}
		content.add(scroll);
	}

	public boolean checkTextFields(ArrayList<JTextField> texts) {
		for (int i = 0; i < texts.size(); i++) {
			if (texts.get(i).getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Form is not completely filled up");
				return false;
			}
		}
		return true;
	}

	public boolean checkType(JTextField idText, String text) {
		String id = idText.getText();
		try {
			Integer.parseInt(id);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, text + " must be a number");
			return false;
		}
		return true;
	}
}
