package library;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class AdminGUI {

	StudentGUI sgui = new StudentGUI();
	LibraryGUI lgui = new LibraryGUI();
	AdminDAO adminHandler = new AdminDAO();
	
	public AdminGUI(int admin_id){
		JFrame adminFrame = new JFrame();
		adminFrame.setVisible(true);
		adminFrame.setTitle("Admin");
		adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		adminFrame.setBounds(100, 100, 1100, 700);
		adminFrame.setLocationRelativeTo(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(100,170,210));
		panel.setLayout(null);
		
		JLabel loginlbl = new JLabel();
		loginlbl.setBounds(10,20,250,50);
		loginlbl.setBackground(new Color(2,90,130));
		loginlbl.setFont(new Font("Arial", 20,20));
		//loginlbl.setForeground(Color.WHITE);
		loginlbl.setText("Logged in as " + adminHandler.getAdminName(admin_id));
		panel.add(loginlbl);
		
		JButton logoutbtn = new JButton("Logout");
		logoutbtn.setBounds(920,20,100,50);
		logoutbtn.setBackground(new Color(2,90,130));
		logoutbtn.setForeground(Color.WHITE);
		panel.add(logoutbtn);
		
		JPanel forms = new JPanel();
		forms.setLayout(null);
		forms.setBounds(10, 70, 1100, 600);
		forms.setBackground(new Color(100,170,210));
		panel.add(forms);
		
		logoutbtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				adminFrame.setVisible(false);				
				Main.main(null);
			}			
		});
		
		JMenuBar menubar = new JMenuBar();
		
		JMenu studentMenu = new JMenu("Student center");
		menubar.add(studentMenu);
		
		JMenuItem addItem = new JMenuItem("Add a student");
		studentMenu.add(addItem);
		
		addItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {				
				forms.removeAll();
				forms.add(sgui.studentgui(admin_id, false));
				sgui.addGUI();
				forms.revalidate();
				forms.repaint();
			}			
		});
		
		JMenuItem updateItem = new JMenuItem("Update student");
		studentMenu.add(updateItem);
		
		updateItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {				
				forms.removeAll();
				forms.add(sgui.studentgui(admin_id, true));
				sgui.updateGUI("Update", true, true);
				forms.revalidate();
				forms.repaint();
			}			
		});
		
		JMenuItem deleteItem = new JMenuItem("Remove student");
		studentMenu.add(deleteItem);
		
		deleteItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {				
				forms.removeAll();
				forms.add(sgui.studentgui(admin_id, true));
				sgui.updateGUI("Delete", true, true);
				forms.revalidate();
				forms.repaint();
			}			
		});
		
		JMenuItem viewItem = new JMenuItem("View all students");
		studentMenu.add(viewItem);
		
		viewItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {				
				forms.removeAll();
				forms.add(sgui.studentgui(admin_id, true));
				sgui.updateGUI("", true, true);
				forms.revalidate();
				forms.repaint();
			}			
		});
		
		JMenuItem logItem = new JMenuItem("Students log");
		studentMenu.add(logItem);
		
		logItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {	
			//	StudentGUI sgui = new StudentGUI();
				forms.removeAll();
				forms.add(sgui.studentgui(admin_id, false));
				sgui.logDisplay();
				forms.revalidate();
				forms.repaint();
				
			}			
		});
		
		JMenu bookMenu = new JMenu("Book managment");
		menubar.add(studentMenu);
		
		JMenuItem addBookItem = new JMenuItem("Add book");
		bookMenu.add(addBookItem);
		
		addBookItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {	
				forms.removeAll();
				forms.add(lgui.librarygui());
				lgui.addGUI();
				forms.revalidate();
				forms.repaint();
				
			}			
		});
		
		JMenuItem updateBookItem = new JMenuItem("Update book");
		bookMenu.add(updateBookItem);
		
		updateBookItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {	
				forms.removeAll();
				forms.add(lgui.librarygui());
				lgui.updateGUI("Update", true, true);
				forms.revalidate();
				forms.repaint();
				
			}			
		});
		
		JMenuItem deleteBookItem = new JMenuItem("Remove book");
		bookMenu.add(deleteBookItem);
		
		deleteBookItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {	
				forms.removeAll();
				forms.add(lgui.librarygui());
				lgui.updateGUI("Delete", true, true);
				forms.revalidate();
				forms.repaint();
				
			}			
		});
		JMenuItem viewBooksItem = new JMenuItem("View all books");
		bookMenu.add(viewBooksItem);
		
		viewBooksItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {	
				forms.removeAll();
				forms.add(lgui.librarygui());
				lgui.updateGUI("", true, true);
				forms.revalidate();
				forms.repaint();
				
			}			
		});
		
		JMenuItem bookLogItem = new JMenuItem("Books log");
		bookMenu.add(bookLogItem);
		
		bookLogItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {	
				forms.removeAll();
				forms.add(lgui.librarygui());
				lgui.logDisplay();
				forms.revalidate();
				forms.repaint();
				
			}			
		});
		
		menubar.add(bookMenu);
	    adminFrame.setJMenuBar(menubar);
	    
		
		
		ImageIcon image = new ImageIcon("src/books2.jpg");
		JLabel bookImage = new JLabel(image);
		bookImage.setBounds(0, 0, 1000, 560);
		forms.add(bookImage);
		
	/*
		JButton librarybtn = new JButton("Library Management");
		librarybtn.setBounds(550, 30, 250, 70);
		librarybtn.setFont(new Font("Arial", 20, 20));
		librarybtn.setBackground(new Color(2,90,130));
		librarybtn.setForeground(Color.WHITE);
		panel.add(librarybtn);
		librarybtn.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				
				forms.removeAll();
				forms.add(lgui.librarygui());
				forms.revalidate();
				forms.repaint();
			}			
		});
		*/
		adminFrame.add(panel);
	}
	
}
