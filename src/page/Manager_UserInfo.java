package page;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
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



import user.*;
import controller.*;
import custom.*;



public class Manager_UserInfo extends JPanel{
	DefaultTableModel tableModel;
	JTable userTable;
	private JButton jBtnAddRow;
	private JButton jBtnEditRow;
	private JButton jBtnDelRow;
	private CustomFont CustomFont = new CustomFont();
	HashMap<String, String> exceptionList = new HashMap<String, String>();
	public Manager_UserInfo(JPanel panel)  throws SQLException {
		
		ArrayList<User> UserList = UserController.select();
		
		Rectangle rect = panel.getBounds();
		setPreferredSize(rect.getSize());
		setLayout(new BorderLayout());
		String str1 = "Index -1 out of bounds for length "+ UserList.size();
		String str32 = "Index -1 out of bounds for length 32";

		exceptionList.put(str1, "커서를 확인하세요");
		exceptionList.put(str32, "커서를 확인하세요");
		exceptionList.put("null", "커서를 확인하세요");
		JPanel jpMain = new JPanel();
		
		jpMain.setLayout(null);
		jpMain.setBackground(Color.white);
		JPanel pContTitle = new JPanel();
//	      pContTitle.setPreferredSize(new Dimension(0, 79));
		 pContTitle.setBounds(10, 5, 970, 79);
	      pContTitle.setLayout(null);
	      pContTitle.setBackground(Color.white);
	      jpMain.add(pContTitle);

	      JLabel lContTitle = new JLabel("회원 정보");
	      lContTitle.setBounds(0, 0, 970, 75);
	      lContTitle.setFont(CustomFont.r31);
	      lContTitle.setBorder(new CustomLineBorder(Color.black, 2, CustomLineBorder.BOTTOM));
	      pContTitle.add(lContTitle);
		JPanel tablePanel = new JPanel();
		tablePanel.setBounds(0, 150, 1000, 455);
		
		String header[] = {"ID","PW","이름","성별","연착처","주소","이메일","관리자 여부","탈퇴 여부"};
		
		
		tableModel = new DefaultTableModel(header, 0);
		userTable = new JTable(tableModel);
		if(UserList != null) {
			Object[][] content = new Object[UserList.size()][header.length];
	
			for(int i = 0; i < UserList.size(); i++) {
				User temp = UserList.get(i);
				
				tableModel.addRow(new Object[] {
						temp.getUSER_ID(),
						temp.getUSER_PW(),
						temp.getUSER_NAME(),
						temp.getUSER_SEX(),
						temp.getUSER_PHONE(),
						temp.getUSER_ADRESS(),
						temp.getUSER_EMAIL(),
						temp.getMANAGER_YN(),
						temp.getUSER_SECESSIO_YN()
				});

			}
		}
		userTable.setFont(CustomFont.rp13);
		tablePanel.add(userTable);
		tablePanel.setBackground(Color.white);
		JScrollPane scrollpane = new JScrollPane(userTable);
		// 스크롤 크기 설정
		scrollpane.setPreferredSize(new Dimension(970,450));
		// 테이블과 스크롤을 연결해서 화면에 보이기
		scrollpane.setViewportView(userTable);
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
				 DefaultTableModel model2 = (DefaultTableModel)userTable.getModel();
                 int row = userTable.getSelectedRow();
                 String value0 = (String) model2.getValueAt(row, 0);
                 String value1 = (String) model2.getValueAt(row, 1);
                 String value2 = (String) model2.getValueAt(row, 2);
                 String value3 = (String) model2.getValueAt(row, 3);
                 String value4 = (String) model2.getValueAt(row, 4);
                 String value5 = (String) model2.getValueAt(row, 5);
                 String value6 = (String) model2.getValueAt(row, 6);
                 String value7 = (String) model2.getValueAt(row, 7);
                 String value8 = (String) model2.getValueAt(row, 8);
                 User EditUser = new User(value0,value1,value2,value3,value4,value5,value6,value7,value8);
                 int res = JOptionPane.showConfirmDialog(null,"수정 하시겠습니까?","Messege",JOptionPane.YES_NO_OPTION);
                 if(res == JOptionPane.YES_OPTION) {
                 try {
					UserController.EditUser(EditUser);
					JOptionPane.showMessageDialog(null, "수정 되었습니다", "Messege", JOptionPane.PLAIN_MESSAGE, null);
					ManagerPage.update();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					System.out.println(e1.getMessage());
				}
                 }  
				
			}catch(Exception e1) {
				JOptionPane.showMessageDialog(null, "커서를 확인하세요", "Messege", JOptionPane.PLAIN_MESSAGE, null);
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
				 DefaultTableModel model2 = (DefaultTableModel)userTable.getModel();
                 int row = userTable.getSelectedRow();
                 String user_id = (String) model2.getValueAt(row, 0);
                 int res = JOptionPane.showConfirmDialog(null,"정말 삭제하겠습니까?","Messege",JOptionPane.YES_NO_OPTION);
                 if(res == JOptionPane.YES_OPTION) {
                 try {
					UserController.DelUser(user_id);
					JOptionPane.showMessageDialog(null, "삭제 되었습니다", "Messege", JOptionPane.PLAIN_MESSAGE, null);
					ManagerPage.update();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                }
			}catch(Exception e1) {
		
				
					JOptionPane.showMessageDialog(null, "커서를 확인하세요", "Messege", JOptionPane.PLAIN_MESSAGE, null);
				}
	
			}
		});
		add(jBtnDelRow);
		add(jpMain);
		
		
		setVisible(true);
	}
	
}
