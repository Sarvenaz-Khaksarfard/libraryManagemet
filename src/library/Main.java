package library;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Main{

	public static void main(String[] args) {
		
		AdminDAO adminhandler = new AdminDAO();
	//	adminhandler.addAdmin("Jennifer", "Taylor", "j_taylor", "linda");
		
		JFrame mainFrame = new JFrame();
	//	mainFrame.setSize(400, 400);

		mainFrame.setTitle("Library Management System");
		mainFrame.setBounds(100, 100, 600, 500);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		panel.setBackground(new Color(100,170,210));
		panel.setLayout(null);
		JLabel title = new JLabel();
		title.setBounds(10, 10, 550, 100);
		title.setText("Welcome to Library Management System");
		title.setFont(new Font("Aria", 30,30));
		title.setForeground(new Color(2,60,210));
		panel.add(title);
	/*	JLabel title2 = new JLabel();
		title2.setBounds(100, 120, 400, 80);
		title2.setText("Choose one of the options below to sign in");
		title2.setFont(new Font("Arial", 20,20));
		title2.setForeground(new Color(2,60,210));
		panel.add(title2);*/
		JButton adminbtn = new JButton("Admin");
		adminbtn.setBounds(120, 150, 150, 70);
		adminbtn.setFont(new Font("Arial", 20, 20));
		adminbtn.setBackground(new Color(2,90,130));
		adminbtn.setForeground(Color.WHITE);
		
		panel.add(adminbtn);
		adminbtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							Login frame = new Login();
							frame.setSize(500, 350);
							frame.setVisible(true);
							frame.setAdmin(true);
							frame.setStudent(false);
							mainFrame.setVisible(false);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}		
		});
		JButton studentbtn = new JButton("Student");
		studentbtn.setBounds(290, 150, 150, 70);
		studentbtn.setFont(new Font("Arial", 20, 20));
		studentbtn.setBackground(new Color(2,90,130));
		studentbtn.setForeground(Color.WHITE);
		panel.add(studentbtn);
		
		studentbtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							Login frame = new Login();
							frame.setSize(500, 350);
							frame.setVisible(true);
							frame.setAdmin(true);
							frame.setStudent(true);
							frame.setAdmin(false);
							mainFrame.setVisible(false);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}		
		});
		ImageIcon image = new ImageIcon("src/books.jpg");
		JLabel bookImage = new JLabel(image);
		bookImage.setBounds(10, 280, 570, 150);
		panel.add(bookImage);
		mainFrame.add(panel);
		mainFrame.revalidate();
		mainFrame.repaint();
	}
}
