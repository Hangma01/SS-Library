package page;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;



import controller.BookController;
import custom.CustomFont;
import book.Book;



public class Manager_BookInfo extends JPanel{
	DefaultTableModel tableModel;
	JTable bookTable;
	private JButton jBtnAddRow;
	private JButton jBtnEditRow;
	private JButton jBtnDelRow;
	BookInsertDailog dia;
	CustomFont CustomFont = new CustomFont();
	HashMap<String, String> exceptionList = new HashMap<String, String>();
	public Manager_BookInfo(JPanel panel) throws SQLException {
		exceptionList.put("class java.lang.String cannot be cast to class java.time.LocalDateTime (java.lang.String and java.time.LocalDateTime are in module java.base of loader 'bootstrap')", "날짜는 수정할 수 없습니다.");
		ArrayList<Book> BookList = BookController.select();
		dia = new BookInsertDailog();
		Rectangle rect = panel.getBounds();
		setPreferredSize(rect.getSize());
		setLayout(new BorderLayout());

		
		
		JPanel jpMain = new JPanel();
		jpMain.setLayout(null);
		jpMain.setBackground(Color.white);
		
		
		JPanel pContTitle = new JPanel();
//	      pContTitle.setPreferredSize(new Dimension(0, 79));
		 pContTitle.setBounds(10, 5, 970, 79);
	      pContTitle.setLayout(null);
	      pContTitle.setBackground(Color.white);
	      jpMain.add(pContTitle);

	      JLabel lContTitle = new JLabel("도서 관리");
	      lContTitle.setBounds(0, 0, 970, 75);
	      lContTitle.setFont(CustomFont.r31);
	      lContTitle.setBorder(new CustomLineBorder(Color.black, 2, CustomLineBorder.BOTTOM));
	      pContTitle.add(lContTitle);

//		jpMain.add(ManagerTitle1);
		
		JPanel tablePanel = new JPanel();
		tablePanel.setBounds(0, 150, 1000, 455);
		
		String header[] = {"도서 코드","제목","장르","저자","이미지","출판사","등록일","발행일","청구기호"};
		
		
		tableModel = new DefaultTableModel(header, 0);
		bookTable = new JTable(tableModel);
		if(BookList != null) {
			Object[][] content = new Object[BookList.size()][header.length];
	
			for(int i = 0; i < BookList.size(); i++) {
				Book temp = BookList.get(i);
				
				tableModel.addRow(new Object[] {
						temp.getBook_Code(),
						temp.getBook_Title(),
						temp.getBook_Type(),
						temp.getBook_Author(),
						temp.getBook_Img(),
						temp.getBook_Publisher(),
						temp.getBook_Insert_Date(),
						temp.getBook_Publish_Date(),
						temp.getBook_ClaimingSymbol()
				});

			}
		}
		bookTable.setFont(CustomFont.rp13);
		tablePanel.add(bookTable);
		JScrollPane scrollpane = new JScrollPane(bookTable);
		// 스크롤 크기 설정
		scrollpane.setPreferredSize(new Dimension(970,450));
		// 테이블과 스크롤을 연결해서 화면에 보이기
		scrollpane.setViewportView(bookTable);
		tablePanel.setBackground(Color.white);
		tablePanel.add(scrollpane);
		add(tablePanel);
		jBtnAddRow = new JButton("추가");
		jBtnAddRow.setBounds(795, 615, 60, 20);
		jBtnAddRow.setFont(CustomFont.rp13);
		jBtnAddRow.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dia.setVisible(true);
				
			}
		});
		
		add(jBtnAddRow);
		jBtnEditRow = new JButton("수정");
		jBtnEditRow.setBounds(860, 615, 60, 20);
		jBtnEditRow.setFont(CustomFont.rp13);
		jBtnEditRow.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(e.getActionCommand());
				try {
				 DefaultTableModel model2 = (DefaultTableModel)bookTable.getModel();
                 int row = bookTable.getSelectedRow();
                 String value0 = (String) model2.getValueAt(row, 0);
                 String value1 = (String) model2.getValueAt(row, 1);
                 String value2 = (String) model2.getValueAt(row, 2);
                 String value3 = (String) model2.getValueAt(row, 3);
                 String value4 = (String) model2.getValueAt(row, 4);
                 String value5 = (String) model2.getValueAt(row, 5);
                 LocalDateTime value6 = (LocalDateTime) model2.getValueAt(row, 6);
                 LocalDateTime value7 = (LocalDateTime) model2.getValueAt(row, 7);
                 String value8 = (String) model2.getValueAt(row, 8);

                 Book EditBook = new Book(value0,value1,value2,value3,value4,value5,value6,value7,value8);
                 int res = JOptionPane.showConfirmDialog(null,"수정 하시겠습니까?","Messege",JOptionPane.YES_NO_OPTION);
                 if(res == JOptionPane.YES_OPTION) {
                 try {
					BookController.EditBook(EditBook);
					JOptionPane.showMessageDialog(null, "수정 되었습니다", "Messege", JOptionPane.PLAIN_MESSAGE, null);
					ManagerPage.update();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                 }
                 }catch(Exception e1) {
                	 if(exceptionList.get(e1.getMessage()) != null) {
     					JOptionPane.showMessageDialog(null, exceptionList.get(e1.getMessage()), "Messege", JOptionPane.PLAIN_MESSAGE, null);
     				}
     				else {
     					JOptionPane.showMessageDialog(null, "커서위치를 확인 하세요", "Messege", JOptionPane.PLAIN_MESSAGE, null);
     					System.out.println(e1.getMessage());
     					
     				} 					
                 }
                 }  
				
	
		});
		add(jBtnEditRow);
		jBtnDelRow = new JButton("삭제");
		jBtnDelRow.setBounds(925, 615, 60, 20);
		jBtnDelRow.setFont(CustomFont.rp13);
		jBtnDelRow.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
				 System.out.println(e.getActionCommand());
				 DefaultTableModel model2 = (DefaultTableModel)bookTable.getModel();
                 int row = bookTable.getSelectedRow();
                 String Book_code = (String) model2.getValueAt(row, 0);
                 int res = JOptionPane.showConfirmDialog(null,"정말 삭제하겠습니까?","Messege",JOptionPane.YES_NO_OPTION);
                 if(res == JOptionPane.YES_OPTION) {
                 try {
					BookController.DelBook(Book_code);
					JOptionPane.showMessageDialog(null, "삭제 되었습니다", "Messege", JOptionPane.PLAIN_MESSAGE, null);
					ManagerPage.update();
				} catch (SQLException e1) {
					
					System.out.println(e1.getMessage());
				}
                }
			}
			catch(Exception e1) {
			 
				if(exceptionList.get(e1.getMessage()) != null) {
					JOptionPane.showMessageDialog(null, exceptionList.get(e1.getMessage()), "Messege", JOptionPane.PLAIN_MESSAGE, null);
				}
				else {
					JOptionPane.showMessageDialog(null, "커서 위치를 확인하세요", "Messege", JOptionPane.PLAIN_MESSAGE, null);
					
					
				}					
              
				}
			}
		});
		add(jBtnDelRow);
		add(jpMain);
		
		
		
		setVisible(true);
	}
	
}
