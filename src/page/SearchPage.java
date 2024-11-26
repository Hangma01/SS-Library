package page;

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

public class SearchPage extends JPanel implements ActionListener, MouseListener {

	private CustomFont customFont = new CustomFont();
	private JPanel mainPanel;
	private CustomColor customColor = new CustomColor();


	private String[] pageTitleList = { "통합검색", "상세검색" };
	private JButton btnSideBarIntegrSearch;
	private JButton btnSidebarDetail;
	private JPanel pContent;

	
	public SearchPage(String searchText, JPanel pMainPaenl,int cbIndex) {

	//	this.mainPanel = mainPanel;

		setPreferredSize(new Dimension(1400, 750));
		setLayout(null);
		setBackground(Color.white);

		// 사이드 컨텐츠
		JPanel pSidebar = new JPanel();
		pSidebar.setBounds(50, 5, 220, 300);
		pSidebar.setLayout(null);
		pSidebar.setBackground(Color.white);
		add(pSidebar);

		// 사이드 - 제목
		JLabel lSidebarTitle = new JLabel("검색");
		lSidebarTitle.setBounds(0, 0, 220, 80);
		lSidebarTitle.setOpaque(true);
		lSidebarTitle.setHorizontalAlignment(JLabel.CENTER);
		lSidebarTitle.setBackground(Color.white);
		lSidebarTitle.setForeground(customColor.skyBlue);
		lSidebarTitle.setFont(customFont.r31);
		lSidebarTitle.setBorder(new CustomLineBorder(customColor.skyBlue, 2, CustomLineBorder.BOTTOM));
		pSidebar.add(lSidebarTitle);

		// 사이드 - 통합검색
		JPanel pSideBarIntegrSearch = new JPanel();
		pSideBarIntegrSearch.setBounds(0, 81, 220, 60);
		pSideBarIntegrSearch.setLayout(null);
		pSideBarIntegrSearch.setBorder(new CustomLineBorder(Color.LIGHT_GRAY, 1, CustomLineBorder.BOTTOM));
		pSideBarIntegrSearch.setBackground(Color.white);
		pSidebar.add(pSideBarIntegrSearch);

		btnSideBarIntegrSearch = new JButton("통합검색");
		btnSideBarIntegrSearch.setBounds(0, 0, 220, 59);
		btnSideBarIntegrSearch.setOpaque(true);
		btnSideBarIntegrSearch.setHorizontalAlignment(JLabel.LEFT);
		btnSideBarIntegrSearch.setFocusPainted(false);
		btnSideBarIntegrSearch.setContentAreaFilled(false);
		btnSideBarIntegrSearch.setBackground(Color.white);
		btnSideBarIntegrSearch.setFont(customFont.rp13);
		btnSideBarIntegrSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnSideBarIntegrSearch.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
		btnSideBarIntegrSearch.addActionListener(this);
		btnSideBarIntegrSearch.addMouseListener(this);
		pSideBarIntegrSearch.add(btnSideBarIntegrSearch);

		// 사이드 - 상세검색
		JPanel pSidebarDetailSearch = new JPanel();
		pSidebarDetailSearch.setBounds(0, 142, 220, 60);
		pSidebarDetailSearch.setLayout(null);
		pSidebarDetailSearch.setBorder(new CustomLineBorder(Color.LIGHT_GRAY, 1, CustomLineBorder.BOTTOM));
		pSidebarDetailSearch.setBackground(Color.white);
		pSidebar.add(pSidebarDetailSearch);

		btnSidebarDetail = new JButton("상세검색");
		btnSidebarDetail.setBounds(0, 0, 220, 59);
		btnSidebarDetail.setOpaque(true);
		btnSidebarDetail.setHorizontalAlignment(JLabel.LEFT);
		btnSidebarDetail.setFocusPainted(false);
		btnSidebarDetail.setContentAreaFilled(false);
		btnSidebarDetail.setBackground(Color.white);
		btnSidebarDetail.setFont(customFont.rp13);
		btnSidebarDetail.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnSidebarDetail.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
		btnSidebarDetail.addActionListener(this);
		btnSidebarDetail.addMouseListener(this);
		pSidebarDetailSearch.add(btnSidebarDetail);

		// 도서 검색 및 리스트
		pContent = new JPanel();
		pContent.setBounds(340, 5, 1045, 700);
		// pContent.setLayout(null);
		pContent.setBackground(Color.white);
		add(pContent);

		pContent.removeAll();
		pContent.add("통합검색", new IntegratedSearchPage(searchText, cbIndex, pContent, pageTitleList[0]));
		pContent.revalidate();
		pContent.repaint();

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		JButton btnTemp = (JButton) e.getSource();

		if (btnTemp == btnSideBarIntegrSearch) {
			pContent.removeAll();
			pContent.add(new IntegratedSearchPage("", 0, pContent, pageTitleList[0]));
			pContent.revalidate();
			pContent.repaint();
		} else if (btnTemp == btnSidebarDetail) {

			pContent.removeAll();
			pContent.add(new DetailSearchPage(null, null, pContent, pageTitleList[1]));
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
