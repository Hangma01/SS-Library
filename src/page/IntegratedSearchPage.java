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
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import book.Book;
import controller.BookController;
import controller.LendingController;
import custom.CustomColor;
import custom.CustomFont;
import custom.CustomHintTextField;
import custom.CustomLineBorder;
import lending.Lending;
import user.UserLogged;

public class IntegratedSearchPage extends JPanel implements ActionListener, MouseListener, KeyListener {

	private BookController bookController = new BookController();
	private LendingController lendingController = new LendingController();
	private CustomColor customColor = new CustomColor();
	private JPanel pContListBox;
	private String[] SearchTypeData = { "제목", "저자", "출판사" };
	private JLabel lContSearchBookCount;
	private ArrayList<JButton> btnFindFirstImgIndexList = new ArrayList<JButton>();
	private ArrayList<JButton> btnFindFirstTitleIndexList = new ArrayList<JButton>();
	private ArrayList<JButton> btnFindIndexList = new ArrayList<JButton>();
	private ArrayList<String> sFindBookCodeList = new ArrayList<String>();
	private ArrayList<String> sFindFirstBookCodeList = new ArrayList<String>();
	private JComboBox<String> cbContSearchType;
	private JLabel[] lContBookListState;
	private ArrayList<JLabel> lContBookListStateIdx = new ArrayList<JLabel>();
	private JLabel[] lContBookListReturnDate;
	private ArrayList<JLabel> lContBookListReturnDateIdx = new ArrayList<JLabel>();

	private String searchText;
	private String pageTitle;
	private JPanel pContent;
	private CustomFont customFont = new CustomFont();
	private JTextField tfContSearchInput;
	private JButton btnContSearch;
	private String tfHint;

	private JPanel pDetailInfo;
	private JScrollPane scroll;

	public IntegratedSearchPage(String searchText, int cbIndex, JPanel pContent, String pageTitle) {
		this.pContent = pContent;
		this.pageTitle = pageTitle;
		this.searchText = searchText;

		setPreferredSize(new Dimension(1045, 700));
		setLayout(null);
		setBackground(Color.white);

		tfHint = "검색어를 입력하세요.";

		pDetailInfo = new JPanel();
		pDetailInfo.setBounds(0, 0, 1045, 700);
		add(pDetailInfo);
		pDetailInfo.setVisible(false);
		pDetailInfo.setBackground(Color.white);

		pContListBox = new JPanel();
		pContListBox.setLayout(new BoxLayout(pContListBox, BoxLayout.Y_AXIS));

		contTitleAndSearchBar(pageTitle, cbIndex);

		String SearchText = tfContSearchInput.getText();
		if (!SearchText.equals(tfHint) && (SearchText.length() > 0)) {

			ArrayList<Book> bookList = null;
			ArrayList<Lending> lendingList = null;
			String[] sContBookListHeader = { "책코드", "청구기호", "등록일", "도서상태", "대출", "반납예정" };
			String searchType = null;
			try {

				if (cbIndex == 0) {
					searchType = "book_title";
				} else if (cbIndex == 1) {
					searchType = "book_author";
				} else if (cbIndex == 2) {
					searchType = "book_publisher";
				}

				bookList = bookController.Search(searchText, searchType);

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
					iBookMatchTitleCount = bookController.searchMatchCount(searchText, searchType);
				} catch (SQLException e) {
					e.printStackTrace();
				}

				for (int i = 0; i < iBookListLen; i++) {
//					System.out.println("iBookMatchTitleCount=" + iBookMatchTitleCount[0]);
	//				System.out.println("iBookMatchCount" + iBookMatchCount);
					String[][] sBookInfoList = new String[iBookMatchTitleCount[iBookMatchCount]][6];

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

						sBookInfoList[iBookDupCount][0] = sBookCodeStart + book.getBook_Code();
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
					if (file.exists()) {
						Image img = imgIcon.getImage();
						Image changeImg = img.getScaledInstance(110, 150, Image.SCALE_SMOOTH);
						btnContBookFirstImg = new JButton(new ImageIcon(changeImg));
					} else {
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
					// btnContBookFirstTitle.addActionListener(this);
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

						if (lContBookListState[j].getText().equals("대출불가")) {
							lContBookListState[j].setForeground(Color.red);
							btnContBookListAp[j].setEnabled(false);
						} else {
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
		scroll.setSize(1045, 698);
		scroll.getVerticalScrollBar().setUnitIncrement(16);
		add(scroll);

	}

	private void contTitleAndSearchBar(String pageTitle, int cbIndex) {

		// 검색 창 타이틀 -> 함수로 변경
		JPanel pContTitle = new JPanel();
		pContTitle.setPreferredSize(new Dimension(0, 79));
		pContTitle.setLayout(null);
		pContTitle.setBackground(Color.white);
		pContListBox.add(pContTitle);

		JLabel lContTitle = new JLabel(pageTitle);
		lContTitle.setBounds(0, 0, 970, 75);
		lContTitle.setFont(customFont.r31);
		lContTitle.setBorder(new CustomLineBorder(Color.black, 2, CustomLineBorder.BOTTOM));
		pContTitle.add(lContTitle);

		JPanel pContSearchBar = new JPanel();
		pContSearchBar.setPreferredSize(new Dimension(0, 140));
		pContSearchBar.setLayout(null);
		pContSearchBar.setBackground(Color.white);

		JLabel lContSearchBottomLine = new JLabel();
		lContSearchBottomLine.setBorder(new CustomLineBorder(Color.black, 2, CustomLineBorder.BOTTOM));
		lContSearchBottomLine.setBounds(95, 70, 780, 70);
		pContSearchBar.add(lContSearchBottomLine);

		lContSearchBookCount = new JLabel();
		lContSearchBookCount.setBounds(95, 105, 400, 30);
		lContSearchBookCount.setFont(customFont.rp13);

		pContSearchBar.add(lContSearchBookCount);

		cbContSearchType = new JComboBox<String>(SearchTypeData);
		cbContSearchType.setBounds(105, 40, 70, 30);
		cbContSearchType.setSelectedIndex(cbIndex);
		cbContSearchType.setBackground(Color.WHITE);
		cbContSearchType.setFont(customFont.rp13);
		cbContSearchType.setCursor(new Cursor(Cursor.HAND_CURSOR));
		pContSearchBar.add(cbContSearchType);

		tfContSearchInput = new CustomHintTextField(tfHint);
		tfContSearchInput.setBorder(null);

		tfContSearchInput.setBounds(185, 44, 420, 20);
		tfContSearchInput.addKeyListener(this);

		if (searchText.length() > 0) {
			tfContSearchInput.setText(searchText);
		}
		pContSearchBar.add(tfContSearchInput);

		btnContSearch = new JButton(new ImageIcon("./img/search.png"));
		btnContSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnContSearch.setContentAreaFilled(false);
		btnContSearch.setBorderPainted(false);
		btnContSearch.setFocusPainted(false);
		btnContSearch.setBounds(810, 40, 60, 30);
		btnContSearch.addActionListener(this);
		btnContSearch.addMouseListener(this);
		pContSearchBar.add(btnContSearch);

		JLabel lContSearchBar = new JLabel();
		lContSearchBar.setBounds(95, 30, 780, 50);
		lContSearchBar.setBorder(new LineBorder(customColor.lightBlue, 3));
		pContSearchBar.add(lContSearchBar);

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

		for (int i = 0; i < btnFindFirstImgIndexList.size(); i++) {
			JButton btnFindImgIndex = btnFindFirstImgIndexList.get(i);
			JButton btnFindTitleIndex = btnFindFirstTitleIndexList.get(i);

			if (btnTemp == btnFindImgIndex || btnTemp == btnFindTitleIndex) {
				String sFindBookCode = sFindFirstBookCodeList.get(i);

				scroll.setVisible(false);
				pContListBox.setVisible(false);
				pDetailInfo.removeAll();
				pDetailInfo.add(new DetailBookInfoPage(pContListBox, scroll, pDetailInfo, sFindBookCode));
				pDetailInfo.revalidate();
				pDetailInfo.repaint();
				break;
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

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

		int cbIndex = cbContSearchType.getSelectedIndex();

		if (btnTemp == btnContSearch) {
			pContent.removeAll();
			pContent.add(new IntegratedSearchPage(tfContSearchInput.getText(), cbIndex, pContent, pageTitle));
			pContent.revalidate();
			pContent.repaint();
		} else {
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

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {

		int getKey = e.getKeyCode();

		// 엔터를 눌렀을 때 현재 입력한 문자까지만
		// 팝업에 출력!
		if (getKey == 10) {

			int cbIndex = cbContSearchType.getSelectedIndex();

			pContent.removeAll();
			pContent.add(new IntegratedSearchPage(tfContSearchInput.getText(), cbIndex, pContent, pageTitle));
			pContent.revalidate();
			pContent.repaint();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}
}