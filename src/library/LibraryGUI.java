package library;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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

public class LibraryGUI {

	Object[] cols = { "Book ID", "Title", "Author", "Edition", "Year", "ISBN", "Availability" };
	Object[] logcols = { "Book ID", "Title", "Author", "Borrowed by" };
	JPanel content;
	BookDAO bookHandler = new BookDAO();
	AdminDAO adminHandler = new AdminDAO();
	BorrowReturnDAO brHandler = new BorrowReturnDAO();
	HashMap<Integer, String> map = new HashMap<Integer, String>();

	public JPanel librarygui() {

		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		contentPane.setBounds(10, 10, 1050, 700);
		contentPane.setBackground(new Color(100, 170, 210));

		/*	
		*/
		content = new JPanel();
		content.setLayout(null);
		content.setBounds(10, 10, 1000, 800);
		content.setBackground(new Color(100, 170, 210));
		Border border = BorderFactory.createLineBorder(Color.BLACK, 2, true);
		content.setBorder(border);

		ImageIcon image = new ImageIcon("src/books4.jpg");
		JLabel bookImage = new JLabel(image);
		bookImage.setBounds(10, 20, 930, 550);
		content.add(bookImage);

		contentPane.add(content);

		return contentPane;
	}

	public void addGUI() {
		Object[] vals = new Object[cols.length];
		for (int i = 0; i < vals.length; i++)
			vals[i] = "";
		createForm(vals, "Add");
	}

	public void updateGUI(String label, boolean check_search, boolean check_table) {
		Object[][] rows = bookHandler.getAllBooks();
		displayBookTable(label, check_search, check_table, rows, cols);
	}

	public void logDisplay() {
		Object[][] rows = brHandler.getAllBorrowdBooks();
		displayBookTable("", false, false, rows, logcols);
	}

	public void createForm(Object[] vals, String btnLabel) {
		content.removeAll();
		content.revalidate();
		content.repaint();

		ArrayList<JTextField> textFields = new ArrayList<JTextField>();

		int c = 0;

		JLabel idlbl = new JLabel();
		idlbl.setText("Book ID");
		idlbl.setBounds(30, 30, 100, 30);
		idlbl.setFont(new Font("Arial", 20, 20));
		content.add(idlbl);
		JTextField idText = new JTextField();
		idText.setBounds(150, 30, 200, 35);
		idText.setFont(new Font("Arial", 20, 20));
		idText.setText(vals[c].toString());
		textFields.add(idText);
		content.add(idText);
		JLabel namelbl = new JLabel();
		namelbl.setText("Book Title");
		namelbl.setBounds(30, 75, 100, 30);
		namelbl.setFont(new Font("Arial", 20, 20));
		content.add(namelbl);
		JTextField nameText = new JTextField();
		nameText.setBounds(150, 75, 200, 35);
		nameText.setFont(new Font("Arial", 20, 20));
		nameText.setText(vals[++c].toString());
		textFields.add(nameText);
		content.add(nameText);
		JLabel authorlbl = new JLabel();
		authorlbl.setText("Author");
		authorlbl.setBounds(30, 120, 100, 30);
		authorlbl.setFont(new Font("Arial", 20, 20));
		content.add(authorlbl);
		JTextField authorText = new JTextField();
		authorText.setBounds(150, 120, 200, 35);
		authorText.setFont(new Font("Arial", 20, 20));
		authorText.setText(vals[++c].toString());
		textFields.add(authorText);
		content.add(authorText);
		JLabel editionlbl = new JLabel();
		editionlbl.setText("Edition");
		editionlbl.setBounds(30, 165, 100, 30);
		editionlbl.setFont(new Font("Arial", 20, 20));
		content.add(editionlbl);
		JTextField editionText = new JTextField();
		editionText.setBounds(150, 165, 200, 35);
		editionText.setFont(new Font("Arial", 20, 20));
		editionText.setText(vals[++c].toString());
		textFields.add(editionText);
		content.add(editionText);
		JLabel yearlbl = new JLabel();
		yearlbl.setText("Year");
		yearlbl.setBounds(380, 30, 100, 30);
		yearlbl.setFont(new Font("Arial", 20, 20));
		content.add(yearlbl);
		JTextField yearText = new JTextField();
		yearText.setBounds(500, 30, 200, 35);
		yearText.setFont(new Font("Arial", 20, 20));
		yearText.setText(vals[++c].toString());
		textFields.add(yearText);
		content.add(yearText);

		JLabel isbnlbl = new JLabel();
		isbnlbl.setText("ISBN");
		isbnlbl.setBounds(380, 75, 100, 30);
		isbnlbl.setFont(new Font("Arial", 20, 20));
		content.add(isbnlbl);
		JTextField isbnText = new JTextField();
		isbnText.setBounds(500, 75, 200, 35);
		isbnText.setFont(new Font("Arial", 20, 20));
		isbnText.setText(vals[++c].toString());
		textFields.add(isbnText);
		content.add(isbnText);

		JLabel availbl = new JLabel();
		availbl.setText("Availability");
		availbl.setBounds(380, 120, 100, 30);
		availbl.setFont(new Font("Arial", 20, 20));
		content.add(availbl);
		JTextField availText = new JTextField();
		availText.setBounds(500, 120, 200, 35);
		availText.setFont(new Font("Arial", 20, 20));
		availText.setText(vals[++c].toString());
		textFields.add(availText);
		content.add(availText);

		ImageIcon image = new ImageIcon("src/books3.png");
		JLabel imagelbl = new JLabel(image);
		imagelbl.setBounds(790, 20, 190, 300);
		content.add(imagelbl);

		JButton savebtn = new JButton(btnLabel);
		savebtn.setBounds(530, 270, 170, 60);
		savebtn.setFont(new Font("Arial", 20, 20));
		savebtn.setBackground(new Color(2, 90, 130));
		savebtn.setForeground(Color.WHITE);
		content.add(savebtn);

		savebtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				BookDAO bookHandler = new BookDAO();
				StudentGUI sgui = new StudentGUI();
				if (sgui.checkTextFields(textFields) && sgui.checkType(textFields.get(0), "Book Id")
						&& sgui.checkType(textFields.get(3), "Edition") && sgui.checkType(textFields.get(4), "Year")) {
					if (btnLabel.equals("Add") && bookHandler.checkBookId((Integer.parseInt(idText.getText())))) {
						Book book = new Book();
						book.setId((Integer.parseInt(idText.getText())));
						book.setTitle(nameText.getText());
						book.setAuthor(authorText.getText());
						book.setEdition(Integer.parseInt(editionText.getText()));
						book.setYear(Integer.parseInt(yearText.getText()));
						book.setIsbn(isbnText.getText());
						book.setAvailability(availText.getText());

						bookHandler.addBook(book);
						JOptionPane.showMessageDialog(null, "Book added successfully");
					}

					else if (btnLabel.equals("Apply Changes")) {
						int c = 1;
						map.put(c, idText.getText());
						map.put(++c, nameText.getText());
						map.put(++c, authorText.getText());
						map.put(++c, editionText.getText());
						map.put(++c, yearText.getText());
						map.put(++c, isbnText.getText());
						map.put(++c, availText.getText());
						if (bookHandler.updateBook(map, vals[0]))
							JOptionPane.showMessageDialog(null, "Book updated successfully");
					}
				}
			}
		});
	}

	int row = -1;

	public void displayBookTable(String label, boolean check_search, boolean check_table, Object[][] rows,
			Object[] cols) {
		row = -1;
		content.removeAll();
		content.revalidate();
		content.repaint();

		if (check_search == true) {
			JLabel searchlbl = new JLabel("Search book by book title");
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
					if (checkType(searchtxt, "Book Id")) {
						Object[][] rows = bookHandler.searchBook(searchtxt.getText());
						displayBookTable(label, check_search, check_table, rows, cols);
						if (rows.length == 0) {
							JOptionPane.showMessageDialog(null, "No book found");
						}
					}

				}
			});

		}

		if (label != "") {
			JLabel notelbl = new JLabel();
			notelbl.setText("Please select the book that you want to " + label);
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
		table.setBackground(Color.WHITE);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

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
						name = "book_id";
						break;
					case 1:
						name = "title";
						break;
					case 2:
						name = "author";
						break;
					case 3:
						name = "edition";
						break;
					case 4:
						name = "year";
						break;
					case 5:
						name = "isbn";
						break;
					case 6:
						name = "availability";
						break;
					}

					Object[][] rows = bookHandler.sortBooks(name);
					displayBookTable(label, check_search, check_table, rows, cols);
				}

				else if (check_table == false) {
					switch (col) {
					case 0:
						name = "book_id";
						break;
					case 1:
						name = "title";
						break;
					case 2:
						name = "author";
						break;
					case 3:
						name = "student_id";
						break;

					}
					Object[][] rows;
					rows = brHandler.sortBorrowedBooks(name);
					displayBookTable(label, check_search, check_table, rows, cols);
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
						if (table.getModel().getValueAt(row, 6).equals("Yes")) {
							bookHandler.deleteBook((Integer) table.getValueAt(row, 0));
							model.removeRow(row);
							JOptionPane.showMessageDialog(null, "Book deleted successfully");
						} else if (table.getModel().getValueAt(row, 6).equals("No")) {
							JOptionPane.showMessageDialog(null,
									"This book can not be removed, since it is currently borrowed");
						}
					} else if (label.equals("Update") && row >= 0) {
						Object[] vals = new Object[cols.length];
						for (int i = 0; i < cols.length; i++) {
							vals[i] = table.getValueAt(row, i);
						}
						createForm(vals, "Apply Changes");
					}
				}

			});
		}
		content.add(scroll);
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
