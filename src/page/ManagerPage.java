package page;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import custom.CustomFont;






public class ManagerPage extends JPanel implements ActionListener, MouseListener{
	private JButton btnSideUser;
	private JButton btnSideBook;
	private JButton btnSideLending;
	static JPanel UsermPanel;
	static String panelID;
	CustomFont CustomFont = new CustomFont();
	public ManagerPage(JPanel panel) {
		
		Rectangle rect = panel.getBounds();
		setPreferredSize(rect.getSize());
		setLayout(new BorderLayout());
		setSize(new Dimension(1400,900));
		
		
		JPanel pBody = new JPanel(); //몸통 패널
		pBody.setBounds(0, 150, 1400, 750);
		pBody.setLayout(null); 
		pBody.setBackground(Color.WHITE);
	
		
		
		JPanel pSideContent = new JPanel();
		pSideContent.setBounds(50, 0, 220, 300);
		pSideContent.setLayout(null);
		pSideContent.setBackground(Color.white);
		add(pSideContent);
		
		
		// 사이드 검색
		JLabel lSideTitle = new JLabel("관리자");
		lSideTitle.setBounds(0, 0, 220, 80);
		lSideTitle.setOpaque(true);
		lSideTitle.setHorizontalAlignment(JLabel.CENTER);
		lSideTitle.setBackground(Color.white);
		lSideTitle.setForeground(new Color(0,102,255));
		lSideTitle.setFont(CustomFont.r31);
		lSideTitle.setBorder(new CustomLineBorder(new Color(0,102,255), 2, CustomLineBorder.BOTTOM));
		lSideTitle.setBackground(Color.white);
		pSideContent.add(lSideTitle);
		
		
		// 사이드 통합검색
		JPanel pSideUser = new JPanel();
		pSideUser.setBounds(0, 81, 220, 60);
		pSideUser.setLayout(null);
		pSideUser.setBorder(new CustomLineBorder(Color.LIGHT_GRAY, 1, CustomLineBorder.BOTTOM));
		pSideContent.add(pSideUser);
		pBody.add(pSideContent);
		pSideUser.setBackground(Color.white);
		

		btnSideUser = new JButton("유저 관리");
		btnSideUser.setBounds(0, 0, 220, 59);
		btnSideUser.setOpaque(true);
		btnSideUser.setHorizontalAlignment(JLabel.LEFT);
		btnSideUser.setBackground(Color.white);
		btnSideUser.setContentAreaFilled(false);
		btnSideUser.setBackground(Color.white);
		btnSideUser.setFocusPainted(false);
		btnSideUser.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnSideUser.setFont(CustomFont.rp13);
		btnSideUser.setBorder(BorderFactory.createEmptyBorder(0 , 20, 0 , 0));
		btnSideUser.addActionListener(this);
		btnSideUser.addMouseListener(this);
		pSideUser.add(btnSideUser);

		// 사이트 상세검색
		JPanel pSideBook = new JPanel();
		pSideBook.setBounds(0, 142, 220, 60);
		pSideBook.setLayout(null);
		pSideBook.setBorder(new CustomLineBorder(Color.LIGHT_GRAY, 1, CustomLineBorder.BOTTOM));
		pSideBook.setBackground(Color.white);
		pSideContent.add(pSideBook);
		
		btnSideBook = new JButton("도서 관리");
		btnSideBook.setBounds(0, 0, 220, 59);
		btnSideBook.setOpaque(true);
		btnSideBook.setHorizontalAlignment(JLabel.LEFT);
		btnSideBook.setFocusPainted(false);
		btnSideBook.setContentAreaFilled(false);
		btnSideBook.setBackground(Color.white);
		btnSideBook.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnSideBook.setFont(CustomFont.rp13);
		btnSideBook.setBorder(BorderFactory.createEmptyBorder(0 , 20, 0 , 0));
		btnSideBook.addActionListener(this);
		btnSideBook.addMouseListener(this);
		pSideBook.add(btnSideBook);

		
		JPanel pSideLending = new JPanel();
		pSideLending.setBounds(0, 203, 220, 60);
		pSideLending.setLayout(null);
		pSideLending.setBorder(new CustomLineBorder(Color.LIGHT_GRAY, 1, CustomLineBorder.BOTTOM));
		pSideLending.setBackground(Color.white);
		pSideContent.add(pSideLending);
		
		btnSideLending = new JButton("대출반납 관리");
		btnSideLending.setBounds(0, 0, 220, 59);
		btnSideLending.setOpaque(true);
		btnSideLending.setHorizontalAlignment(JLabel.LEFT);
		btnSideLending.setBackground(Color.white);
		btnSideLending.setFont(CustomFont.rp13);
		btnSideLending.setContentAreaFilled(false);
		btnSideLending.setFocusPainted(false);
		btnSideLending.setBackground(Color.white);
		btnSideLending.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnSideLending.setBorder(BorderFactory.createEmptyBorder(0 , 20, 0 , 0));
		btnSideLending.addActionListener(this);
		btnSideLending.addMouseListener(this);
		pSideLending.add(btnSideLending);
		
		UsermPanel = new JPanel();
		UsermPanel.setBounds(340, 0, 1180, 750);
		UsermPanel.setBackground(Color.white);
		
		pBody.add(UsermPanel);
		pBody.add(pSideContent);
		

		
		
			
			
		
		
		
		JLabel jlHeaderImg = new JLabel(new ImageIcon("./img/ssIcon.png"));
		jlHeaderImg.setBounds(50, 10, 100, 100);

		

		add(pBody);
		
		setVisible(true);
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
		b.setForeground(Color.BLUE);
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		JButton b = (JButton) e.getSource();
		b.setForeground(Color.BLACK);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btnTemp = (JButton)e.getSource();
		
		

		if(btnTemp == btnSideUser) {
			UsermPanel.removeAll();
			UsermPanel.setLayout(new BorderLayout());
			try {
				UsermPanel.add(new Manager_UserInfo(UsermPanel));
				panelID = "user";
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			UsermPanel.revalidate();
			UsermPanel.repaint();
		}else if(btnTemp == btnSideBook) {
			UsermPanel.removeAll();
			UsermPanel.setLayout(new BorderLayout());
			try {
				UsermPanel.add(new Manager_BookInfo(UsermPanel));
				panelID = "book";
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			UsermPanel.revalidate();
			UsermPanel.repaint();
		}
		else if(btnTemp == btnSideLending) {
			UsermPanel.removeAll();
			UsermPanel.setLayout(new BorderLayout());
			try {
				UsermPanel.add(new Manager_LendingInfo(UsermPanel));
				panelID = "lending";
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			UsermPanel.revalidate();
			UsermPanel.repaint();
		}
	}
	public static void update() {
		if(panelID == "user") {
			UsermPanel.removeAll();
			UsermPanel.setLayout(new BorderLayout());
			try {
				UsermPanel.add(new Manager_UserInfo(UsermPanel));
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			UsermPanel.revalidate();
			UsermPanel.repaint();
		}
		else if(panelID == "book") {
			UsermPanel.removeAll();
			UsermPanel.setLayout(new BorderLayout());
			try {
				UsermPanel.add(new Manager_BookInfo(UsermPanel));
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			UsermPanel.revalidate();
			UsermPanel.repaint();
		}
		else {
			UsermPanel.removeAll();
			UsermPanel.setLayout(new BorderLayout());
			try {
				UsermPanel.add(new Manager_LendingInfo(UsermPanel));
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			UsermPanel.revalidate();
			UsermPanel.repaint();
		}
	}
}
