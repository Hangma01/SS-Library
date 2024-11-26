package page;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import page.*;
import book.*;
import controller.BookController;
import custom.*;


public class BookInsertDailog extends JDialog{
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	JTextField tf_book_code,tf_book_Title,tf_book_Type,tf_book_Author,
				tf_book_Img,tf_book_Publisher,tf_book_Insert_Date,
				tf_book_Publish_Date,tf_book_ClaimingSymbol = null;
	
	String book_code,book_Title,book_Type,book_Author,book_Img,book_Publisher,book_ClaimingSymbol;
	
	LocalDateTime book_Insert_Date,book_Publish_Date;
	JButton btn = null;
	CustomFont CustomFont = new CustomFont();
	public BookInsertDailog() {
		setLocation(540,200);
		setLayout(null);
		setSize(800, 700);
		this.setFont(CustomFont.rp23);
		
		JPanel PTitle = new JPanel();
		PTitle.setBounds(0,0,800,100);
		PTitle.setLayout(null);
		
		
		JLabel title = new JLabel("도서 정보 입력");
		title.setFont(CustomFont.r31);
		title.setBounds(280,0,400,100);
		PTitle.add(title);
		this.add(PTitle);
//		JPanel code = new JPanel();
//		code.setBounds(40, 150, 700, 50);
//		JTextArea codeText = new JTextArea("도서 코드 : ");
//		tf_book_code = new JTextField(40);
//		code.add(codeText);
//		code.add(tf_book_code);
//		this.add(code);
		JPanel TextArea = new JPanel();
		TextArea.setLayout(null);
		TextArea.setBounds(0,100,150,660);
		
		
		JPanel TextField = new JPanel();
		TextField.setLayout(null);
		TextField.setBounds(150,100,650,660);
		this.add(TextArea);
		this.add(TextField);
		
		
		JLabel TitleText = new JLabel("도서 제목 : ");
		
		TitleText.setBounds(50, 30, 100, 50);
		TitleText.setFont(CustomFont.rp15);
		TitleText.setHorizontalAlignment(4);
		TextArea.add(TitleText);
		tf_book_Title = new JTextField(40);
		tf_book_Title.setBounds(0, 43, 550, 20);
		TextField.add(tf_book_Title);
		
		
		
		
		JLabel TypeText = new JLabel("도서 장르 : ");
		
		TypeText.setBounds(50, 90, 100, 50);
		TypeText.setFont(CustomFont.rp15);
		TypeText.setHorizontalAlignment(4);
		TextArea.add(TypeText);
		tf_book_Type = new JTextField(40);
		tf_book_Type.setBounds(0, 103, 550, 20);
		TextField.add(tf_book_Type);
		
		
		JLabel AuthorText = new JLabel("저자  : ");
		AuthorText.setBounds(50, 150, 100, 50);
		AuthorText.setFont(CustomFont.rp15);
		AuthorText.setHorizontalAlignment(4);
		TextArea.add(AuthorText);
		tf_book_Author = new JTextField(40);
		tf_book_Author.setBounds(0, 163, 550, 20);
	
		TextField.add(tf_book_Author);
		
		
		JLabel ImgText = new JLabel("이미지  : ");
		ImgText.setBounds(50, 210, 100, 50);
		ImgText.setFont(CustomFont.rp15);
		ImgText.setHorizontalAlignment(4);
		TextArea.add(ImgText);
		tf_book_Img = new JTextField(40);
		tf_book_Img.setBounds(0, 223, 550, 20);
		TextField.add(tf_book_Img);
		
		
		
		JLabel PublisherText = new JLabel("출판사 : ");
		PublisherText.setBounds(50, 270, 100, 50);
		PublisherText.setFont(CustomFont.rp15);
		PublisherText.setHorizontalAlignment(4);
		TextArea.add(PublisherText);
		tf_book_Publisher = new JTextField(40);
		tf_book_Publisher.setBounds(0, 283, 550, 20);
		TextField.add(tf_book_Publisher);
		
		
		
		JLabel book_Insert_DateText = new JLabel("등록일 : ");
		book_Insert_DateText.setBounds(0, 330, 150, 50);
		book_Insert_DateText.setFont(CustomFont.rp15);
		book_Insert_DateText.setHorizontalAlignment(4);
		TextArea.add(book_Insert_DateText);
		tf_book_Insert_Date = new JTextField(40);
		tf_book_Insert_Date.setBounds(0, 343, 550, 20);
		TextField.add(tf_book_Insert_Date);
		
		
		
		JLabel book_Publish_DateText = new JLabel("출판일 : ");
		book_Publish_DateText.setBounds(0, 390, 150, 50);
		book_Publish_DateText.setFont(CustomFont.rp15);
		book_Publish_DateText.setHorizontalAlignment(4);
		TextArea.add(book_Publish_DateText);
		tf_book_Publish_Date = new JTextField(40);
		tf_book_Publish_Date.setBounds(0, 403, 550, 20);
		TextField.add(tf_book_Publish_Date);
		
		
		
		JLabel ClaimingSymbolText = new JLabel("청구 기호 : ");
		ClaimingSymbolText.setBounds(50, 450, 100, 50);
		ClaimingSymbolText.setFont(CustomFont.rp15);
		ClaimingSymbolText.setHorizontalAlignment(4);
		TextArea.add(ClaimingSymbolText);
		tf_book_ClaimingSymbol = new JTextField(40);
		tf_book_ClaimingSymbol.setBounds(0, 463, 550, 20);
		TextField.add(tf_book_ClaimingSymbol);
		
		
		
		btn = new JButton("입력");
		btn.setBounds(490,500,60,30);
		TextField.add(btn);
		HashMap<String, String> myexception = new HashMap<String, String>();
		myexception.put("Text '' could not be parsed at index 0", "값을 확인해주세요");
		myexception.put("ORA-01400", "도서 코드에는 빈값이 들어갈 수 없습니다");
		System.out.println("ORA-01400");
		btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
				int res = JOptionPane.showConfirmDialog(null,"추가하겠습니까?","Messege",JOptionPane.YES_NO_OPTION);
                if(res == JOptionPane.YES_OPTION) {
                try {
            		book_Title = tf_book_Title.getText();
            		book_Type = tf_book_Type.getText();
            		book_Author = tf_book_Author.getText();
            		book_Img = tf_book_Img.getText();
            		book_Publisher = tf_book_Publisher.getText();
            		book_Insert_Date = LocalDate.parse(tf_book_Insert_Date.getText(),formatter).atStartOfDay();
            		book_Publish_Date = LocalDate.parse(tf_book_Publish_Date.getText(),formatter).atStartOfDay();
            		book_ClaimingSymbol = tf_book_ClaimingSymbol.getText();
            		insertBook insertBook = new insertBook(book_Title,book_Type,book_Author,book_Img,book_Publisher,book_Insert_Date,book_Publish_Date,book_ClaimingSymbol);
					BookController.insertBook(insertBook);
					JOptionPane.showMessageDialog(null, "추가 되었습니다", "Messege", JOptionPane.PLAIN_MESSAGE, null);
					ManagerPage.update();
					setVisible(false);
				} catch (SQLException e1) {
					
					JOptionPane.showMessageDialog(null, myexception.get(e1.getMessage().substring(0, 9)), "Messege", JOptionPane.PLAIN_MESSAGE, null);
		
				}
               }
				}catch(Exception e2) {
					//실행문
					
				JOptionPane.showMessageDialog(null, myexception.get(e2.getMessage()), "Messege", JOptionPane.PLAIN_MESSAGE, null);
					
				}
			}
		});
	}
	

}
