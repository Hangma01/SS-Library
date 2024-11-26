package page;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import controller.*;
import custom.CustomFont;
import lending.*;

public class Manager_LendingInfo extends JPanel {
	DefaultTableModel tableModel;
	JTable lendingTable;
	private JButton jBtnAddRow;
	private JButton jBtnEditRow;
	private JButton jBtnDelRow;
	HashMap<String, String> exceptionList = new HashMap<String, String>();
	private CustomFont CustomFont = new CustomFont();
	public Manager_LendingInfo(JPanel panel) throws SQLException {

		ArrayList<Lending> LendingList = LendingController.selectAll();
		String str1 = "Index -1 out of bounds for length " + LendingList.size();

		exceptionList.put(str1, "커서를 확인하세요");
		exceptionList.put("java.lang.ArrayIndexOutOfBoundsException", "커서를 확인하세요");
		exceptionList.put("class java.lang.String cannot be cast to class java.time.LocalDateTime (java.lang.String and java.time.LocalDateTime are in module java.base of loader 'bootstrap')", "날짜는 수정할 수 없습니다.");
		Rectangle rect = panel.getBounds();
		setPreferredSize(rect.getSize());
		setLayout(new BorderLayout());

		JPanel jpMain = new JPanel();
		jpMain.setLayout(null);
		jpMain.setBackground(Color.white);
		
		JPanel pContTitle = new JPanel();
//	      pContTitle.setPreferredSize(new Dimension(0, 79));
		 pContTitle.setBounds(10, 5, 970, 79);
	      pContTitle.setLayout(null);
	      pContTitle.setBackground(Color.white);
	      jpMain.add(pContTitle);

	      JLabel lContTitle = new JLabel("대출 정보");
	      lContTitle.setBounds(0, 0, 970, 75);
	      lContTitle.setFont(CustomFont.r31);
	      lContTitle.setBorder(new CustomLineBorder(Color.black, 2, CustomLineBorder.BOTTOM));
	      pContTitle.add(lContTitle);

		JPanel tablePanel = new JPanel();
		tablePanel.setBounds(0, 150, 1000, 455);

		String header[] = { "대출 번호", "사용자 ID", "도서 코드", "대츨일", "반납일", "반납 예정일", "연체일", "연장 여부" };

		tableModel = new DefaultTableModel(header, 0);
		lendingTable = new JTable(tableModel);
		if (LendingList != null) {
			Object[][] content = new Object[LendingList.size()][header.length];

			for (int i = 0; i < LendingList.size(); i++) {
				Lending temp = LendingList.get(i);

				tableModel.addRow(new Object[] { temp.getRending_no(), temp.getUser_id(), temp.getBook_code(),
						temp.getBook_lending_date(), temp.getBook_return_date(), temp.getBook_return_expected_date(),
						temp.getBook_overdue_count(), temp.getBook_extend_yn(),

				});

			}
		}
		lendingTable.setFont(CustomFont.rp13);
		tablePanel.add(lendingTable);
		JScrollPane scrollpane = new JScrollPane(lendingTable);
		// 스크롤 크기 설정
		scrollpane.setPreferredSize(new Dimension(970, 450));
		// 테이블과 스크롤을 연결해서 화면에 보이기
		scrollpane.setViewportView(lendingTable);
		tablePanel.setBackground(Color.white);
		tablePanel.add(scrollpane);
		add(tablePanel);

		jBtnEditRow = new JButton("수정");
		jBtnEditRow.setBounds(860, 615, 60, 20);
		jBtnEditRow.setFont(CustomFont.rp13);
		jBtnEditRow.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					System.out.println(e.getActionCommand());
					DefaultTableModel model2 = (DefaultTableModel) lendingTable.getModel();
					int row = lendingTable.getSelectedRow();
					int value0 = (int) model2.getValueAt(row, 0);
					String value1 = (String) model2.getValueAt(row, 1);
					String value2 = (String) model2.getValueAt(row, 2);
					LocalDateTime value3 = (LocalDateTime) model2.getValueAt(row, 3);
					LocalDateTime value4 = (LocalDateTime) model2.getValueAt(row, 4);
					LocalDateTime value5 = (LocalDateTime) model2.getValueAt(row, 5);
					int value6 = (int) model2.getValueAt(row, 6);
					String value7 = (String) model2.getValueAt(row, 7);

					Lending EditLending = new Lending(value0, value1, value2, value3, value4, value5, value6, value7);
					int res = JOptionPane.showConfirmDialog(null, "수정 하시겠습니까?", "Messege", JOptionPane.YES_NO_OPTION);
					if (res == JOptionPane.YES_OPTION) {
						try {
							LendingController.EditLending(EditLending);
							JOptionPane.showMessageDialog(null, "수정 되었습니다", "Messege", JOptionPane.PLAIN_MESSAGE, null);
							ManagerPage.update();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				} catch (Exception e1) {
					if (exceptionList.get(e1.getMessage()) != null) {
						JOptionPane.showMessageDialog(null, exceptionList.get(e1.getMessage()), "Messege",
								JOptionPane.PLAIN_MESSAGE, null);
					} else {
						System.out.println((e1.getMessage()));
						JOptionPane.showMessageDialog(null, "알 수 없는 에러 발생\n 대표적으로 커서를 확인해주세요", "Messege", JOptionPane.PLAIN_MESSAGE, null);

					}
				}
			}
		});
		add(jBtnEditRow);
		jBtnDelRow = new JButton("삭제");
		jBtnDelRow.setBounds(925, 615, 60, 20);
		jBtnDelRow.setFont(CustomFont.rp13);
		jBtnDelRow.addActionListener(new ActionListener() {

			@Override

			public void actionPerformed(ActionEvent e) {
				try {
					System.out.println(e.getActionCommand());
					DefaultTableModel model2 = (DefaultTableModel) lendingTable.getModel();
					int row = lendingTable.getSelectedRow();
					int lending_no = (int) model2.getValueAt(row, 0);
					int res = JOptionPane.showConfirmDialog(null, "정말 삭제하겠습니까?", "Messege", JOptionPane.YES_NO_OPTION);
					if (res == JOptionPane.YES_OPTION) {
						try {
							LendingController.DelLending(lending_no);
							JOptionPane.showMessageDialog(null, "삭제 되었습니다", "Messege", JOptionPane.PLAIN_MESSAGE, null);
							ManagerPage.update();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				} catch (Exception e1) {
					if(exceptionList.get(e1.getMessage()) != null) {
     					JOptionPane.showMessageDialog(null, exceptionList.get(e1.getMessage()), "Messege", JOptionPane.PLAIN_MESSAGE, null);
     				}
     				else {
     					JOptionPane.showMessageDialog(null, "커서위치를 확인 하세요", "Messege", JOptionPane.PLAIN_MESSAGE, null);
     					System.out.println(e1.getMessage());}
				}
			}
		});
		add(jBtnDelRow);
		add(jpMain);

		setVisible(true);
	}

}
