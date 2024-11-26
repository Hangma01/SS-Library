package page;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.LineBorder;

import custom.CustomColor;
import custom.CustomFont;
import dao.UserDAO;


public class IDFindPage extends JPanel {

	CustomFont pageFont = new CustomFont();
	CustomColor customColor = new CustomColor();

    public IDFindPage(JPanel pBody) {
        setLayout(null);
        setPreferredSize(new Dimension(1400, 900));
        setBackground(Color.white);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(null);
        contentPanel.setBounds(480, 145, 420, 300);
        contentPanel.setBorder(new LineBorder(customColor.lightBlue, 2));
        contentPanel.setBackground(Color.white);
        add(contentPanel);

        JLabel nameLabel = new JLabel("이름");
        nameLabel.setFont(pageFont.r16);
        nameLabel.setBounds(30, 35, 100, 30);
        contentPanel.add(nameLabel);

        JTextField nameField = new JTextField(10);
        nameField.setFont(pageFont.r16);
        nameField.setBounds(180, 35, 210, 30);
        nameField.setBorder(new LineBorder(Color.gray, 1));
        contentPanel.add(nameField);

        JLabel phoneLabel = new JLabel("전화번호(-포함)");
        phoneLabel.setFont(pageFont.r16);
        phoneLabel.setBounds(30, 100, 140, 30);
        contentPanel.add(phoneLabel);

        JTextField phoneField = new JTextField(10);
        phoneField.setFont(pageFont.r16);
        phoneField.setBounds(180, 100, 210, 30);
        phoneField.setBorder(new LineBorder(Color.gray, 1));
        contentPanel.add(phoneField);

        JLabel passwordLabel = new JLabel("비밀번호");
        passwordLabel.setFont(pageFont.r16);
        passwordLabel.setBounds(30, 165, 100, 30);
        contentPanel.add(passwordLabel);

        JTextField passwordField = new JTextField(10);
        passwordField.setFont(pageFont.r16);
        passwordField.setBounds(180, 165, 210, 30);
        passwordField.setBorder(new LineBorder(Color.gray, 1));
        contentPanel.add(passwordField);

        JButton findButton = new JButton("아이디 찾기");
        findButton.setFont(pageFont.r16);
        findButton.setBounds(30, 230, 360, 40);
        findButton.setBackground(customColor.lightBlue);
        findButton.setForeground(Color.white);
        findButton.setFocusPainted(false);
        findButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        contentPanel.add(findButton);

        findButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nameField.getText().isEmpty() || phoneField.getText().isEmpty() || passwordField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "모든 필드를 입력하세요", "입력 오류", JOptionPane.ERROR_MESSAGE);
                } else {
                    UserDAO userDAO = new UserDAO();
                    try {
                        String result = userDAO.findID(nameField.getText(), phoneField.getText(), passwordField.getText());
                        if (result != null) {
                            if (result.equals("탈퇴한 회원입니다")) {
                                JOptionPane.showMessageDialog(null, result, "아이디 찾기 실패", JOptionPane.ERROR_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(null, "아이디: " + result, "아이디 찾기 성공", JOptionPane.INFORMATION_MESSAGE);
                                MainFrame.pBody.removeAll();
                                MainFrame.pBody.add(new LoginPage(pBody));
                                MainFrame.pBody.revalidate();
                                MainFrame.pBody.repaint();
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "아이디를 찾을 수 없습니다", "아이디 찾기 실패", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "오류 발생: " + ex.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

    }
}
