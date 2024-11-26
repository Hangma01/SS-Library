package page;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import custom.CustomColor;
import custom.CustomFont;
import custom.CustomLineBorder;
import user.UserLogged;

public class MainFrame extends JFrame implements MouseListener {
	//240606 19:40
	
	/* MainFrame에 public static인 것들
	 * pBody
	 * checkLog() :  로그인&로그아웃 시 헤더에 로그인 버튼들 변경
	 * toMain() : MainPage로 돌아가기
	 */
	
	CustomFont cf = new CustomFont();
	CustomColor cc = new CustomColor();
	
	private static JPanel pHeader; 
	private JPanel pNeck; 
	public static JPanel pBody; // 몸통 패널
	
	private static JPanel pHeaderButton;
	private static JLabel lHome; // toMain버튼 on pHeader
	private static JLabel lLogin; // 로그인버튼
	private static JLabel lJoin; // 회원가입버튼
	private static JLabel lLogout; // 로그아웃버튼
	private static JLabel lMyPage; // 회원페이지버튼
	private static JLabel lAdminPage; // 관리자페이지버튼 

	private static JLabel lLogged; 
	
	private JLabel lHomeImg; // 홈이미지아이콘 on pNeck
	private JLabel lHomeLab; // 홈레이블
	private JLabel lSearch; // 검색메뉴 버튼 
	private JLabel lWelcome; // 안내메뉴 버튼 


	public MainFrame() {
		setTitle("도서관리 프로그램");
		setSize(new Dimension(1400, 900));
		setLocationRelativeTo(null);
		setLayout(null);
		setResizable(false);
		setIconImage(new ImageIcon("./img/Icon.png").getImage());
		
		setPanel(); // 기본패널 세팅 // 상단 고정
		setHeader(); // 기본버튼 세팅(on pHeader) // 상단 고정

		pBody.add(new MainPage(pBody)); //MainPage on pBody
		
		add(pBody);
		add(pNeck);
		add(pHeader);
		setDefaultCloseOperation(3);
		setVisible(true);
	}// constructor()

	private void setPanel() { // 기본패널 세팅
		
		pHeader = new JPanel(); 
		pHeader.setBounds(0, 0, 1400, 30);
		pHeader.setLayout(null);
		pHeader.setBackground(Color.LIGHT_GRAY);

		pNeck = new JPanel(); // 메인이미지 + 메뉴버튼
		pNeck.setBounds(0, 30, 1400, 120);
		pNeck.setLayout(null);
		pNeck.setBorder(new CustomLineBorder(cc.skyBlue, 2, CustomLineBorder.BOTTOM));
		pNeck.setBackground(Color.WHITE);

		pBody = new JPanel(); //몸통 패널
		pBody.setBounds(0, 150, 1400, 750);
		pBody.setLayout(new BorderLayout());
		pBody.setBackground(Color.WHITE);

		pHeaderButton = new JPanel(); // Header 버튼 패널
		pHeaderButton.setBounds(1050, 10, 300, 20);//우측 여백 50??
		pHeaderButton.setLayout(new GridLayout(1, 3));
		pHeaderButton.setBackground(Color.LIGHT_GRAY);
		
	}// panelSet()

	private void setHeader() { // 헤더 세팅

		lLogged = new JLabel("");
		
		lHome = new JLabel("메인으로");
		lHome.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lHome.addMouseListener(this);
		lHome.setFont(cf.rp13);
		lHome.setHorizontalAlignment(0); //center

		lLogin = new JLabel("로그인");
		lLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lLogin.addMouseListener(this);
		lLogin.setFont(cf.rp13);
		lLogin.setHorizontalAlignment(0); //center

		lJoin = new JLabel("회원가입");
		lJoin.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lJoin.addMouseListener(this);
		lJoin.setFont(cf.rp13);
		lJoin.setHorizontalAlignment(0); //center

		lLogout = new JLabel("로그아웃");
		lLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lLogout.addMouseListener(this);
		lLogout.setFont(cf.rp13);
		lLogout.setHorizontalAlignment(0); //center

		lMyPage = new JLabel("마이페이지");
		lMyPage.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lMyPage.addMouseListener(this);
		lMyPage.setFont(cf.rp13);
		lMyPage.setHorizontalAlignment(0); //center


		lAdminPage = new JLabel("관리자페이지");
		lAdminPage.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lAdminPage.addMouseListener(this);
		lAdminPage.setFont(cf.rp13);
		lAdminPage.setHorizontalAlignment(0); //center

		pHeaderButton.add(lHome); // 기본세팅 1of3
		pHeaderButton.add(lLogin); // 기본세팅 2of3
		pHeaderButton.add(lJoin); // 기본세팅 3of3
		pHeader.add(pHeaderButton);
//		-----------------------------------------------------------
		ImageIcon imgIcon = new ImageIcon("./img/book1.png");
		Image img = imgIcon.getImage();
		Image changeImg = img.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
		lHomeImg = new JLabel("SS도서관리", new ImageIcon(changeImg),SwingConstants.RIGHT);
		lHomeImg.setFont(cf.r34);
		lHomeImg.setVerticalAlignment(JLabel.CENTER);
		lHomeImg.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lHomeImg.addMouseListener(this);
		lHomeImg.setBounds(50, 10, 300, 100);

		lSearch = new JLabel("도서 검색"); // 메뉴버튼 on pNeck
		lSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lSearch.addMouseListener(this);
		lSearch.setBounds(1070, 50, 100, 20);
		lSearch.setFont(cf.r20);
		
		lWelcome = new JLabel("사용 안내"); //메뉴버튼 on pNeck
		lWelcome.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lWelcome.addMouseListener(this);
		lWelcome.setBounds(1220, 50, 100, 20);
		lWelcome.setFont(cf.r20);
		
		pNeck.add(lHomeImg);
		pNeck.add(lSearch);
		pNeck.add(lWelcome);

	} // headerSet()
	
	public static void checkLog() {// 로그인 여부 체크 -> 헤더 버튼 세팅
		System.out.println("id: " + UserLogged.getUserId() + ", 관리자여부: " + UserLogged.isAdmin());
		
//		lLogged = new JLabel(UserLogged.getUserId() + (UserLogged.getUserId().isEmpty()?"":"님, 환영합니다."));
		lLogged.setText(UserLogged.getUserId() + (UserLogged.getUserId().isEmpty()?"":"님, 환영합니다."));
		lLogged.setBounds(550, 10, 300, 20);
		lLogged.setBackground(Color.LIGHT_GRAY);
		lLogged.setHorizontalAlignment(0);
		pHeader.add(lLogged);
		pHeader.repaint();
		
		pHeaderButton.removeAll();
		pHeaderButton.add(lHome);
		if(UserLogged.getUserId().isEmpty()) {
			pHeaderButton.add(lLogin);
			pHeaderButton.add(lJoin);
			
		}else {
			pHeaderButton.add(lLogout);
			if(UserLogged.isAdmin()==true) {
				pHeaderButton.add(lAdminPage);
			}else {
				pHeaderButton.add(lMyPage);
			}
		}
		pHeaderButton.revalidate();
		pHeaderButton.repaint();
		
	}// checkLog()

	public static void toMain() {
		System.out.println("toMain()");
		pBody.removeAll();
		pBody.add(new MainPage(pBody));
		pBody.revalidate();
		pBody.repaint();
	}//toMain
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource().equals(lHome)) {
			toMain();
		} else if (e.getSource().equals(lLogin)) {
			System.out.println("LoginPage로"); //로그인페이지 열기
			pBody.removeAll();
			pBody.add(new LoginPage(pBody));
			pBody.revalidate();
			pBody.repaint();
		} else if (e.getSource().equals(lJoin)) {
			System.out.println("SignPage로"); // 회원가입페이지 열기
			pBody.removeAll();
			pBody.add(new SignPage(pBody));
			pBody.revalidate();
			pBody.repaint();
		} else if (e.getSource().equals(lLogout)) {
			int logout = JOptionPane.showConfirmDialog(null,"로그아웃 하시겠습니까?", "Message", JOptionPane.YES_NO_OPTION);
			if(logout == 0) {
				UserLogged.setUserId("");
				UserLogged.setAdmin(false);
				checkLog();
				toMain();
			}
		} else if (e.getSource().equals(lMyPage)) {
			System.out.println("MyPage로"); // 마이페이지 열기
			pBody.removeAll();
			pBody.add(new MyPage_Main(getTitle(), pBody, getDefaultCloseOperation()));
			pBody.revalidate();
			pBody.repaint();
		} else if (e.getSource().equals(lAdminPage)) {
			System.out.println("AdminPage로"); // 관리자페이지 열기
			pBody.removeAll();
			pBody.add(new ManagerPage(pBody));
			pBody.revalidate();
			pBody.repaint();
		} else if (e.getSource().equals(lHomeImg)) {
			toMain();
		} else if (e.getSource().equals(lSearch)) {
			System.out.println("SearchPage로"); // 검색페이지 열기
			pBody.removeAll();
			pBody.add(new SearchPage("",pBody,0));
			pBody.revalidate();
			pBody.repaint();
		} else if( e.getSource().equals(lWelcome)) {
			System.out.println("WelcomePage로"); // 안내페이지 열기
			pBody.removeAll();
			pBody.add(new WelcomPage(pBody));
			pBody.revalidate();
			pBody.repaint();

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
		JLabel temp = (JLabel) e.getSource();
		
		if(temp != lHomeImg) {
			temp.setForeground(Color.BLUE);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		JLabel temp = (JLabel) e.getSource();
		temp.setForeground(Color.BLACK);
	}

}