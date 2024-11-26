package page;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;
import javax.swing.border.LineBorder;

import controller.UserController;
import custom.CustomColor;
import custom.CustomFont;
import dao.UserDAO;
import user.User;
import user.UserLogged;

public class LoginPage extends JPanel implements KeyListener {

	CustomFont customFont = new CustomFont();
	CustomColor customColor = new CustomColor();

	private JButton loginButton;
	private JTextField IDField;
	private JPasswordField passwordField;

	public LoginPage(JPanel pBody) {

		Rectangle rect = pBody.getBounds();
		setPreferredSize(rect.getSize());
		setBackground(Color.white);
		setLayout(null);

		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(null);
		contentPanel.setBounds(500, 145, 380, 300);
		contentPanel.setBorder(new LineBorder(customColor.lightBlue, 2));
		contentPanel.setBackground(Color.white);
		add(contentPanel);

		JLabel IDLabel = new JLabel("아이디");
		IDLabel.setFont(customFont.r16);
		IDLabel.setBounds(30, 35, 100, 30);
		contentPanel.add(IDLabel);

		IDField = new JTextField(10);
		IDField.setFont(customFont.r16);
		IDField.setBounds(140, 35, 210, 30);
		IDField.setBorder(new LineBorder(Color.gray, 1));
		contentPanel.add(IDField);

		JLabel passwordLabel = new JLabel("비밀번호");
		passwordLabel.setFont(customFont.r16);
		passwordLabel.setBounds(30, 100, 100, 30);
		contentPanel.add(passwordLabel);

		// Use JPasswordField for password input
		passwordField = new JPasswordField(10);
		passwordField.setEchoChar('*');
		passwordField.setFont(customFont.r16);
		passwordField.setBounds(140, 100, 210, 30);
		passwordField.setBorder(new LineBorder(Color.gray, 1));
		contentPanel.add(passwordField);

		loginButton = new JButton("로그인");
		loginButton.setFont(customFont.r16);
		loginButton.setBounds(30, 175, 320, 40);
		loginButton.setBackground(customColor.lightBlue);
		loginButton.setForeground(Color.white);
		loginButton.setFocusPainted(false);
		loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		contentPanel.add(loginButton);

		JButton IDFind = new JButton("아이디 찾기");
		IDFind.setFont(customFont.rp11);
		IDFind.setBounds(30, 245, 100, 20);
		IDFind.setBackground(new Color(147, 112, 219));
		IDFind.setFocusPainted(false);
		IDFind.setContentAreaFilled(false);
		IDFind.setBorder(null);
		IDFind.setForeground(Color.gray);
		IDFind.setCursor(new Cursor(Cursor.HAND_CURSOR));
		contentPanel.add(IDFind);

		IDFind.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pBody.removeAll();
				pBody.add(new IDFindPage(pBody));
				pBody.revalidate();
				pBody.repaint();
			}
		});

		JButton PWFind = new JButton("비밀번호 찾기");
		PWFind.setFont(customFont.rp11);
		PWFind.setBounds(140, 245, 110, 20);
		PWFind.setBackground(new Color(147, 112, 219));
		PWFind.setFocusPainted(false);
		PWFind.setContentAreaFilled(false);
		PWFind.setBorder(null);
		PWFind.setForeground(Color.gray);
		PWFind.setCursor(new Cursor(Cursor.HAND_CURSOR));
		contentPanel.add(PWFind);

		PWFind.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pBody.removeAll();
				pBody.add(new PWFindPage(pBody));
				pBody.revalidate();
				pBody.repaint();
			}
		});

		JButton Sign = new JButton("회원가입");
		Sign.setFont(customFont.rp11);
		Sign.setBounds(260, 245, 90, 20);
		Sign.setBackground(new Color(147, 112, 219));
		Sign.setFocusPainted(false);
		Sign.setContentAreaFilled(false);
		Sign.setBorder(null);
		Sign.setForeground(Color.gray);
		Sign.setCursor(new Cursor(Cursor.HAND_CURSOR));
		contentPanel.add(Sign);

		Sign.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pBody.removeAll();
				pBody.add(new SignPage(pBody));
				pBody.revalidate();
				pBody.repaint();
			}
		});

		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});
		IDField.addKeyListener(this);
        passwordField.addKeyListener(this);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            login();}

	}

	private void login() {
	    if (IDField.getText().isEmpty() || passwordField.getPassword().length == 0) {
	        JOptionPane.showMessageDialog(null, "고객 정보를 입력하세요", "고객 정보", JOptionPane.ERROR_MESSAGE);
	    } else {
	        UserController uc = new UserController(); // 0531정일 추가
	        try {
	            String userId = IDField.getText().trim();
	            String password = new String(passwordField.getPassword());
	            User temp = uc.login(userId, password.trim()); // 정일추가0531

	            if (!temp.getUSER_ID().isEmpty()) { // 0531정일 추가
	                if (temp.getUSER_SECESSIO_YN().equalsIgnoreCase("y")) {
	                    // 탈퇴한 회원인 경우
	                    JOptionPane.showMessageDialog(null, "이미 탈퇴한 회원입니다.", "로그인 실패", JOptionPane.ERROR_MESSAGE);
//	                    if (!temp.getUSER_ID().isEmpty()) { // 0531정일 추가
	                } else {
	                    // 로그인 성공 시 UserLogged 클래스에 사용자 ID 저장
	                    UserLogged.setUserId(IDField.getText());

	                    // user 정보 저장
	                    User user = UserDAO.getUserById(IDField.getText().trim());
	                    UserLogged.setUser(user);

	                    UserLogged.setAdmin(temp.getMANAGER_YN().equalsIgnoreCase("y") ? true : false); // 0531정일 추가
	                    MainFrame.checkLog(); // 0531정일 추가
	                    JOptionPane.showMessageDialog(null, "로그인 성공", "로그인", JOptionPane.INFORMATION_MESSAGE);
	                    MainFrame.toMain();
	                }
	            } else {
	                JOptionPane.showMessageDialog(null, "로그인 실패", "로그인", JOptionPane.ERROR_MESSAGE);
	            }
	        } catch (Exception ex) {
	            ex.printStackTrace();
	            JOptionPane.showMessageDialog(null, "오류 발생: " + ex.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
	        }
	    }
	}


}
