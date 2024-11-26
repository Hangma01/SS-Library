package page;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;

import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import user.User;
import user.UserLogged;
import dao.UserDAO;
import custom.CustomColor;
import custom.CustomFont;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyPage extends JPanel implements ActionListener {
	
	JPasswordField passwordField;
	
	JTextField tfEmail;
	JTextField tfAddress;
	JTextField tfpn;
	JTextField name;
	private CustomFont customFont = new CustomFont();
	private CustomColor customColor = new CustomColor();
	private JButton button;
	private JButton btnDeleteUser;
	DeleteDailog dia;

	public MyPage(String userId) {

		setLayout(null);
		setPreferredSize(new Dimension(1400, 900));
		setBackground(Color.white);

		JTextField textfield = new JTextField();
		
		dia = new DeleteDailog();
		User user = UserLogged.getUser();

		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(null);
		contentPanel.setBounds(0, 0, 1045, 600);
		contentPanel.setBackground(Color.white);
		add(contentPanel);

		JPanel pContTitle = new JPanel();
		pContTitle.setBounds(0, 5, 1045, 79);
		pContTitle.setLayout(null);
		pContTitle.setBackground(Color.white);
		contentPanel.add(pContTitle);

		JLabel lContTitle = new JLabel("회원정보 수정");
		lContTitle.setBounds(0, 0, 970, 75);
		lContTitle.setFont(customFont.r31);
		lContTitle.setBorder(new CustomLineBorder(Color.black, 2, CustomLineBorder.BOTTOM));
		pContTitle.add(lContTitle);

		// 이름
		JLabel lName = new JLabel("이름");
		lName.setFont(customFont.r16);
		lName.setBounds(270, 150, 100, 30);
		contentPanel.add(lName);

		name = new JTextField(20);
		name.setBounds(470, 150, 200, 30);
		name.setText(user.getUSER_NAME());
		contentPanel.add(name);

		// 비밀번호
		JLabel lPW = new JLabel("비밀번호");
		lPW.setFont(customFont.r16);
		lPW.setBounds(270, 190, 150, 30);
		contentPanel.add(lPW);

		this.passwordField = new JPasswordField(20); // 클래스의 필드로 초기화
		this.passwordField.setBounds(470, 190, 200, 30);
		this.passwordField.setText(user.getUSER_PW()); 
		contentPanel.add(this.passwordField);

		// 전화번호
		JLabel Phonenumber = new JLabel("전화번호(-필수)");
		Phonenumber.setFont(customFont.r16);
		Phonenumber.setBounds(270, 230, 200, 30);
		contentPanel.add(Phonenumber);

		tfpn = new JTextField(20);
		tfpn.setBounds(470, 230, 200, 30);
		tfpn.setText(user.getUSER_PHONE());
		contentPanel.add(tfpn);

		// 주소
		JLabel lAddress = new JLabel("집 주소");
		lAddress.setFont(customFont.r16);
		lAddress.setBounds(270, 270, 200, 30);
		contentPanel.add(lAddress);

		tfAddress = new JTextField(20);
		tfAddress.setBounds(470, 270, 200, 30);
		tfAddress.setText(user.getUSER_ADRESS());
		contentPanel.add(tfAddress);

		// 이메일
		JLabel lEmail = new JLabel("이메일");
		lEmail.setFont(customFont.r16);
		lEmail.setBounds(270, 310, 200, 30);
		contentPanel.add(lEmail);

		tfEmail = new JTextField(20);
		tfEmail.setBounds(470, 310, 200, 30);
		tfEmail.setText(user.getUSER_EMAIL());
		contentPanel.add(tfEmail);

		button = new JButton("수정하기");
		button.setFont(customFont.rp14);
		button.setBounds(350, 380, 100, 30);
		button.setBackground(customColor.lightBlue);
		button.setForeground(Color.WHITE);
		button.addActionListener(this);
		button.setFocusPainted(false);
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		contentPanel.add(button);

		btnDeleteUser = new JButton("회원탈퇴");
		btnDeleteUser.setFont(customFont.rp14);
		btnDeleteUser.setBounds(500, 380, 100, 30);
		btnDeleteUser.setBackground(Color.red);
		btnDeleteUser.setForeground(Color.WHITE);
		btnDeleteUser.addActionListener(this);
		btnDeleteUser.setFocusPainted(false);
		btnDeleteUser.setCursor(new Cursor(Cursor.HAND_CURSOR));
		contentPanel.add(btnDeleteUser);

		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	    JButton btn = (JButton) e.getSource();

	    if (btn == button) {
	        if (passwordField.getPassword().length == 0 || name.getText().isEmpty() || tfpn.getText().isEmpty()
	                || tfAddress.getText().isEmpty() || tfEmail.getText().isEmpty()) {
	            JOptionPane.showMessageDialog(MyPage.this, "정보를 전부 입력해주세요.", "입력 오류", JOptionPane.ERROR_MESSAGE);
	        } else {
	            // 휴대번호 입력에 대한 유효성 검사를 추가합니다.
	            String phonePattern = "^010-(?:\\d{4})-\\d{4}$";
	            Pattern pattern = Pattern.compile(phonePattern);
	            Matcher matcher = pattern.matcher(tfpn.getText());
	            if (matcher.matches()) {
	                try {
	                    String userId = UserLogged.getUserId();

	                    User user = new User(userId, new String(passwordField.getPassword()), name.getText(), tfpn.getText(),
	                            tfAddress.getText(), tfEmail.getText());

	                    int result = UserDAO.reviseUser(user);
	                    UserLogged.setUser(user);

	                    if (result > 0) {
	                        JOptionPane.showMessageDialog(button, "수정 성공", "수정", JOptionPane.INFORMATION_MESSAGE);
	                    } else {
	                        JOptionPane.showMessageDialog(button, "수정 실패", "수정", JOptionPane.ERROR_MESSAGE);
	                    }
	                } catch (Exception ex) {
	                    ex.printStackTrace();
	                    JOptionPane.showMessageDialog(button, "오류 발생: " + ex.getMessage(), "오류",
	                            JOptionPane.ERROR_MESSAGE);
	                }
	            } else {
	                JOptionPane.showMessageDialog(MyPage.this, "휴대번호는 숫자와 하이픈(-)만 입력 가능합니다.", "입력 오류",
	                        JOptionPane.ERROR_MESSAGE);
	            }
	        }
	    } else if (btn == btnDeleteUser) {
	        dia.setVisible(true);
	    }
	}

}