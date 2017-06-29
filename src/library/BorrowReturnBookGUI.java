package library;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class BorrowReturnBookGUI {

	BookDAO bookHandler = new BookDAO();
	Object[] cols = { "Book ID", "Title", "Author", "Edition", "Year", "ISBN", "Availability" };
	JPanel books;
	Student student = new Student();

	public BorrowReturnBookGUI(Student student) {
		JFrame Frame = new JFrame();
		Frame.setSize(400, 400);
		Frame.setVisible(true);
		Frame.setTitle(student.getfname() + " " + student.getlname() + " " + student.getId());
		Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Frame.setBounds(100, 100, 1100, 700);
		Frame.setLocationRelativeTo(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(100, 170, 210));
		panel.setLayout(null);
		Frame.add(panel);

		JButton logoutbtn = new JButton("Logout");
		logoutbtn.setBounds(920, 20, 100, 50);
		logoutbtn.setBackground(new Color(2, 90, 130));
		logoutbtn.setForeground(Color.WHITE);
		panel.add(logoutbtn);

		logoutbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Frame.setVisible(false);
				Main.main(null);
			}
		});

		books = new JPanel();
		books.setLayout(null);
		books.setBounds(10, 120, 1100, 500);
		books.setBackground(new Color(100, 170, 210));
		panel.add(books);

		JRadioButton borrowBook = new JRadioButton("Borrow books", true);
		borrowBook.setBounds(40, 40, 200, 50);
		borrowBook.setFont(new Font("Arial", 20, 20));
		borrowBook.setBackground(new Color(100, 170, 210));
		borrowBook.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				Object[][] rows = bookHandler.getAllBooks();
				books.removeAll();
				displayBooks(student, "Borrow", rows, cols);
				books.revalidate();
				books.repaint();
			}
		});

		JRadioButton returnBook = new JRadioButton("Return books");
		returnBook.setBounds(300, 40, 200, 50);

		returnBook.setFont(new Font("Arial", 20, 20));
		returnBook.setBackground(new Color(100, 170, 210));
		returnBook.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				books.removeAll();
				Object[] cols = { "Book Id", "Title", "Author", "Edition", "Year", "ISBN" };
				BorrowReturnDAO brHandler = new BorrowReturnDAO();
				Object[][] rows = brHandler.getStudentBooksInfo(brHandler.getStudentsBooksId(student.getId()));
				displayBooks(student, "Return", rows, cols);
				books.revalidate();
				books.repaint();
			}
		});

		ButtonGroup group = new ButtonGroup();

		group.add(borrowBook);
		group.add(returnBook);

		panel.add(borrowBook);
		panel.add(returnBook);

		Object[][] rows = bookHandler.getAllBooks();
		books.removeAll();
		displayBooks(student, "Borrow", rows, cols);

		books.revalidate();
		books.repaint();
	}

	int row = -1;

	public void displayBooks(Student student, String label, Object[][] rows, Object[] cols) {

		books.removeAll();
		books.revalidate();
		books.repaint();

		DefaultTableModel model = new DefaultTableModel(rows, cols) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		JTable table = new JTable(model);
		table.setBackground(Color.WHITE);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scroll = new JScrollPane(table);
		scroll.setBounds(20, 60, 970, 300);
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

		JButton button = new JButton(label);
		button.setBounds(550, 400, 130, 50);
		button.setFont(new Font("Arial", 20, 18));
		button.setBackground(new Color(2, 90, 130));
		button.setForeground(Color.WHITE);
		books.add(button);

		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (label.equals("Borrow")) {
					if (bookHandler.checkAvailability((Integer) table.getValueAt(row, 0))) {
						JOptionPane.showMessageDialog(null, "Book " + (Integer) table.getValueAt(row, 0)
								+ " is successfully borrowed by " + student.getfname() + " " + student.getlname());

						model.setValueAt("No", row, 6);
						BorrowReturnDAO brHandler = new BorrowReturnDAO();
						brHandler.addBorrowDetails(student.getId(), student.getfname(), student.getlname(),
								(Integer) table.getValueAt(row, 0), table.getValueAt(row, 1).toString());
						bookHandler.updateAvailability((Integer) table.getValueAt(row, 0), "No");
						
					} else {
						JOptionPane.showMessageDialog(null, "This book is currently not available");
					}
				}

				else if (label.equals("Return")) {
					BorrowReturnDAO brHandler = new BorrowReturnDAO();
					brHandler.returnBook((Integer) table.getValueAt(row, 0));
					bookHandler.updateAvailability((Integer) table.getValueAt(row, 0), "Yes");
					JOptionPane.showMessageDialog(null, "Book " + (Integer) table.getValueAt(row, 0)
							+ " is successfully returned by " + student.getfname() + " " + student.getlname());
					model.removeRow(row);
				}
			}
		});
		books.add(scroll);
	}
}
