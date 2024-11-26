package page;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import user.User;
import controller.UserController;
import custom.CustomColor;
import custom.CustomFont;
import dao.UserDAO;

public class SignPage extends JPanel {

	CustomFont customFont = new CustomFont();
	JTextField txt1, txt3, txt4, txt5, txtCity, txtDistrict, txt7;
	JPasswordField txt2;
	CustomColor customColor = new CustomColor();
	JComboBox<String> emailComboBox;
	JTextField customEmailField;
	JButton comboBoxButton;

	public SignPage(JPanel jpBody) {

		setLayout(null);
		setPreferredSize(new Dimension(1400, 900));
		setBackground(Color.white);

		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(null);
		contentPanel.setBounds(435, 100, 550, 470);
		contentPanel.setBorder(new LineBorder(customColor.lightBlue, 2));
		contentPanel.setBackground(Color.white);
		add(contentPanel);

		JLabel lbl1 = new JLabel("아이디");
		lbl1.setFont(customFont.r16);
		lbl1.setBounds(30, 35, 100, 30);
		contentPanel.add(lbl1);

		txt1 = new JTextField(20);
		txt1.setBounds(200, 35, 210, 30);
		txt1.setFont(customFont.r16);
		txt1.setBorder(new LineBorder(Color.gray, 1));
		contentPanel.add(txt1);

		JButton checkButton = new JButton("중복 확인");
		checkButton.setFont(customFont.r13);
		checkButton.setBounds(420, 35, 100, 30);
		checkButton.setFocusPainted(false);
		checkButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		contentPanel.add(checkButton);

		JLabel lbl2 = new JLabel("비밀번호");
		lbl2.setFont(customFont.r16);
		lbl2.setBounds(30, 85, 100, 30);
		contentPanel.add(lbl2);

		txt2 = new JPasswordField(20);
		txt2.setFont(customFont.r16);
		txt2.setEchoChar('*');
		txt2.setBounds(200, 85, 320, 30);
		txt2.setBorder(new LineBorder(Color.gray, 1));
		contentPanel.add(txt2);

		JLabel lbl3 = new JLabel("이름");
		lbl3.setFont(customFont.r16);
		lbl3.setBounds(30, 135, 150, 30);
		contentPanel.add(lbl3);

		txt3 = new JTextField(20);
		txt3.setBounds(200, 135, 320, 30);
		txt3.setFont(customFont.r16);
		txt3.setBorder(new LineBorder(Color.gray, 1));
		contentPanel.add(txt3);

		JLabel lbl4 = new JLabel("성별");
		lbl4.setFont(customFont.r16);
		lbl4.setBounds(30, 185, 150, 30);
		contentPanel.add(lbl4);

		// 성별 선택 라디오 버튼
		JRadioButton maleRadioButton = new JRadioButton("M");
		maleRadioButton.setFont(customFont.r17);
		maleRadioButton.setBounds(300, 185, 50, 30);
		maleRadioButton.setBackground(Color.white);
		contentPanel.add(maleRadioButton);

		JRadioButton femaleRadioButton = new JRadioButton("F");
		femaleRadioButton.setFont(customFont.r17);
		femaleRadioButton.setBounds(400, 185, 50, 30);
		femaleRadioButton.setBackground(Color.white);
		contentPanel.add(femaleRadioButton);

		// 라디오 버튼 그룹으로 묶기
		ButtonGroup genderButtonGroup = new ButtonGroup();
		genderButtonGroup.add(maleRadioButton);
		genderButtonGroup.add(femaleRadioButton);

		// 전화번호 라벨과 텍스트 필드 추가 부분
		JLabel lbl5 = new JLabel("전화번호(-포함) ");
		lbl5.setFont(customFont.r16);
		lbl5.setBounds(30, 235, 140, 30);
		contentPanel.add(lbl5);

		txt5 = new JTextField(20);
		txt5.setBounds(200, 235, 320, 30);
		txt5.setFont(customFont.r16);
		txt5.setBorder(new LineBorder(Color.gray, 1));
		txt5.setText(" 예시 010 - 0000 - 0000");

		txt5.addFocusListener(new FocusAdapter() {

			@Override
			public void focusGained(FocusEvent e) {
				txt5.setText(null);
			}
		});

		contentPanel.add(txt5);

		JLabel lbl6 = new JLabel("주소");
		lbl6.setFont(customFont.r16);
		lbl6.setBounds(30, 285, 100, 30);
		contentPanel.add(lbl6);

		txtCity = new JTextField(10);
		txtCity.setBounds(200, 285, 125, 30);
		txtCity.setFont(customFont.r16);
		txtCity.setBorder(new LineBorder(Color.gray, 1));
		contentPanel.add(txtCity);

		JLabel lblCity = new JLabel("시");
		lblCity.setFont(customFont.r16);
		lblCity.setBounds(335, 285, 20, 30);
		contentPanel.add(lblCity);

		txtDistrict = new JTextField(10);
		txtDistrict.setBounds(365, 285, 125, 30);
		txtDistrict.setFont(customFont.r16);
		txtDistrict.setBorder(new LineBorder(Color.gray, 1));
		contentPanel.add(txtDistrict);

		JLabel lblDistrict = new JLabel("구");
		lblDistrict.setFont(customFont.r16);
		lblDistrict.setBounds(500, 285, 20, 30);
		contentPanel.add(lblDistrict);

		JLabel lbl7 = new JLabel("이메일");
		lbl7.setFont(customFont.r16);
		lbl7.setBounds(30, 335, 100, 30);
		contentPanel.add(lbl7);

		txt7 = new JTextField(10);
		txt7.setBounds(200, 335, 125, 30);
		txt7.setFont(customFont.r16);
		txt7.setBorder(new LineBorder(Color.gray, 1));
		contentPanel.add(txt7);

		JLabel atLabel = new JLabel("@");
		atLabel.setFont(customFont.r16);
		atLabel.setBounds(340, 335, 20, 30);
		contentPanel.add(atLabel);

		// 콤보박스 생성 및 추가
		String[] emailDomains = { "직접 입력", "naver.com", "daum.net", "gmail.com", "example.com", "nate.com",
				"kakao.com" };
		emailComboBox = new JComboBox<>(emailDomains);
		emailComboBox.setBounds(365, 335, 155, 30);
		emailComboBox.setBackground(Color.white);
		emailComboBox.setFont(customFont.r16);
		contentPanel.add(emailComboBox);

		// 사용자 입력 필드 생성 및 추가
		customEmailField = new JTextField(10);
		customEmailField.setBounds(340, 335, 140, 30); 
		customEmailField.setFont(customFont.r16);
		customEmailField.setBorder(new LineBorder(Color.gray, 1));
		customEmailField.setVisible(false);
		contentPanel.add(customEmailField);

		ImageIcon originalIcon = new ImageIcon("./img/9660.png"); 


		int targetWidth = 15; 
		int targetHeight = 8; 
		Image scaledImage = originalIcon.getImage().getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImage);

		
		comboBoxButton = new JButton(scaledIcon);
		comboBoxButton.setBounds(480, 335, 20, 30);
		comboBoxButton.setFont(customFont.r16);
		comboBoxButton.setFocusPainted(false);
		comboBoxButton.setVisible(false); // 초기에는 보이지 않도록 설정
		contentPanel.add(comboBoxButton);

		// 콤보박스 액션 리스너
		emailComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedDomain = (String) emailComboBox.getSelectedItem();
				if (selectedDomain.equals("직접 입력")) {
					customEmailField.setVisible(true);
					customEmailField.setEditable(true);
					customEmailField.setText(null);
					emailComboBox.setVisible(false);
					comboBoxButton.setVisible(true);
					customEmailField.requestFocus();
				}
			}
		});

		// 버튼 액션 리스너
		comboBoxButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				customEmailField.setVisible(false);
				comboBoxButton.setVisible(false);
				emailComboBox.setVisible(true);
				emailComboBox.showPopup();
			}
		});

		JButton button = new JButton("회원가입");
		button.setFont(customFont.r16);
		button.setBounds(30, 410, 490, 40);
		button.setBackground(customColor.lightBlue);
		button.setForeground(Color.WHITE);
		button.setFocusPainted(false);
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		contentPanel.add(button);

		UserDAO userDAO = new UserDAO();

		checkButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String userId = txt1.getText();

				boolean isDuplicate;
				try {
					isDuplicate = userDAO.isUserIdDuplicate(userId);

					if (isDuplicate) {
						JOptionPane.showMessageDialog(contentPanel, "중복된 아이디입니다.", "중복 확인", JOptionPane.ERROR_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(contentPanel, "사용 가능한 아이디입니다.", "중복 확인",
								JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 사용자가 입력한 값들
				String userId = txt1.getText();
				String password = new String(txt2.getPassword());
				String name = txt3.getText();
				String phoneNumber = txt5.getText();
				String city = txtCity.getText();
				String district = txtDistrict.getText();
				String address = city + "시" + " " + district + "구";
				String email;

				// 이메일 가공
				if (emailComboBox.getSelectedItem().equals("직접 입력")) {
					email = txt7.getText() + "@" + customEmailField.getText();
				} else {
					email = txt7.getText() + "@" + emailComboBox.getSelectedItem().toString();
				}

				boolean isDuplicate2;
				try {
					isDuplicate2 = userDAO.isPhoneNumberDuplicate(phoneNumber);

					if (isDuplicate2) {
						JOptionPane.showMessageDialog(contentPanel, "중복된 휴대폰 번호입니다.", "입력 오류",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}

				// 입력값 길이 제약
				if (userId.length() >= 20 || password.length() >= 20 || name.length() >= 20
						|| phoneNumber.length() >= 20 || address.length() >= 20 || email.length() >= 20) {
					JOptionPane.showMessageDialog(contentPanel, "입력값은 20자 이하여야 합니다.", "입력 오류",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				// 이름과 주소에 대한 정규 표현식 패턴
				String nameRegex = "^[A-Za-z가-힣]*$"; // 영어와 한글만 허용
				String addressRegex = "^[A-Za-z가-힣0-9\\s]*$"; // 영어, 한글, 숫자, 공백만 허용

				// 이름이 올바른지 확인
				if (!name.matches(nameRegex)) {
					JOptionPane.showMessageDialog(contentPanel, "이름은 영어와 한글만 입력 가능합니다.", "입력 오류",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				// 주소가 올바른지 확인
				if (!address.matches(addressRegex)) {
					JOptionPane.showMessageDialog(contentPanel, "주소는 영어, 한글, 숫자, 공백만 입력 가능합니다.", "입력 오류",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				// 정규 표현식 패턴
				String phoneRegex = "^010-(?:\\d{4})-\\d{4}$";

				// 입력한 휴대번호가 유효한지 확인
				if (!phoneNumber.matches(phoneRegex)) {
					JOptionPane.showMessageDialog(contentPanel, "유효하지 않은 휴대번호입니다. 숫자와 하이픈(-)만 입력하세요.", "입력 오류",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				// 회원가입 처리
				if (userId.isEmpty() || password.isEmpty() || name.isEmpty() || phoneNumber.isEmpty()
						|| address.isEmpty() || email.isEmpty()) {
					JOptionPane.showMessageDialog(contentPanel, "정보를 전부 입력해주세요.", "입력 오류", JOptionPane.ERROR_MESSAGE);
					return;
				}

				if (maleRadioButton.isSelected() || femaleRadioButton.isSelected()) {
					saveUserData(userId, password, name, maleRadioButton.isSelected() ? "M" : "F", phoneNumber, city,
							district, email);
					jpBody.removeAll();
					jpBody.revalidate();
					jpBody.repaint();
					MainFrame.toMain();
				} else {
					JOptionPane.showMessageDialog(contentPanel, "성별을 선택해주세요.", "입력 오류", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	private void saveUserData(String userId, String password, String name, String gender, String phoneNumber,
			String city, String district, String email) {
		try {
			UserController userController = new UserController();
			String address = city + "시" + " " + district + "구";
			User user = new User(userId, password, name, gender, phoneNumber, address, email, "N", "N");

			boolean isSuccess = userController.join(user);
			if (isSuccess) {
				JOptionPane.showMessageDialog(this, "회원가입에 성공했습니다.", "회원가입 성공", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(this, "회원가입에 실패했습니다.", "회원가입 실패", JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
