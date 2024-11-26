package page;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import book.Book;
import controller.BookController;
import controller.LendingController;
import custom.CustomColor;
import custom.CustomFont;
import custom.CustomLineBorder;
import lending.Lending;
import user.UserLogged;


public class DetailSearchPage extends JPanel implements ActionListener, MouseListener, KeyListener {

	private BookController bookController = new BookController();
	private LendingController lendingController = new LendingController();
	private JPanel pContListBox;
	private JLabel lContSearchBookCount;
	private ArrayList<JButton> btnFindFirstImgIndexList = new ArrayList<JButton>();
	private ArrayList<JButton> btnFindFirstTitleIndexList = new ArrayList<JButton>();
	private ArrayList<JButton> btnFindIndexList = new ArrayList<JButton>();
	private ArrayList<String> sFindBookCodeList = new ArrayList<String>();
	private ArrayList<String> sFindFirstBookCodeList = new ArrayList<String>();
	private JLabel[] lContBookListState;
	private ArrayList<JLabel> lContBookListStateIdx = new ArrayList<JLabel>();
	private JLabel[] lContBookListReturnDate;
	private ArrayList<JLabel> lContBookListReturnDateIdx = new ArrayList<JLabel>();

	
	private String pageTitle;
	private JPanel pContent;
	private CustomFont customFont = new CustomFont();
	private CustomColor customColor = new CustomColor();
	private JTextField tfContSearchInputTitle;
	private JTextField tfContSearchInputAuthor;
	private JTextField tfContSearchInputPb;
	private JTextField tfContSearchInputPbDateStart;
	private JTextField tfContSearchInputPbDateEnd;
	private JButton btnContReset;
	
	private String[] searchHeader;
	private String[] searchText;
	
	private JButton btnContSearch;
	private JPanel pDetailInfo;
	private JScrollPane scroll;
	
	public DetailSearchPage(String[] searchHeader, String[] searchText, JPanel pContent, String pageTitle) {
		this.pContent = pContent;
		this.searchHeader = searchHeader;
		this.searchText = searchText;
		this.pageTitle = pageTitle;
		
		setPreferredSize(new Dimension(1045, 700));
		setLayout(null);
		setBackground(Color.white);

		
		pContListBox = new JPanel();
		pContListBox.setLayout(new BoxLayout(pContListBox, BoxLayout.Y_AXIS));
		
		pDetailInfo = new JPanel();
		pDetailInfo.setBounds(0, 0, 1045, 700);
		add(pDetailInfo);
		pDetailInfo.setVisible(false);
		pDetailInfo.setBackground(Color.white);
		

		contTitleAndSearchBar(pageTitle);

		if ((searchHeader != null) && (searchText != null)) {

			ArrayList<Book> bookList = null;
			ArrayList<Lending> lendingList = null;
			String[] sContBookListHeader = { "책코드", "청구기호", "등록일", "도서상태", "대출", "반납예정" };
			
			try {
				bookList = bookController.DeatilSearch(searchHeader, searchText);

				lContSearchBookCount.setText("총 " + bookList.size() + "개의 책이 검색되었습니다.");

				lendingList = lendingController.select();

			} catch (SQLException e) {
				e.printStackTrace();
			}

			int iBookListLen = bookList.size();
			String sBookCodeStart = "ISBN ";
			if (iBookListLen > 0) {

				int iBookMatchTitleCount[] = null;
				int iBookMatchCount = 0;

				try {
					iBookMatchTitleCount = bookController.detailSearchMatchCount(searchHeader, searchText);
				} catch (SQLException e) {
					e.printStackTrace();
				}

				for (int i = 0; i < iBookListLen; i++) {

						
					String[][] sBookInfoList = new String[iBookMatchTitleCount[iBookMatchCount]][6];
//					System.out.println("너뭔데" +sBookInfoList.length);
					iBookMatchCount++;

					String sFirstImg = bookList.get(i).getBook_Img();
					String sFirstTitle = bookList.get(i).getBook_Title();
					String sFirstPb = bookList.get(i).getBook_Publisher();
					String sFirstAuthor = bookList.get(i).getBook_Author();
					String sFirstType = bookList.get(i).getBook_Type();
					LocalDateTime publishDate = bookList.get(i).getBook_Publish_Date();
					String sFirstPbDate = publishDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
					sFindFirstBookCodeList.add(bookList.get(i).getBook_Code());
					
					int iBookDupCount = 0;
					while (i < iBookListLen) {

						Book book = bookList.get(i);

						sBookInfoList[iBookDupCount][0] = sBookCodeStart+ book.getBook_Code();
						sFindBookCodeList.add(book.getBook_Code());

						sBookInfoList[iBookDupCount][1] = book.getBook_ClaimingSymbol();
						
						LocalDateTime insertDate = book.getBook_Insert_Date();
						String insertDateFormat = insertDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
						sBookInfoList[iBookDupCount][2] = insertDateFormat;
						sBookInfoList[iBookDupCount][4] = "대출";

						if (lendingList.size() > 0) {
							for (Lending lendingInfo : lendingList) {
								if (lendingInfo.getBook_code().equals(book.getBook_Code())) {

									LocalDateTime returnExpectedDate = lendingInfo.getBook_return_expected_date();
									String returnExpectedDateFormat = returnExpectedDate
											.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

									sBookInfoList[iBookDupCount][3] = "대출불가";
									sBookInfoList[iBookDupCount][5] = returnExpectedDateFormat;

									break;
								} else {
									sBookInfoList[iBookDupCount][3] = "대출가능";
									sBookInfoList[iBookDupCount][5] = "";
								}
							}
						} else {
							sBookInfoList[iBookDupCount][3] = "대출가능";
							sBookInfoList[iBookDupCount][5] = "";
						}

						if (i < iBookListLen - 1) {
							Book nextBook = bookList.get(i + 1);

							if (book.getBook_Title().equals(nextBook.getBook_Title())
									&& book.getBook_Author().equals(nextBook.getBook_Author())) {
								iBookDupCount++;
								i++;
							} else {
								break;
							}
						} else {
							break;
						}
					}

					JPanel pContBookList;
					pContBookList = new JPanel();
					pContBookList.setLayout(new BorderLayout());
					pContBookList.setPreferredSize(new Dimension(0, 300 + (30 * iBookDupCount)));
					pContBookList.setBackground(Color.white);

					
					JButton btnContBookFirstImg = null;
					ImageIcon imgIcon = new ImageIcon(sFirstImg);
					
					// 이미지 파일 있는지 체크
					File file = new File(imgIcon.toString());
					if(file.exists()) {
						Image img = imgIcon.getImage();
						Image changeImg = img.getScaledInstance(110, 150, Image.SCALE_SMOOTH);
						btnContBookFirstImg = new JButton(new ImageIcon(changeImg));
					}else {
						imgIcon = new ImageIcon("./img/no_image.jpg");
						Image img = imgIcon.getImage();
						Image changeImg = img.getScaledInstance(110, 150, Image.SCALE_SMOOTH);
						btnContBookFirstImg = new JButton(new ImageIcon(changeImg));
					}
					
					btnContBookFirstImg.setBounds(95, 40, 110, 150);
					btnContBookFirstImg.setCursor(new Cursor(Cursor.HAND_CURSOR));
					btnContBookFirstImg.addMouseListener(this);
					pContBookList.add(btnContBookFirstImg);
					btnFindFirstImgIndexList.add(btnContBookFirstImg);

					JPanel pContBookFirstTitle = new JPanel(new FlowLayout(FlowLayout.LEFT));
					pContBookFirstTitle.setBounds(200, 40, 675, 40);
					pContBookFirstTitle.setBackground(Color.white);
					pContBookList.add(pContBookFirstTitle);

					JButton btnContBookFirstTitle = new JButton(sFirstTitle);
					btnContBookFirstTitle.setFont(customFont.r17);
					btnContBookFirstTitle.setBorderPainted(false);
					btnContBookFirstTitle.setFocusable(false);
					btnContBookFirstTitle.setContentAreaFilled(false);
					btnContBookFirstTitle.setBackground(Color.white);
					btnContBookFirstTitle.setCursor(new Cursor(Cursor.HAND_CURSOR));
					btnContBookFirstTitle.addMouseListener(this);
					pContBookFirstTitle.add(btnContBookFirstTitle);
					btnFindFirstTitleIndexList.add(btnContBookFirstTitle);

					JLabel lContBookFirstAuthor = new JLabel(sFirstAuthor);
					lContBookFirstAuthor.setBounds(225, 50, 110, 100);
					lContBookFirstAuthor.setFont(customFont.rp13);
					pContBookList.add(lContBookFirstAuthor);

					JLabel lContBookFirstPb = new JLabel(sFirstPb);
					lContBookFirstPb.setBounds(225, 80, 110, 100);
					lContBookFirstPb.setFont(customFont.rp13);
					pContBookList.add(lContBookFirstPb);

					JLabel lContBookFirstType = new JLabel(sFirstType);
					lContBookFirstType.setBounds(225, 100, 110, 100);
					lContBookFirstType.setFont(customFont.rp13);
					pContBookList.add(lContBookFirstType);

					JLabel lContBookFirstPbDate = new JLabel(sFirstPbDate);
					lContBookFirstPbDate.setBounds(225, 120, 110, 100);
					lContBookFirstPbDate.setFont(customFont.rp13);
					pContBookList.add(lContBookFirstPbDate);

					JLabel lContBookListTopLine = new JLabel();
					lContBookListTopLine.setBounds(95, 210, 780, 1);
					lContBookListTopLine.setBorder(new CustomLineBorder(Color.cyan, 1, CustomLineBorder.BOTTOM));
					pContBookList.add(lContBookListTopLine);

					JPanel pContBookListHeader = new JPanel();
					pContBookListHeader.setBounds(95, 210, 780, 30);
					pContBookListHeader.setBorder(new CustomLineBorder(Color.gray, 1, CustomLineBorder.BOTTOM));
					pContBookListHeader.setBackground(Color.lightGray);
					pContBookListHeader.setLayout(null);

					// 나중에 스트링 배열ㄹ로 정리~~
					JLabel lHeader1 = new JLabel(sContBookListHeader[0]);
					lHeader1.setBounds(78, 10, 100, 12);
					pContBookListHeader.add(lHeader1);
					
					JLabel lHeader2 = new JLabel(sContBookListHeader[1]);
					lHeader2.setBounds(205, 10, 100, 12);
					pContBookListHeader.add(lHeader2);
					
					JLabel lHeader3 = new JLabel(sContBookListHeader[2]);
					lHeader3.setBounds(330, 10, 100, 12);
					pContBookListHeader.add(lHeader3);
					
					JLabel lHeader4 = new JLabel(sContBookListHeader[3]);
					lHeader4.setBounds(430, 10, 100, 12);
					pContBookListHeader.add(lHeader4);
					
					JLabel lHeader5 = new JLabel(sContBookListHeader[4]);	
					lHeader5.setBounds(550, 10, 100, 12);
					pContBookListHeader.add(lHeader5);
					
					JLabel lHeader6 = new JLabel(sContBookListHeader[5]);
					lHeader6.setBounds(651, 10, 100, 12);
					pContBookListHeader.add(lHeader6);

					pContBookList.add(pContBookListHeader);

					JLabel[] lContBookListCode = new JLabel[iBookDupCount + 1];
					JLabel[] lContBookListClamingSymbol = new JLabel[iBookDupCount + 1];
					JLabel[] lContBookListInsertDate = new JLabel[iBookDupCount + 1];
					lContBookListState = new JLabel[iBookDupCount + 1];
					JButton[] btnContBookListAp = new JButton[iBookDupCount + 1];
					lContBookListReturnDate = new JLabel[iBookDupCount + 1];

					for (int j = 0; j < iBookDupCount + 1; j++) {
						lContBookListCode[j] = new JLabel(sBookInfoList[j][0]);
						lContBookListCode[j].setBounds(142, 240 + 30 * j, 100, 30);
						lContBookListCode[j].setHorizontalAlignment(JLabel.CENTER);
						pContBookList.add(lContBookListCode[j]);
	
						lContBookListClamingSymbol[j] = new JLabel(sBookInfoList[j][1]);
						lContBookListClamingSymbol[j].setBounds(265, 240 + 30 * j, 120, 30);
						lContBookListClamingSymbol[j].setHorizontalAlignment(JLabel.CENTER);
						pContBookList.add(lContBookListClamingSymbol[j]);
	
						lContBookListInsertDate[j] = new JLabel(sBookInfoList[j][2]);
						lContBookListInsertDate[j].setBounds(395, 240 + 30 * j, 100, 30);
						lContBookListInsertDate[j].setHorizontalAlignment(JLabel.CENTER);
						pContBookList.add(lContBookListInsertDate[j]);
	
						lContBookListState[j] = new JLabel(sBookInfoList[j][3]);
						lContBookListState[j].setBounds(502, 240 + 30 * j, 100, 30);
						lContBookListState[j].setHorizontalAlignment(JLabel.CENTER);
						lContBookListStateIdx.add(lContBookListState[j]);
						pContBookList.add(lContBookListState[j]);
	

						btnContBookListAp[j] = new JButton(sBookInfoList[j][4]);
						btnContBookListAp[j].setBounds(628, 244 + 30 * j, 60, 23);
						btnContBookListAp[j].setHorizontalAlignment(JLabel.CENTER);
						btnContBookListAp[j].setCursor(new Cursor(Cursor.HAND_CURSOR));
						btnContBookListAp[j].setFocusPainted(false);
						btnFindIndexList.add(btnContBookListAp[j]);
						
						if(lContBookListState[j].getText().equals("대출불가")) {
							lContBookListState[j].setForeground(Color.red);
							btnContBookListAp[j].setEnabled(false);
						}else {
							btnContBookListAp[j].addActionListener(this);
							btnContBookListAp[j].addMouseListener(this);
						}
						pContBookList.add(btnContBookListAp[j]);
	
						lContBookListReturnDate[j] = new JLabel(sBookInfoList[j][5]);
						lContBookListReturnDate[j].setBounds(722, 240 + 30 * j, 100, 30);
						lContBookListReturnDate[j].setHorizontalAlignment(JLabel.CENTER);
						lContBookListReturnDateIdx.add(lContBookListReturnDate[j]);
						pContBookList.add(lContBookListReturnDate[j]);

						JLabel lBottomLine = new JLabel();
						lBottomLine.setBounds(95, 275 + 30 * iBookDupCount, 780, 1);
						lBottomLine.setBorder(new CustomLineBorder(Color.cyan, 1, CustomLineBorder.BOTTOM));
						pContBookList.add(lBottomLine);

					}
					
					JLabel lContBookFirstBottomLine = new JLabel();
					lContBookFirstBottomLine.setBounds(95, 220 + (30 * iBookDupCount), 780, 70);
					lContBookFirstBottomLine.setBorder(new CustomLineBorder(Color.gray, 2, CustomLineBorder.BOTTOM));
					pContBookList.add(lContBookFirstBottomLine);

					
					
					JLabel l = new JLabel("");
					pContBookList.add(l);

					pContListBox.add(pContBookList);
				}
			} else {
				nonSearch();
			}

		} else {
			nonSearch();
		}

		scroll = new JScrollPane(pContListBox);
		scroll.setBorder(null);
		scroll.setSize(1045, 700);
		scroll.getVerticalScrollBar().setUnitIncrement(16);
		add(scroll);

	}

	private void contTitleAndSearchBar(String pageTitle) {

		// 검색 창 타이틀 -> 함수로 변경
		JPanel pContTitle = new JPanel();
		pContTitle.setPreferredSize(new Dimension(0, 79));
		pContTitle.setLayout(null);
		pContTitle.setBackground(Color.white);
		pContListBox.add(pContTitle);



		JPanel pContSearchBar = new JPanel();
		pContSearchBar.setPreferredSize(new Dimension(0, 310));
		pContSearchBar.setLayout(null);
		pContSearchBar.setBackground(Color.white);

		
		lContSearchBookCount = new JLabel();
		lContSearchBookCount.setBounds(95, 280, 400, 30);
		lContSearchBookCount.setFont(customFont.rp13);

		pContSearchBar.add(lContSearchBookCount);

		// 검색 - 제목
		JLabel lContSearchTitle = new JLabel(" 제목");
		lContSearchTitle.setBounds(160, 15, 90, 40);
		lContSearchTitle.setFont(customFont.r17);
		lContSearchTitle.setBorder(new LineBorder(Color.lightGray));
		pContSearchBar.add(lContSearchTitle);

		tfContSearchInputTitle = new JTextField(50);
		tfContSearchInputTitle.setBounds(285, 15, 500, 40);
		tfContSearchInputTitle.addKeyListener(this);
		pContSearchBar.add(tfContSearchInputTitle);
		
		System.out.println(pageTitle);
		JLabel lContTitle = new JLabel(pageTitle);
		lContTitle.setBounds(0, 0, 970, 75);
		lContTitle.setFont(customFont.r31);
		lContTitle.setBorder(new CustomLineBorder(Color.black, 2, CustomLineBorder.BOTTOM));
		pContTitle.add(lContTitle);
		
		
		// 검색 - 저자
		JLabel lContSearchAuthor = new JLabel(" 저자");
		lContSearchAuthor.setBounds(160, 65, 90, 40);
		lContSearchAuthor.setFont(customFont.r17);
		lContSearchAuthor.setBorder(new LineBorder(Color.lightGray));
		pContSearchBar.add(lContSearchAuthor);

		tfContSearchInputAuthor = new JTextField(50);
		tfContSearchInputAuthor.setBounds(285, 65, 500, 40);
		tfContSearchInputAuthor.addKeyListener(this);

		pContSearchBar.add(tfContSearchInputAuthor);
		
		
		// 검색 - 출판사
		JLabel lContSearchPb = new JLabel(" 출판사");
		lContSearchPb.setBounds(160, 115, 90, 40);
		lContSearchPb.setFont(customFont.r17);
		lContSearchPb.setBorder(new LineBorder(Color.lightGray));
		pContSearchBar.add(lContSearchPb);

		tfContSearchInputPb = new JTextField(50);
		tfContSearchInputPb.setBounds(285, 115, 500, 40);
		tfContSearchInputPb.addKeyListener(this);
		
		pContSearchBar.add(tfContSearchInputPb);
				
		
		// 검색 - 발행년도
		JLabel lContSearchPbDate = new JLabel(" 발행년도");
		lContSearchPbDate.setBounds(160, 165, 90, 40);
		lContSearchPbDate.setFont(customFont.r17);
		lContSearchPbDate.setBorder(new LineBorder(Color.lightGray));
		pContSearchBar.add(lContSearchPbDate);

		//tfContSearchInputPbDateStart = new HintTextField(tfHintPbDateStart);
		tfContSearchInputPbDateStart = new JTextField();
		tfContSearchInputPbDateStart.setBounds(285, 165, 80, 40);
		tfContSearchInputPbDateStart.addKeyListener(this);
//		tfContSearchInputPbDateStart.setDocument(new JTextFieldLimit(4));

		JLabel lContSearchPbDatetilde = new JLabel("~");
		lContSearchPbDatetilde.setBounds(373, 165, 30, 35);
		lContSearchPbDatetilde.setFont(customFont.r30);
		pContSearchBar.add(lContSearchPbDatetilde);
		
		//tfContSearchInputPbDateEnd = new HintTextField(tfHintPbDateEnd);
		tfContSearchInputPbDateEnd = new JTextField();
		tfContSearchInputPbDateEnd.setBounds(400, 165, 80, 40);
		tfContSearchInputPbDateEnd.addKeyListener(this);
//		tfContSearchInputPbDateEnd.setDocument(new JTextFieldLimit(4));
		
		
		
		pContSearchBar.add(tfContSearchInputPbDateStart);
		pContSearchBar.add(tfContSearchInputPbDateEnd);


		btnContSearch = new JButton("검색");
		btnContSearch.setBounds(400, 230, 80, 30);
		btnContSearch.addActionListener(this);
		btnContSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnContSearch.setBackground(customColor.lightBlue);
		btnContSearch.setForeground(Color.white);
		btnContSearch.setFont(customFont.r14);
		btnContSearch.setFocusPainted(false);
		pContSearchBar.add(btnContSearch);
		
		btnContReset = new JButton("초기화");
		btnContReset.setBounds(500, 230, 80, 30);
		btnContReset.addActionListener(this);
		btnContReset.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnContReset.setBackground(customColor.lightBlue);
		btnContReset.setForeground(Color.white);
		btnContReset.setFont(customFont.r14);
		btnContReset.setFocusPainted(false);
		pContSearchBar.add(btnContReset);
		
		
		if(searchHeader != null) {
			for(int i = 0; i < searchHeader.length; i++) {
				switch (searchHeader[i]) {
				
				case "book_title":
					tfContSearchInputTitle.setText(searchText[i]);
					break;
					
				case "book_author":
					tfContSearchInputAuthor.setText(searchText[i]);
					break;
					
				case "book_publisher":
					tfContSearchInputPb.setText(searchText[i]);
					break;
					
				case "book_pbDate":
					tfContSearchInputPbDateStart.setText(searchText[i].substring(0,4));
					tfContSearchInputPbDateEnd.setText(searchText[i].substring(4,8));
					break;
					
					
				default:
					break;
				}
			}
		}

		JLabel lContSearchBottomLine = new JLabel();
		lContSearchBottomLine.setBorder(new CustomLineBorder(Color.black, 2, CustomLineBorder.BOTTOM));
		lContSearchBottomLine.setBounds(95, 50, 780, 260);
		pContSearchBar.add(lContSearchBottomLine);
		

		
		pContListBox.add(pContSearchBar);

	}

	public void nonSearch() {
		JPanel pBookList = new JPanel();
		pBookList.setLayout(null);
		pBookList.setPreferredSize(new Dimension(0, 550));

		pBookList.setBackground(Color.white);

		JLabel lNotSearch = new JLabel(new ImageIcon("./img/no_data.jpg"));
		lNotSearch.setBounds(280, 70, 400, 400);
		lNotSearch.setFont(customFont.r30);
		pBookList.add(lNotSearch);

		pContListBox.add(pBookList);
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		JButton btnTemp = (JButton) e.getSource();
		
		for(int i = 0; i < btnFindFirstImgIndexList.size(); i++) {
			JButton btnFindImgIndex = btnFindFirstImgIndexList.get(i);
			JButton btnFindTitleIndex = btnFindFirstTitleIndexList.get(i);
			
			if(btnTemp == btnFindImgIndex || btnTemp == btnFindTitleIndex) {
				String sFindBookCode = sFindFirstBookCodeList.get(i);
			
				pContListBox.setVisible(false);
				scroll.setVisible(false);
				pDetailInfo.removeAll();
				pDetailInfo.add(new DetailBookInfoPage(pContListBox,scroll,pDetailInfo,sFindBookCode));
				pDetailInfo.revalidate();
				pDetailInfo.repaint();
				
				break;
			}
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
		JButton b = (JButton) e.getSource();
		b.setForeground(Color.blue);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		JButton b = (JButton) e.getSource();
		b.setForeground(Color.black);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btnTemp = (JButton) e.getSource();

		if (btnTemp == btnContSearch) {
			
			int iInputTitleLen = tfContSearchInputTitle.getText().length();
			int iInputAuthorLen = tfContSearchInputAuthor.getText().length();
			int iInputPbLen = tfContSearchInputPb.getText().length();
			int iInputPbDateStartLen = tfContSearchInputPbDateStart.getText().length();
			int iInputDateEndLen = tfContSearchInputPbDateEnd.getText().length();
			
			if((iInputPbDateStartLen == 4 && iInputDateEndLen == 4) || (iInputPbDateStartLen == 0 && iInputDateEndLen == 0)) {
				
				try{
					int pbDateStart = 0;
					int pbDateEnd = 0;
					if(iInputPbDateStartLen == 4 && iInputDateEndLen == 4) {
						pbDateStart = Integer.parseInt(tfContSearchInputPbDateStart.getText());
						pbDateEnd = Integer.parseInt(tfContSearchInputPbDateEnd.getText());
					}
				
					if(pbDateStart <= pbDateEnd) {
					
						int[] inputLen = {iInputTitleLen, iInputAuthorLen, iInputPbLen, iInputPbDateStartLen, iInputDateEndLen};
						int iCount = contentCount(inputLen);
						
						String[] searchHeader = null;
						String[] searchText = null;
						if(iCount > 0) {
							searchHeader = new String[iCount];
							searchText = new String[iCount];
						}
						
			
						searchSqlFormat(inputLen, iCount, searchHeader, searchText);
						
						pContent.removeAll();
						pContent.add(new DetailSearchPage(searchHeader,searchText, pContent, pageTitle));
						pContent.revalidate();
						pContent.repaint();
					}else {
						JOptionPane.showMessageDialog(null, "발행년도에 잘못된 값이 입력되었습니다.", "상세검색", JOptionPane.INFORMATION_MESSAGE);
					}
				}catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "발행년도에 숫자를 입력해주세요.", "상세검색", JOptionPane.INFORMATION_MESSAGE);
				}
			}else {
				JOptionPane.showMessageDialog(null, "발행년도에 4개의 숫자를 입력해주세요.", "상세검색", JOptionPane.INFORMATION_MESSAGE);
			}
		}else if (btnTemp == btnContReset) {
			
			tfContSearchInputTitle.setText("");
			tfContSearchInputAuthor.setText("");
			tfContSearchInputPb.setText("");
			tfContSearchInputPbDateStart.setText("");
			tfContSearchInputPbDateEnd.setText("");
		}else {
			// 대출 버튼을 클릭했을 때
			if (userLoginState()) {

				int result = JOptionPane.showConfirmDialog(null, "대출 하시겠습니까?", "대출", JOptionPane.YES_NO_OPTION);

				if (result == JOptionPane.YES_OPTION) {
					for (int i = 0; i < btnFindIndexList.size(); i++) {
						JButton btnFindIndex = btnFindIndexList.get(i);

						if (btnTemp == btnFindIndex) {
							String sBookCode = sFindBookCodeList.get(i);

							// 대출 가능 유/무 확인 후 랜드 하기
							if (userLendCheck()) {

								bookListChagne(i, btnTemp, sBookCode);
							}

							break;
						}
					}
				}
			} else {
				JOptionPane.showMessageDialog(null, "로그인을 해주세요.", "대출", JOptionPane.INFORMATION_MESSAGE);
//				pMainPanel.removeAll();
//				pMainPanel.setLayout(new BorderLayout());
//				pMainPanel.add(new LoginPage(pMainPanel));
//				pMainPanel.revalidate();
//				pMainPanel.repaint();
			}
		}

	}
	
	private void bookListChagne(int index, JButton btnTemp, String sBookCode) {
		try {

			String returnExpeDate = lendingController.lendBook(sBookCode);

			if (returnExpeDate != null) {
				lContBookListStateIdx.get(index).setForeground(Color.red);
				lContBookListStateIdx.get(index).setText("대출불가");

				lContBookListReturnDateIdx.get(index).setText(returnExpeDate);

				btnTemp.setEnabled(false);
				btnTemp.removeActionListener(this);
				btnTemp.removeMouseListener(this);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

	}

	private boolean userLoginState() {

		boolean result = false;

		String userId = UserLogged.getUserId();
//		System.out.println("userId= " + userId);
		if (userId != null && !userId.isEmpty()) {
			result = true;
		}

		return result;
	}

	private boolean userLendCheck() {
		boolean result = false;

		String userId = UserLogged.getUserId();

		try {
			if (lendingController.lentCount(userId)) {
				if (lendingController.lentOverDueCount(userId)) {
					result = true;
				} else {
					JOptionPane.showMessageDialog(null, "연체된 책이 있습니다.", "대출", JOptionPane.INFORMATION_MESSAGE);
				}

			} else {
				JOptionPane.showMessageDialog(null, "대출횟수를 초과하였습니다.", "대출", JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}


	private void searchSqlFormat(int[] inputLen, int iCount, String[] searchHeader, String[] searchText) {
		
		if(iCount != 0) {
			
			int count = 0;
			
			if(inputLen[0] > 0) {
				searchHeader[count] = "book_title";
				searchText[count] =  tfContSearchInputTitle.getText();
				
				count ++;
			}
			
			if(inputLen[1] > 0) {
				searchHeader[count] =  "book_author";
				searchText[count] =  tfContSearchInputAuthor.getText();
				
				count ++;
			}
			
			if(inputLen[2] > 0) {
				searchHeader[count] =  "book_publisher";
				searchText[count] =  tfContSearchInputPb.getText();
				
				count ++;
			}
			
			if((inputLen[3] > 0) && (inputLen[4] > 0)){
				searchHeader[count] =  "book_pbDate";
				searchText[count] =  tfContSearchInputPbDateStart.getText()+tfContSearchInputPbDateEnd.getText();
			}
		}
		
	}

	private int contentCount(int[] inputLen) {
		
		int count = 0;
		
		if(inputLen[0] > 0) {
			count ++;
		}
		
		if(inputLen[1] > 0) {
			count ++;
		}
		
		if(inputLen[2] > 0) {
			count ++;
		}
		
		if((inputLen[3] > 0) && (inputLen[4] > 0)){
			count ++;
		}
		
		return count;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int getKey = e.getKeyCode();
		
//		System.out.println(getKey);
		
		// 엔터를 눌렀을 때 현재 입력한 문자까지만
		// 팝업에 출력!
		if(getKey == 10) {
			
			int iInputTitleLen = tfContSearchInputTitle.getText().length();
			int iInputAuthorLen = tfContSearchInputAuthor.getText().length();
			int iInputPbLen = tfContSearchInputPb.getText().length();
			int iInputPbDateStartLen = tfContSearchInputPbDateStart.getText().length();
			int iInputDateEndLen = tfContSearchInputPbDateEnd.getText().length();
			
			int[] inputLen = {iInputTitleLen, iInputAuthorLen, iInputPbLen, iInputPbDateStartLen, iInputDateEndLen};
			int iCount = contentCount(inputLen);
			
			String searchHeader[] = new String[iCount];
			String searchText[] = new String[iCount];

			searchSqlFormat(inputLen, iCount, searchHeader, searchText);

			
			pContent.removeAll();
			pContent.add(new DetailSearchPage(searchHeader,searchText, pContent, pageTitle));
			pContent.revalidate();
			pContent.repaint();
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}