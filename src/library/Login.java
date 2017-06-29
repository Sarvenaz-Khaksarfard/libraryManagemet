package library;

import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

public class Login extends JFrame {

	private JPanel contentPane;

	AdminDAO adminHandler = new AdminDAO();
	StudentDAO studentHandler = new StudentDAO();
	Student student = new Student();

	boolean isAdmin = false;
	boolean isStudent = false;

	public Login() {
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(100, 170, 210));
		JLabel userlbl = new JLabel();
		userlbl.setText("Username");
		userlbl.setBounds(50, 70, 100, 30);
		userlbl.setFont(new Font("Arial", 20, 20));
		userlbl.setForeground(new Color(2, 60, 210));
		contentPane.add(userlbl);
		JTextField userText = new JTextField();
		userText.setBounds(170, 70, 200, 35);
		userText.setFont(new Font("Arial", 20, 20));
		contentPane.add(userText);
		JLabel passlbl = new JLabel();
		passlbl.setText("Password");
		passlbl.setBounds(50, 110, 100, 30);
		passlbl.setFont(new Font("Arial", 20, 20));
		passlbl.setForeground(new Color(2, 60, 210));
		contentPane.add(passlbl);

		JPasswordField passText = new JPasswordField();
		passText.setEchoChar('*');
		passText.setBounds(170, 110, 200, 35);
		passText.setFont(new Font("Arial", 20, 20));
		contentPane.add(passText);

		Checkbox passwordVisibility = new Checkbox("Show password");
		passwordVisibility.setBounds(170, 170, 200, 30);
		passwordVisibility.setFont(new Font("Arial", 20, 17));
		contentPane.add(passwordVisibility);

		passwordVisibility.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == ItemEvent.SELECTED) {
					passText.setEchoChar((char) 0);
				} else {
					passText.setEchoChar('*');
				}
			}

		});

		JButton loginbtn = new JButton("Login");
		loginbtn.setBounds(170, 230, 130, 60);
		loginbtn.setFont(new Font("Arial", 20, 20));
		loginbtn.setBackground(new Color(2, 90, 130));
		loginbtn.setForeground(Color.WHITE);
		contentPane.add(loginbtn);

		loginbtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				String password = new String(passText.getPassword());
				if (getAdmin() && adminHandler.checkAdmin(userText.getText(), password)) {
					new AdminGUI(adminHandler.getAdminId(userText.getText(), password));
					setVisible(false);
				} else if (getStudent() && studentHandler.checkStudent(userText.getText(), password)) {
					Student student = studentHandler.getCurrentStudent(userText.getText(), password);
					new BorrowReturnBookGUI(student);
					setVisible(false);
				}
			}
		});

		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
				"clickButton");

		getRootPane().getActionMap().put("clickButton", new AbstractAction() {
			public void actionPerformed(ActionEvent ae) {
				loginbtn.doClick();
			}
		});

		setContentPane(contentPane);
	}

	public boolean getAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public boolean getStudent() {
		return isStudent;
	}

	public void setStudent(boolean isStudent) {
		this.isStudent = isStudent;
	}

}
