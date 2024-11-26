package page;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

import java.util.ArrayList;


import custom.CustomColor;
import custom.CustomFont;
import custom.CustomLineBorder;

public class WelcomPage extends JPanel implements ActionListener, MouseListener {
//240608 17:30
	CustomColor customColor = new CustomColor();
	CustomFont customFont = new CustomFont();

	private String[] pageTitleList = { "메인화면", "검색화면", "회원가입", "대출관리" };

	private JPanel pSidebar;
	private ArrayList<JButton> alSideBtn = new ArrayList<JButton>();

	private JPanel pContent;
	private JScrollPane scroll;
	JPanel[] arrContent = new JPanel[4];

	public WelcomPage(JPanel pBody) {
		setPreferredSize(pBody.getBounds().getSize()); // 1400*750
		setLayout(null);
		setBackground(Color.WHITE);

		setSide();
		setContent();

	} // constructor

	private void setSide() {

		// 사이드 컨텐츠
		pSidebar = new JPanel();
		pSidebar.setBounds(50, 5, 220, 330);
		pSidebar.setLayout(null);
		pSidebar.setBackground(Color.WHITE);
		add(pSidebar);

		// 사이드 - 제목
		JLabel lSidebarTitle = new JLabel("안내");
		lSidebarTitle.setBounds(0, 0, 220, 80);
		lSidebarTitle.setOpaque(true);
		lSidebarTitle.setHorizontalAlignment(JLabel.CENTER);
		lSidebarTitle.setBackground(Color.WHITE);
		lSidebarTitle.setForeground(customColor.skyBlue);
		lSidebarTitle.setFont(customFont.r31);
		lSidebarTitle.setBorder(new CustomLineBorder(customColor.skyBlue, 2, CustomLineBorder.BOTTOM));
		pSidebar.add(lSidebarTitle);

		// 사이드 - 버튼&패널
		ArrayList<JPanel> pList = new ArrayList<JPanel>();
		ArrayList<JButton> btnList = new ArrayList<JButton>();

		for (int index = 0; index < pageTitleList.length; index++) {

			pList.add(new JPanel());
			pList.get(index).setBounds(0, 81 + index * 61, 220, 60);
			pList.get(index).setLayout(null);
			pList.get(index).setBorder(new CustomLineBorder(Color.LIGHT_GRAY, 1, CustomLineBorder.BOTTOM));
			pList.get(index).setBackground(Color.WHITE);
			
			
			btnList.add(new JButton(pageTitleList[index]));
			btnList.get(index).setBounds(0, 0, 220, 59);
			btnList.get(index).setOpaque(true);
			btnList.get(index).setHorizontalAlignment(JLabel.LEFT);
			btnList.get(index).setFocusPainted(false);
			btnList.get(index).setContentAreaFilled(false);
			btnList.get(index).setBackground(Color.white);
			btnList.get(index).setFont(customFont.rp13);
			btnList.get(index).setCursor(new Cursor(Cursor.HAND_CURSOR));
			btnList.get(index).setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
			btnList.get(index).addActionListener(this);
			btnList.get(index).addMouseListener(this);

			pList.get(index).add(btnList.get(index));
			alSideBtn.add(btnList.get(index));

			pSidebar.add(pList.get(index));
		}

	}// setSide()

	private void setContent() { // 나중에 메인패널 2개 함수로 빼기

		pContent = new JPanel();
		pContent.setBounds(340, 10, 1045, 740); // (340, 5)
		pContent.setLayout(null);
		pContent.setBackground(Color.WHITE);
		add(pContent);

		for (int i = 0; i < 4; i++) { //
			arrContent[i] = new JPanel();
			arrContent[i].setSize(new Dimension(1045,2000));
			arrContent[i].setLayout(new BoxLayout(arrContent[i], BoxLayout.Y_AXIS));
//			arrContent[i].setLayout(new GridLayout(0, 1));
//			arrContent[i].setLayout(null); //null이면 스크롤이 안달림
			
			arrContent[i].setBackground(Color.WHITE);
		}
		setContent0();// 0번 버튼
		setContent1();// 1번 버튼
		setContent2();// 2번 버튼
		setContent3();// 3번 버튼

		scroll = new JScrollPane(arrContent[0]);
		scroll.setBorder(null);
		scroll.setSize(1045, 698);
		scroll.getVerticalScrollBar().setUnitIncrement(25);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		pContent.add(scroll);

	}// setContent()

	private void setContent0() { // 0: 메인화면

		JPanel pContTitle = new JPanel();
		//pContTitle.setBounds(0, 5, 1045, 79);
		//BoxLayout에 여러개 넣으려면 아래처럼 디멘션으로 사이즈 줘야함, x값은 무시(Y_AXIS일 경우)
		pContTitle.setPreferredSize(new Dimension(1045,80));
		pContTitle.setLayout(null);
		pContTitle.setBackground(Color.WHITE);
		arrContent[0].add(pContTitle);

		JLabel lContTitle = new JLabel(pageTitleList[0]);
		lContTitle.setBounds(0, 0, 970, 75);
		lContTitle.setFont(customFont.r31);
		lContTitle.setBorder(new CustomLineBorder(Color.black, 2, CustomLineBorder.BOTTOM));
		pContTitle.add(lContTitle);
		
		JLabel content0_1 = new JLabel(new ImageIcon("./img/Info0_1.png"));
		content0_1.setPreferredSize(new Dimension(0,370)); //이미지 가로크기: 970
		arrContent[0].add(content0_1);
		
		JLabel content0_2 = new JLabel(new ImageIcon("./img/Info0_2.png"));
		content0_2.setPreferredSize(new Dimension(0,470));
		arrContent[0].add(content0_2);	
		
		JLabel content0_3 = new JLabel(new ImageIcon("./img/Info0_3.png"));
		content0_3.setPreferredSize(new Dimension(0,400));
		arrContent[0].add(content0_3);	
		
		

	} // setContent0

	private void setContent1() { // 1: 검색화면

		JPanel pContTitle = new JPanel();
		pContTitle.setPreferredSize(new Dimension(1045,80));
		pContTitle.setLayout(null);
		pContTitle.setBackground(Color.WHITE);
		arrContent[1].add(pContTitle);

		JLabel lContTitle = new JLabel(pageTitleList[1]);
		lContTitle.setBounds(0, 0, 970, 75);
		lContTitle.setFont(customFont.r31);
		lContTitle.setBorder(new CustomLineBorder(Color.black, 2, CustomLineBorder.BOTTOM));
		pContTitle.add(lContTitle);

		JLabel content1_1 = new JLabel(new ImageIcon("./img/infoImage/Info1_1.png"));
		content1_1.setPreferredSize(new Dimension(0,575));
		arrContent[1].add(content1_1);
		
		JLabel content1_2 = new JLabel(new ImageIcon("./img/infoImage/Info1_2.png"));
		content1_2.setPreferredSize(new Dimension(0,340));
		arrContent[1].add(content1_2);	
		
		JLabel content1_3 = new JLabel(new ImageIcon("./img/infoImage/Info1_3.png"));
		content1_3.setPreferredSize(new Dimension(0,495));
		arrContent[1].add(content1_3);	
	}// setContent1

	private void setContent2() { // 2: 회원가입

		JPanel pContTitle = new JPanel();
		//pContTitle.setBounds(0, 5, 1045, 79);
		pContTitle.setPreferredSize(new Dimension(1045,80));
		pContTitle.setLayout(null);
		pContTitle.setBackground(Color.WHITE);
		arrContent[2].add(pContTitle);

		JLabel lContTitle = new JLabel(pageTitleList[2]);
		lContTitle.setBounds(0, 0, 970, 75);
		lContTitle.setFont(customFont.r31);
		lContTitle.setBorder(new CustomLineBorder(Color.BLACK, 2, CustomLineBorder.BOTTOM));
		pContTitle.add(lContTitle);

		JLabel content2_1 = new JLabel(new ImageIcon("./img/infoImage/Info2_1.png"));
		content2_1.setPreferredSize(new Dimension(0,460));
		arrContent[2].add(content2_1);
		
		JLabel content2_2 = new JLabel(new ImageIcon("./img/infoImage/Info2_2.png"));
		content2_2.setPreferredSize(new Dimension(0,590));
		arrContent[2].add(content2_2);	

	}//setContent2

	private void setContent3() { // 3: 대출관리

		JPanel pContTitle = new JPanel();
		pContTitle.setPreferredSize(new Dimension(1045,80));
		pContTitle.setLayout(null);
		pContTitle.setBackground(Color.WHITE);
		arrContent[3].add(pContTitle);

		JLabel lContTitle = new JLabel(pageTitleList[3]);
		lContTitle.setBounds(0, 0, 970, 75);
		lContTitle.setFont(customFont.r31);
		lContTitle.setBorder(new CustomLineBorder(Color.black, 2, CustomLineBorder.BOTTOM));
		pContTitle.add(lContTitle);

		JLabel content3_1 = new JLabel(new ImageIcon("./img/infoImage/Info3_1.png"));
		content3_1.setPreferredSize(new Dimension(0,635));
		arrContent[3].add(content3_1);
		
		JLabel content3_2 = new JLabel(new ImageIcon("./img/infoImage/Info3_2.png"));
		content3_2.setPreferredSize(new Dimension(0,520));
		arrContent[3].add(content3_2);	
		
		JLabel content3_3 = new JLabel(new ImageIcon("./img/infoImage/Info3_3.png"));
		content3_3.setPreferredSize(new Dimension(0,500));
		arrContent[3].add(content3_3);		
	}//setContent3

	@Override
	public void mouseClicked(MouseEvent e) {
		// 사이드 버튼 4개 중 누를 시
		if (e.getSource().getClass().getSimpleName().equals("JButton")) {
			JButton pSelected = (JButton) e.getSource();

			int iSelected = -1;
			for (int i = 0; i < 4; i++) {
				if (pSelected.equals(alSideBtn.get(i))) {
					iSelected = i;
					break;
				}
			}
			scroll = new JScrollPane(arrContent[iSelected]);
			scroll.setBorder(null);
			scroll.setSize(1045, 698);
			scroll.getVerticalScrollBar().setUnitIncrement(25);
			scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

			pContent.removeAll();
			pContent.add(scroll);
			pContent.revalidate();
			pContent.repaint();
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
		b.setForeground(Color.BLUE);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		JButton b = (JButton) e.getSource();
		b.setForeground(Color.BLACK);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

}