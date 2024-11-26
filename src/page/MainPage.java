package page;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.LineBorder;

import book.Book;
import book.Poem;
import controller.BookController;
import custom.CustomColor;
import custom.CustomFont;
import custom.CustomHintTextField;

public class MainPage extends JPanel implements MouseListener, KeyListener {
	//240606 19:40

	private BookController bc = new BookController();
	private Poem poem = new Poem();
	CustomColor cc =new CustomColor();
	CustomFont cf = new CustomFont();

	private JComboBox<String> cbSearch; // 검색창콤보박스
	private JTextField fSearch; // 검색창필드
	private JButton bSearch; // 검색창버튼

	private String searchFor; // 검색어
	private int cbIndex; // 콤보박스 인덱스

	private ArrayList<JPanel> p10 = new ArrayList<JPanel>();
	private ArrayList<JLabel> best5;
	private ArrayList<JLabel> newest5;

	public MainPage(JPanel pBody) {

		setPreferredSize(pBody.getBounds().getSize()); // 1400*750
		setLayout(null);
		setBackground(Color.WHITE);

		setSearchPanel(); // 검색창
		setPoem(); // 좌우 추천 시 패널
		setRecmd(); // 하단 추천 도서 패널 탭

//		for (int i = 0; i < best5.size(); i++) {
//			System.out.print(best5.get(i).getText() + "/");
//		}
//		System.out.println();
//		for (int i = 0; i < newest5.size(); i++) {
//			System.out.print(newest5.get(i).getText() + "/");
//		}
		
	}// constructor()

	private void setPoem() {
//		---------------------------------------------중간 좌우 패널
		JPanel pLeftCenter = new JPanel(); // 왼쪽 패널
		pLeftCenter.setBounds(50, 50, 350, 100);
		pLeftCenter.setLayout(null);
		pLeftCenter.setBackground(Color.WHITE);

		JLabel lLeft = new JLabel(poem.getPoemL());
		lLeft.setFont(cf.ri14);
		lLeft.setHorizontalAlignment(JLabel.LEFT);
		lLeft.setBounds(0, 0, 350, 100);

		pLeftCenter.add(lLeft);

		JPanel pRightCenter = new JPanel(); // 오른쪽 패널
		pRightCenter.setBounds(1000, 50, 350, 120);
		pRightCenter.setLayout(null);
		pRightCenter.setBackground(Color.WHITE);

		JLabel lRight = new JLabel(poem.getPoemR());
		lRight.setFont(cf.ri14);
		lRight.setHorizontalAlignment(JLabel.LEFT);
		lRight.setBounds(0, 0, 350, 100);

		JLabel lAut = new JLabel(poem.getAuth());
		lAut.setFont(cf.r14);
		lAut.setHorizontalAlignment(JLabel.RIGHT);
		lAut.setBounds(0, 100, 350, 20);

		pRightCenter.add(lRight);
		pRightCenter.add(lAut);

		add(pLeftCenter);
		add(pRightCenter);
	}// setPoem()

	private void setSearchPanel() { // 메인 검색창
		JPanel pSearch = new JPanel();
		pSearch.setBounds(450, 75, 500, 50);
		pSearch.setLayout(null);
		pSearch.setBackground(Color.WHITE);
		pSearch.setBorder(new LineBorder(cc.lightBlue, 3));

		String[] searchTypes = { "제목", "저자", "출판사" };
		cbSearch = new JComboBox<String>(searchTypes);
		cbSearch.setFont(cf.rp13);
		cbSearch.setBounds(10, 10, 70, 30);
		cbSearch.setBackground(Color.WHITE);
		cbSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));

//		fSearch = new JTextField("검색어를 입력하세요."); // 검색창 필드
		fSearch = new CustomHintTextField("검색어를 입력하세요.");
//		fSearch.setFont(cf.r14);
		fSearch.setBounds(90, 10, 350, 30);
		fSearch.setBorder(null);
		fSearch.addMouseListener(this);
		fSearch.addKeyListener(this);

		bSearch = new JButton(new ImageIcon("./img/search.png")); // 검색창 버튼
		bSearch.setBounds(450, 10, 40, 30);
		bSearch.setBorderPainted(false);
		bSearch.setFocusable(false);
		bSearch.setContentAreaFilled(false);
		bSearch.setBackground(Color.WHITE);
		bSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));
		bSearch.addMouseListener(this);

		pSearch.add(cbSearch);
		pSearch.add(fSearch);
		pSearch.add(bSearch);

		add(pSearch);
	}// setSearchPanel 검색창

	private void setRecmd() {
//		------------------------------------------------------
		JTabbedPane tRecmd = new JTabbedPane(); // 하단 추천도서 탭
		tRecmd.setBounds(50, 200, 1300, 500);
		tRecmd.setFont(cf.r16);
		tRecmd.setBackground(Color.WHITE);

		JPanel pBest = new JPanel(); // 인기도서패널 on tRecmd
		pBest.setLayout(null);
		pBest.setBounds(0, 0, 1300, 500);
		pBest.setBackground(Color.WHITE);

		JPanel pNewest = new JPanel(); // 신간도서패널 on tRecmd
		pNewest.setLayout(null);
		pNewest.setBounds(0, 0, 1300, 500);
		pNewest.setBackground(Color.WHITE);

		JLabel lOfBest = new JLabel("많이 대출된 책");
		lOfBest.setBounds(550, 0, 200, 50);
		lOfBest.setHorizontalAlignment(0);
		lOfBest.setFont(cf.r24);

		JLabel lOfNewest = new JLabel("새로 발간된 책");
		lOfNewest.setBounds(550, 0, 200, 50);
		lOfNewest.setHorizontalAlignment(0);
		lOfNewest.setFont(cf.r24);

//		pBest.add(lOfBest);
//		pNewest.add(lOfNewest);

		tRecmd.add("Best", pBest);
		tRecmd.add("Newest", pNewest);
//		----------------------------------
		ArrayList<Book> est5 = new ArrayList<Book>();
		try {
			est5 = bc.recmdBest5();
			best5 = getRecmd5(est5, pBest); // Best5
			est5.clear();
			est5 = bc.recmdNewest5();
			newest5 = getRecmd5(est5, pNewest); // Newst5

		} catch (Exception e) {
//			e.printStackTrace();
		}

		add(tRecmd);
//		---------------------------------------추천패널	
	}// setRecmd 추천탭패널

	private ArrayList<JLabel> getRecmd5(ArrayList<Book> est5, JPanel pOfest) {

		ArrayList<JPanel> panelList = new ArrayList<JPanel>();
		ArrayList<JLabel> rankList = new ArrayList<JLabel>();
		ArrayList<JLabel> titleList = new ArrayList<JLabel>();
		ArrayList<JLabel> imgList = new ArrayList<JLabel>();
		ArrayList<JLabel> authorList = new ArrayList<JLabel>();

		for (int i = 0; i < 5; i++) {

			try {
				panelList.add(new JPanel());
				panelList.get(i).setBounds(50 + i * 245, 50, 220, 380);
				panelList.get(i).setBorder(new LineBorder(cc.lightBlue, 1));
				panelList.get(i).setLayout(null);

				rankList.add(new JLabel("No." + (i + 1)));
				rankList.get(i).setFont(cf.ri20);
				rankList.get(i).setForeground(Color.BLUE);
				rankList.get(i).setBounds(10, 10, 60, 20);

				titleList.add(new JLabel(est5.get(i).getBook_Title()));
				titleList.get(i).setFont(cf.r18);
				titleList.get(i).setBounds(10, 30, 200, 20);

				ImageIcon temp = new ImageIcon(est5.get(i).getBook_Img());
				//이미지파일 없는 도서의 경우
				File file = new File(temp.toString());
				if(!file.exists()) { //대체 이미지 사용
					temp = new ImageIcon("./img/no_image.jpg");
				}
				
				imgList.add(new JLabel(new ImageIcon(temp.getImage()
						.getScaledInstance(210, 310, Image.SCALE_SMOOTH))));
//			Image.SCALE_SMOOTH: smoothing 우선 / Image.SCALE_FAST: 속도 우선
				imgList.get(i).setBounds(5, 50, 210, 310);
//				imgList.get(i).setBackground(Color.CYAN);

				authorList.add(new JLabel(est5.get(i).getBook_Author()));
				authorList.get(i).setFont(cf.r16);
				authorList.get(i).setBounds(10, 360, 200, 20);
				authorList.get(i).setHorizontalAlignment(JLabel.RIGHT);

				panelList.get(i).add(rankList.get(i));
				panelList.get(i).add(titleList.get(i));
				panelList.get(i).add(imgList.get(i));
				panelList.get(i).add(authorList.get(i));
				panelList.get(i).setCursor(new Cursor(Cursor.HAND_CURSOR));
				panelList.get(i).addMouseListener(this);

			} catch (IndexOutOfBoundsException e) {
//				e.printStackTrace();
				panelList.add(new JPanel());
				panelList.get(i).setBounds(50 + i * 245, 50, 220, 380);
				panelList.get(i).setLayout(null);

				rankList.add(new JLabel("No." + (i + 1)));
				rankList.get(i).setFont(cf.ri20);
				rankList.get(i).setBounds(10, 10, 60, 20);

				titleList.add(new JLabel(""));
				titleList.get(i).setBounds(10, 30, 200, 20);

				JLabel temp = new JLabel("준비 중입니다.");
				temp.setBounds(5, 50, 210, 310);
				temp.setHorizontalAlignment(0);
				temp.setFont(cf.r18);

				panelList.get(i).add(rankList.get(i));
				panelList.get(i).add(titleList.get(i));
				panelList.get(i).add(temp);
			}
			p10.add(panelList.get(i));
			pOfest.add(panelList.get(i));
		}
		return titleList;
	} // setRecmd5() 5개씩 뽑아오기

	@Override
	public void mouseClicked(MouseEvent e) {
		// 검색버튼 누를 시
		if (e.getSource().equals(bSearch)) {
			searchFor = fSearch.getText();
			cbIndex = cbSearch.getSelectedIndex();
			if (searchFor.equals("검색어를 입력하세요.")) {
				searchFor = "";
			}
			System.out.println("검색어: " + searchFor + ", cbIndex: " + cbIndex);
			MainFrame.pBody.removeAll();
			MainFrame.pBody.add(new SearchPage(searchFor, MainFrame.pBody, cbIndex));
			MainFrame.pBody.revalidate();
			MainFrame.pBody.repaint();
		}
		// 추천 패널 10개 중 누를 시
		if (e.getSource().getClass().getSimpleName().equals("JPanel")) {
			JPanel pSelected = (JPanel) e.getSource();

			int iSelected = -1;
			for (int i = 0; i < 10; i++) {
				if (pSelected.equals(p10.get(i))) {
					iSelected = i;
					break;
				}
			}

			if (iSelected >= 5) {
				searchFor = newest5.get(iSelected - 5).getText();
			} else if (iSelected >= 0) {
				searchFor = best5.get(iSelected).getText();
			}

			System.out.println("검색어: " + searchFor + ", cbIndex: " + cbIndex);
			MainFrame.pBody.removeAll();
			MainFrame.pBody.add(new SearchPage(searchFor, MainFrame.pBody, 0));
			MainFrame.pBody.revalidate();
			MainFrame.pBody.repaint();
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
//		if (e.getSource().equals(fSearch)) {
//			if (fSearch.getText().equals("검색어를 입력하세요.")) {
//				fSearch.setText("");
//			}
//		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
//		if (e.getSource().equals(fSearch)) {
//			if (fSearch.getText().equals("")) {
//				fSearch.setText("검색어를 입력하세요.");
//			}
//		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
//		if (fSearch.getText().equals("검색어를 입력하세요.")) {
//			fSearch.setText("");
//		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 10) {
//			if (!fSearch.getText().equals("검색어를 입력하세요.")) {
//				searchFor = fSearch.getText();
//				cbIndex = cbSearch.getSelectedIndex();
//				System.out.println("검색어: " + searchFor + ", cbIndex: " + cbIndex);
//				MainFrame.pBody.removeAll();
//				MainFrame.pBody.add(new SearchPage(searchFor, MainFrame.pBody, cbIndex));
//				MainFrame.pBody.revalidate();
//				MainFrame.pBody.repaint();
//			}
			searchFor = fSearch.getText();
			cbIndex = cbSearch.getSelectedIndex();
			System.out.println("검색어: " + searchFor + ", cbIndex: " + cbIndex);
			MainFrame.pBody.removeAll();
			MainFrame.pBody.add(new SearchPage(searchFor, MainFrame.pBody, cbIndex));
			MainFrame.pBody.revalidate();
			MainFrame.pBody.repaint();
				
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

}
