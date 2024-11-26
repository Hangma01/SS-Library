package page;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import custom.CustomColor;
import custom.CustomFont;
import custom.CustomLineBorder;
import user.UserLogged;

public class MyPage_Main extends JPanel implements ActionListener, MouseListener {
	private JPanel jpBody;
	private String userId;
	private CustomColor customColor = new CustomColor();
	CustomFont pageFont = new CustomFont();
	private JPanel pContent;
	private JButton btnSideBarIntegrSearch;
	private JButton btnSidebarDetail;
	private JButton btnSideBarWithdrawal;

	public MyPage_Main(String userId, JPanel jpBody, int cbIndex) {
		this.userId = UserLogged.getUserId();
		this.jpBody = jpBody;

		setPreferredSize(new Dimension(1400, 750));
		setLayout(null);
		setBackground(Color.white);

		// 사이드바 설정
		JPanel pSidebar = new JPanel();
		pSidebar.setBounds(50, 5, 220, 300);
		pSidebar.setLayout(null);
		pSidebar.setBackground(Color.white);
		add(pSidebar);

		JLabel lSidebarTitle = new JLabel("마이페이지");
		lSidebarTitle.setBounds(0, 0, 220, 80);
		lSidebarTitle.setOpaque(true);
		lSidebarTitle.setHorizontalAlignment(JLabel.CENTER);
		lSidebarTitle.setBackground(Color.white);
		lSidebarTitle.setForeground(customColor.skyBlue);
		lSidebarTitle.setFont(pageFont.r31);
		lSidebarTitle.setBorder(new CustomLineBorder(customColor.skyBlue, 2, CustomLineBorder.BOTTOM));
		pSidebar.add(lSidebarTitle);

		// 회원정보 수정
		JPanel pSideBarIntegrSearch = new JPanel();
		pSideBarIntegrSearch.setBounds(0, 81, 220, 60);
		pSideBarIntegrSearch.setLayout(null);
		pSideBarIntegrSearch.setBackground(Color.white);
		pSideBarIntegrSearch.setBorder(new CustomLineBorder(Color.LIGHT_GRAY, 1, CustomLineBorder.BOTTOM));
		pSidebar.add(pSideBarIntegrSearch);

		btnSideBarIntegrSearch = new JButton("회원정보수정");
		btnSideBarIntegrSearch.setBounds(0, 0, 220, 59);
		btnSideBarIntegrSearch.setOpaque(true);
		btnSideBarIntegrSearch.setHorizontalAlignment(JLabel.LEFT);
		btnSideBarIntegrSearch.setFocusPainted(false);
		btnSideBarIntegrSearch.setContentAreaFilled(false);
		btnSideBarIntegrSearch.setBackground(Color.white);
		btnSideBarIntegrSearch.setFont(pageFont.rp13);
		btnSideBarIntegrSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnSideBarIntegrSearch.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
		btnSideBarIntegrSearch.addActionListener(this);
		btnSideBarIntegrSearch.addMouseListener(this);
		pSideBarIntegrSearch.add(btnSideBarIntegrSearch);

		// 대출 여부
		JPanel pSidebarDetailSearch = new JPanel();
		pSidebarDetailSearch.setBounds(0, 142, 220, 60);
		pSidebarDetailSearch.setLayout(null);
		pSidebarDetailSearch.setBorder(new CustomLineBorder(Color.LIGHT_GRAY, 1, CustomLineBorder.BOTTOM));
		pSidebarDetailSearch.setBackground(Color.white);
		pSidebar.add(pSidebarDetailSearch);

		btnSidebarDetail = new JButton("대출여부");
		btnSidebarDetail.setBounds(0, 0, 220, 59);
		btnSidebarDetail.setOpaque(true);
		btnSidebarDetail.setHorizontalAlignment(JLabel.LEFT);
		btnSidebarDetail.setFocusPainted(false);
		btnSidebarDetail.setContentAreaFilled(false);
		btnSidebarDetail.setBackground(Color.white);
		btnSidebarDetail.setFont(pageFont.rp13);
		btnSidebarDetail.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnSidebarDetail.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
		btnSidebarDetail.addActionListener(this);
		btnSidebarDetail.addMouseListener(this);
		pSidebarDetailSearch.add(btnSidebarDetail);

		// pContent 설정
		pContent = new JPanel();
		pContent.setBounds(340, 5, 1045, 700);
		pContent.setBackground(Color.white);
		pContent.setLayout(new BorderLayout());
		add(pContent);

		// 기본 페이지로 MyPage 추가
		MyPage myPage = new MyPage(userId);
		pContent.add(myPage, BorderLayout.CENTER);
		pContent.revalidate();
		pContent.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btnTemp = (JButton) e.getSource();

		
		
		if (btnTemp == btnSideBarIntegrSearch) {
			// 회원정보수정 버튼 클릭 시
			pContent.removeAll();
			pContent.add(new MyPage(userId), BorderLayout.CENTER);
			pContent.revalidate();
			pContent.repaint();
		} else if (btnTemp == btnSidebarDetail) {
			// 대출여부 버튼 클릭 시
			pContent.removeAll();
			pContent.add(new BOOK_RETURNPage(userId), BorderLayout.CENTER);
			pContent.revalidate();
			pContent.repaint();

		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
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
}
