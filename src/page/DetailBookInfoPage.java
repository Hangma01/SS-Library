package page;


import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.io.File;
import java.sql.SQLException;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import javax.swing.border.LineBorder;

import book.Book;
import book.BookDetail;
import controller.BookDetailController;
import controller.BookController;
import custom.CustomFont;
import custom.CustomLineBorder;

public class DetailBookInfoPage extends JPanel implements ActionListener, MouseListener{
	public CustomFont customFont = new CustomFont();
	private JPanel pContent;
	private JButton btnBookInfo;
	private JButton btnAuthorInfo;
	private JButton btnReviewInfo;
	private JTextArea taContBookInfo;
	private JLabel lContBookInfo;
	private JPanel pContBookInfo;
	private int setMinLine = 5;
	private BookController bookController = new BookController();
	private BookDetailController bookDetailController = new BookDetailController();
	private BookDetail bookDetail;
	private JLabel lBackWindow;
	private JPanel pDetailInfo;
	private JScrollPane scroll2;
	
	public DetailBookInfoPage(JPanel pContent, JScrollPane scroll2, JPanel pDetailInfo, String sBookCode) {

		this.pContent = pContent;
		this.pDetailInfo = pDetailInfo;
		this.scroll2 = scroll2;
		pContent.setVisible(false);
		scroll2.setVisible(false);
		pDetailInfo.setVisible(true);

		setPreferredSize(new Dimension(1045, 700));
		setBackground(Color.white);
		
		String[] BookInfo = { "책 소개", "저자소개", "서평" };
		
		


		JPanel pContListBox = new JPanel();
		pContListBox.setLayout(new BoxLayout(pContListBox, BoxLayout.Y_AXIS));

		Book book = null;
		// 데이터 가져오기
		try {
			book = bookController.select(sBookCode);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// 상세정보 타이틀
		JPanel pContTitle = new JPanel();
		pContTitle.setLayout(null);
		pContTitle.setPreferredSize(new Dimension(0, 65));
		pContTitle.setBackground(Color.white);
//		pContTitle.setBorder(new CustomLineBorder(Color.black, 2, CustomLineBorder.BOTTOM));
		pContListBox.add(pContTitle);
		
		
		JPanel pSubContTitle = new JPanel();
		pSubContTitle.setLayout(null);
		pSubContTitle.setBounds(0, 0, 970, 65);
		pSubContTitle.setBorder(new CustomLineBorder(Color.black, 2, CustomLineBorder.BOTTOM));
		pSubContTitle.setBackground(Color.white);
		pContTitle.add(pSubContTitle);

		JLabel lContTitleKo = new JLabel("도서상세정보");
		lContTitleKo.setBounds(0, 5, 200, 40);
		lContTitleKo.setFont(customFont.r31);
		lContTitleKo.setBackground(Color.white);
		pSubContTitle.add(lContTitleKo);

		JLabel lContTitleEn = new JLabel("Detail Information");
		lContTitleEn.setBounds(210, 15, 180, 20);
		lContTitleEn.setFont(customFont.r17);
		lContTitleEn.setBackground(Color.white);
		pSubContTitle.add(lContTitleEn);
		
		
		

		// 책 이미지 및 정보
		JPanel pContInfo = new JPanel();
		pContInfo.setLayout(null);
		pContInfo.setPreferredSize(new Dimension(0, 360));
		pContInfo.setBackground(Color.white);
		pContListBox.add(pContInfo);

		
		ImageIcon imgIcon = new ImageIcon("./img/backPageIcon.png");
		Image img = imgIcon.getImage();
		Image changeImg = img.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		lBackWindow = new JLabel(new ImageIcon(changeImg));
		lBackWindow.setBounds(0, 20, 30, 30);
		lBackWindow.setHorizontalAlignment(JLabel.LEFT);
		lBackWindow.addMouseListener(this);
		lBackWindow.setCursor(new Cursor(Cursor.HAND_CURSOR));
		pContInfo.add(lBackWindow);
		
		
		
		JLabel lContInfoImg = null;
		ImageIcon imgIcon2 = new ImageIcon(book.getBook_Img());
		
		// 이미지 파일 있는지 체크
		File file = new File(imgIcon2.toString());
		if(file.exists()) {
			Image img2 = imgIcon2.getImage();
			Image changeImg2 = img2.getScaledInstance(190, 250, Image.SCALE_SMOOTH);
			lContInfoImg = new JLabel(new ImageIcon(changeImg2));
		}else {
			imgIcon2 = new ImageIcon("./img/no_image.jpg");
			Image img2 = imgIcon2.getImage();
			Image changeImg2 = img2.getScaledInstance(190, 250, Image.SCALE_SMOOTH);
			lContInfoImg = new JLabel(new ImageIcon(changeImg2));
		}
		
		lContInfoImg.setBounds(0, 70, 190, 250);
		lContInfoImg.setBorder(new LineBorder(Color.lightGray,1));
		pContInfo.add(lContInfoImg);

		JLabel lContInfoTitle = new JLabel(book.getBook_Title());
		lContInfoTitle.setBounds(250, 85, 650, 40);
		lContInfoTitle.setFont(customFont.r18);
		pContInfo.add(lContInfoTitle);
		
		

		// 저자, 출판사
		JPanel pContInfo1 = new JPanel();
		pContInfo1.setLayout(null);
		pContInfo1.setBounds(250, 125, 701, 70);
		pContInfo1.setBorder(new CustomLineBorder(Color.LIGHT_GRAY, 1, CustomLineBorder.TOP | CustomLineBorder.BOTTOM));
		pContInfo1.setBackground(Color.white);
		pContInfo.add(pContInfo1);

		JLabel lContInfo1Author1 = new JLabel("저자");
		lContInfo1Author1.setBounds(0, 0, 70, 35);
		lContInfo1Author1.setFont(customFont.rp15);
		// pContInfo1Author1.setBorder(new LineBorder(Color.blue));
		pContInfo1.add(lContInfo1Author1);

		JLabel lContInfo1Author2 = new JLabel("|     ");
		lContInfo1Author2.setBounds(80, 0, 100, 35);
		lContInfo1Author2.setFont(customFont.rp15);
		pContInfo1.add(lContInfo1Author2);
		
		JLabel lContInfo1AuthorData = new JLabel(book.getBook_Author());
		lContInfo1AuthorData.setBounds(90, 0, 560, 35);
		lContInfo1AuthorData.setFont(customFont.rp15);
	//	lContInfo1AuthorData.setBorder(new LineBorder(Color.red));
		pContInfo1.add(lContInfo1AuthorData);

		JLabel lContInfo1Pb1 = new JLabel("출판사");
		lContInfo1Pb1.setBounds(0, 35, 70, 35);
		lContInfo1Pb1.setFont(customFont.rp15);
		// pContInfo1Pb1.setBorder(new LineBorder(Color.blue));
		pContInfo1.add(lContInfo1Pb1);

		JLabel lContInfo1Pb2 = new JLabel("|     ");
		lContInfo1Pb2.setBounds(80, 35, 100, 35);
		lContInfo1Pb2.setFont(customFont.rp15);
		pContInfo1.add(lContInfo1Pb2);
		
		JLabel lContInfo1PbData = new JLabel(book.getBook_Publisher());
		lContInfo1PbData.setBounds(90, 35, 560, 35);
		lContInfo1PbData.setFont(customFont.rp15);
	//	lContInfo1Pb2Data.setBorder(new LineBorder(Color.red));
		pContInfo1.add(lContInfo1PbData);

		// 출판일, 가격, code, 장르, 등록번호, 청구기호
		JPanel pContInfo2 = new JPanel();
		pContInfo2.setLayout(null);
		pContInfo2.setBounds(250, 195, 650, 110);
		pContInfo2.setBackground(Color.white);
		pContInfo.add(pContInfo2);

		JLabel lContInfo2PbDate1 = new JLabel("출판일");
		lContInfo2PbDate1.setBounds(0, 0, 70, 35);
		lContInfo2PbDate1.setFont(customFont.rp15);
		pContInfo2.add(lContInfo2PbDate1);

		JLabel lContInfo2PbDate2 = new JLabel("|     ");
		lContInfo2PbDate2.setBounds(80, 0, 100, 35);
		lContInfo2PbDate2.setFont(customFont.rp15);
		pContInfo2.add(lContInfo2PbDate2);
		 
		LocalDateTime publishDate = book.getBook_Publish_Date();
		String PbDate = publishDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		JLabel lContInfo2PbDateData = new JLabel(PbDate);
		lContInfo2PbDateData.setBounds(90, 0, 220, 35);
		lContInfo2PbDateData.setFont(customFont.rp15);
//		lContInfo2PbDateData.setBorder(new LineBorder(Color.red));
		pContInfo2.add(lContInfo2PbDateData);

		JLabel lContInfo1InsertDate1 = new JLabel("등록일");
		lContInfo1InsertDate1.setBounds(325, 0, 100, 35);
		lContInfo1InsertDate1.setFont(customFont.rp15);
		pContInfo2.add(lContInfo1InsertDate1);

		JLabel lContInfo1InsertDate2 = new JLabel("|     ");
		lContInfo1InsertDate2.setBounds(405, 0, 100, 35);
		lContInfo1InsertDate2.setFont(customFont.rp15);
		pContInfo2.add(lContInfo1InsertDate2);
		
		LocalDateTime insertDate = book.getBook_Insert_Date();
		String inDate = insertDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		JLabel lContInfo1InsertDate2Data = new JLabel(inDate);
		lContInfo1InsertDate2Data.setBounds(415, 0, 220, 35);
		lContInfo1InsertDate2Data.setFont(customFont.rp15);
//		lContInfo1InsertDate2Data.setBorder(new LineBorder(Color.red));
		pContInfo2.add(lContInfo1InsertDate2Data);


		JLabel pContInfo2Code1 = new JLabel("도서코드");
		pContInfo2Code1.setBounds(0, 35, 100, 35);
		pContInfo2Code1.setFont(customFont.rp15);
		pContInfo2.add(pContInfo2Code1);

		JLabel pContInfo2Code2 = new JLabel("|     ");
		pContInfo2Code2.setBounds(80, 35, 100, 35);
		pContInfo2Code2.setFont(customFont.rp15);
		pContInfo2.add(pContInfo2Code2);
		
		JLabel pContInfo2CodeData = new JLabel("ISBN" + book.getBook_Code());
		pContInfo2CodeData.setBounds(90, 35, 220, 35);
		pContInfo2CodeData.setFont(customFont.rp15);
//		pContInfo2CodeData.setBorder(new LineBorder(Color.red));
		pContInfo2.add(pContInfo2CodeData);

		JLabel pContInfo2Type1 = new JLabel("도서장르");
		pContInfo2Type1.setBounds(325, 35, 100, 35);
		pContInfo2Type1.setFont(customFont.rp15);
		pContInfo2.add(pContInfo2Type1);

		JLabel pContInfo2Type2 = new JLabel("|     ");
		pContInfo2Type2.setBounds(405, 35, 100, 35);
		pContInfo2Type2.setFont(customFont.rp15);
		pContInfo2.add(pContInfo2Type2);
		
		JLabel pContInfoTypeData = new JLabel(book.getBook_Type());
		pContInfoTypeData.setBounds(415, 35, 220, 35);
		pContInfoTypeData.setFont(customFont.rp15);
//		pContInfoTypeData.setBorder(new LineBorder(Color.red));
		pContInfo2.add(pContInfoTypeData);

		JLabel pContInfo2ClaimingSymbol1 = new JLabel("청구기호");
		pContInfo2ClaimingSymbol1.setBounds(0, 70, 100, 35);
		pContInfo2ClaimingSymbol1.setFont(customFont.rp15);
		pContInfo2.add(pContInfo2ClaimingSymbol1);

		JLabel pContInfo2ClaimingSymbol2 = new JLabel("|     ");
		pContInfo2ClaimingSymbol2.setBounds(80, 70, 100, 35);
		pContInfo2ClaimingSymbol2.setFont(customFont.rp15);
		pContInfo2.add(pContInfo2ClaimingSymbol2);
		
		JLabel pContInfo2ClaimingSymbolData = new JLabel(book.getBook_ClaimingSymbol());
		pContInfo2ClaimingSymbolData.setBounds(90, 70, 220, 35);
		pContInfo2ClaimingSymbolData.setFont(customFont.rp15);
//		pContInfo2ClaimingSymbolData.setBorder(new LineBorder(Color.red));
		pContInfo2.add(pContInfo2ClaimingSymbolData);

		
		
		// 책 소개 버튼
		
		try {
			bookDetail = bookDetailController.select(sBookCode);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		JPanel pContBtnList = new JPanel();
		pContBtnList.setLayout(null);
		pContBtnList.setPreferredSize(new Dimension(0, 50));
		pContBtnList.setBackground(Color.white);
		pContListBox.add(pContBtnList);

		JPanel pContinBtnList = new JPanel(new GridLayout(0, 3));
		pContinBtnList.setBounds(0, 0, 970, 50);
		pContinBtnList.setBackground(Color.white);
		pContBtnList.add(pContinBtnList);

		btnBookInfo = new JButton(BookInfo[0]);
//		btnBookInfo.setBorderPainted(false);
		btnBookInfo.setFocusable(false);
		btnBookInfo.setBackground(Color.orange);
		btnBookInfo.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnBookInfo.setBorder(new LineBorder(Color.black));
		btnBookInfo.setFont(customFont.r17);
		btnBookInfo.addActionListener(this);
		pContinBtnList.add(btnBookInfo);

		btnAuthorInfo = new JButton(BookInfo[1]);
//		btnAuthorInfo.setBorderPainted(false);
		btnAuthorInfo.setFocusable(false);
		btnAuthorInfo.setBackground(Color.white);
		btnAuthorInfo.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnAuthorInfo.setBorder(new LineBorder(Color.LIGHT_GRAY));
		btnAuthorInfo.setFont(customFont.r17);
		btnAuthorInfo.addActionListener(this);
		pContinBtnList.add(btnAuthorInfo);

		btnReviewInfo = new JButton(BookInfo[2]);
//		btnReviewInfo.setBorderPainted(false);
		btnReviewInfo.setFocusable(false);
		btnReviewInfo.setBackground(Color.white);
		btnReviewInfo.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnReviewInfo.setBorder(new LineBorder(Color.LIGHT_GRAY));
		btnReviewInfo.setFont(customFont.r17);
		btnReviewInfo.addActionListener(this);
		pContinBtnList.add(btnReviewInfo);

		// 책 소개
		
		
		
		pContBookInfo = new JPanel();
		pContBookInfo.setBackground(Color.white);
		pContBookInfo.setPreferredSize(new Dimension(0, 240));
		pContBookInfo.setLayout(null);
		pContListBox.add(pContBookInfo);

		lContBookInfo = new JLabel("책 소개");
		lContBookInfo.setBounds(0, 30, 915, 30);
		lContBookInfo.setFont(customFont.r17);
		pContBookInfo.add(lContBookInfo);

		taContBookInfo = new JTextArea();
		taContBookInfo.setLineWrap(true);
		taContBookInfo.setWrapStyleWord(true);
		taContBookInfo.setFont(customFont.rp15);
		taContBookInfo.setBounds(0, 85, 970, 240);
		taContBookInfo.setEditable(false);
		taContBookInfo.setText(bookDetail.getBook_Introduce());
		setBookInfo();
		
		pContBookInfo.add(taContBookInfo);

		JLabel lContBookInfoLine = new JLabel("");
		lContBookInfoLine.setBounds(0, 63, 55, 5);
		lContBookInfoLine.setBorder(new CustomLineBorder(Color.orange, 4, CustomLineBorder.TOP));
		pContBookInfo.add(lContBookInfoLine);

		JScrollPane scroll = new JScrollPane(pContListBox);
		scroll.setPreferredSize(new Dimension(1045, 690));
		scroll.setBorder(null);
		scroll.getVerticalScrollBar().setUnitIncrement(16);
		
		add(scroll);

	}
	
	
	
	// text 줄바뀜 개수 확인
	public int countLines(JTextArea textArea) {
	    AttributedString text = new AttributedString(textArea.getText());
	    text.addAttribute(TextAttribute.FONT, customFont.rp15);
	    FontRenderContext frc = textArea.getFontMetrics(textArea.getFont()).getFontRenderContext();
	    AttributedCharacterIterator charIt = text.getIterator();
	    LineBreakMeasurer lineMeasurer = new LineBreakMeasurer(charIt, frc);
	    float formatWidth = (float) textArea.getSize().width;
	    lineMeasurer.setPosition(charIt.getBeginIndex());

	    int noLines = 0;
	    while (lineMeasurer.getPosition() < charIt.getEndIndex()) {
	      lineMeasurer.nextLayout(formatWidth);
	      noLines++;
	    }

	    return noLines;
	  }
	
	@Override
	public void actionPerformed(ActionEvent e) {

		JButton btnTemp = (JButton) e.getSource();

		if (btnTemp == btnBookInfo) {

			btnBookInfo.setBackground(Color.orange);
			btnAuthorInfo.setBackground(Color.white);
			btnReviewInfo.setBackground(Color.white);
			btnBookInfo.setBorder(new LineBorder(Color.black));
			btnAuthorInfo.setBorder(new LineBorder(Color.LIGHT_GRAY));
			btnReviewInfo.setBorder(new LineBorder(Color.LIGHT_GRAY));
			taContBookInfo.setText(bookDetail.getBook_Introduce());
			lContBookInfo.setText("책 소개");

			setBookInfo();
			
		} else if (btnTemp == btnAuthorInfo) {

			btnBookInfo.setBackground(Color.white);
			btnAuthorInfo.setBackground(Color.orange);
			btnReviewInfo.setBackground(Color.white);
			btnBookInfo.setBorder(new LineBorder(Color.LIGHT_GRAY));
			btnAuthorInfo.setBorder(new LineBorder(Color.black));
			btnReviewInfo.setBorder(new LineBorder(Color.LIGHT_GRAY));
			taContBookInfo.setText(bookDetail.getBook_Authorintroduce());
			lContBookInfo.setText("저자 소개");
			
			setBookInfo();

		} else if (btnTemp == btnReviewInfo) {

			btnBookInfo.setBackground(Color.white);
			btnAuthorInfo.setBackground(Color.white);
			btnReviewInfo.setBackground(Color.orange);
			btnBookInfo.setBorder(new LineBorder(Color.LIGHT_GRAY));
			btnAuthorInfo.setBorder(new LineBorder(Color.LIGHT_GRAY));
			btnReviewInfo.setBorder(new LineBorder(Color.black));
			taContBookInfo.setText(bookDetail.getBook_Review());
			lContBookInfo.setText("서평");

			setBookInfo();
		}

	}
	
	public void setBookInfo() {
		int LineCount = countLines(taContBookInfo);
		System.out.println(LineCount);
		if(LineCount > setMinLine) {
			taContBookInfo.setBounds(0, 85, 970, 20*LineCount);
			pContBookInfo.setPreferredSize(new Dimension(0, 120 + (20*LineCount)));
		}else {
			taContBookInfo.setBounds(0, 85, 970, 240);
			pContBookInfo.setPreferredSize(new Dimension(0, 240));
		}
	}

	public long countChar(String str, char ch) {
		return str.chars().filter(c -> c == ch).count();
	}



	@Override
	public void mouseClicked(MouseEvent e) {
		
		JLabel lTemp = (JLabel) e.getSource();
		if (lTemp == lBackWindow) {
			pContent.setVisible(true);
			pDetailInfo.setVisible(false);
			scroll2.setVisible(true);
		}
	}



	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
