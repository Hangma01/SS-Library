package page;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import book.Book;
import controller.BookController;
import custom.CustomFont;
import dao.UserDAO;
import user.UserLogged;

public class DeleteDailog extends JDialog {
    private JPasswordField passwordField;
   
    private CustomFont customFont = new CustomFont();
    String userId = UserLogged.getUserId();

    JButton btn = null;

    public DeleteDailog() {
        setLocation(700, 300);
        setLayout(null);
        setSize(530, 400);
        this.setFont(PageFont.fontHeaderMain4);
        this.setTitle("탈퇴페이지");

        JLabel warnLabel2 = new JLabel("대출중인 도서가있으면 탈퇴가 불가합니다");
        warnLabel2.setBounds(100, 30, 700, 30);
        warnLabel2.setFont(customFont.r14);
        warnLabel2.setForeground(Color.RED);
        add(warnLabel2);

        JLabel warnLabel = new JLabel("<html>사용하고 계신 아이디"+ "   " +UserLogged.getUserId() + "은(는) 탈퇴할 경우 재사용 및 복구가 "+"<br>" +"불가능합니다.</html>");
        warnLabel.setBounds(30, 50, 700, 70);
        warnLabel.setFont(customFont.r14);
        add(warnLabel);
        
        JLabel warnLabel1 = new JLabel("<html>탈퇴한 아이디는 본인과 타인 모두 재사용 및 복구가불가하오니 "+"<br>" +"신중하게 선택하시기 바랍니다.</html>");
        warnLabel1.setBounds(30, 100, 700, 70);
        warnLabel1.setFont(customFont.r14);
        warnLabel1.setForeground(Color.RED);
        add(warnLabel1);

        JLabel warnLabel3 = new JLabel("<html>탈퇴 후에는 아이디로 다시 가입할 수 없으며 아이디와" +"<br>" + "데이터는 복구할 수 없습니다.</html>");
        warnLabel3.setBounds(30, 180, 700, 30);
        warnLabel3.setFont(customFont.r14);
        warnLabel3.setForeground(Color.RED);
        add(warnLabel3);
        
        JLabel passwordLabel = new JLabel("비밀번호:");
        passwordLabel.setBounds(100, 240, 100, 30);
        passwordLabel.setFont(customFont.r14);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(200, 240, 200, 30);
        add(passwordField);

        JButton deleteButton = new JButton("탈퇴");
        deleteButton.setFont(customFont.rp14);
        deleteButton.setBounds(150, 300, 200, 30);
        deleteButton.setBackground(Color.red);
        deleteButton.setForeground(Color.WHITE);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 정말로 탈퇴하시겠습니까? 다이얼로그를 표시
                int dialogResult = JOptionPane.showConfirmDialog(null, "정말로 탈퇴하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    // "예"를 선택한 경우
                    String password = new String(passwordField.getPassword());

                    // 유저 아이디는 이미 생성자로 전달된 userId를 사용
                    String userID = userId;

                    try {
                        // 비밀번호 검증
                        boolean isValidPassword = UserDAO.validatePassword(userID, password);
                        if (!isValidPassword) {
                            JOptionPane.showMessageDialog(DeleteDailog.this, "비밀번호가 올바르지 않습니다.", "탈퇴 실패",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        // 대출한 책이 있는지 확인
                        boolean hasReturnedBooks = UserDAO.hasReturnedBooks(userID);
                        if (!hasReturnedBooks) {
                            // 반납한 책이 없는 경우
                            int rowsAffected = UserDAO.updateUserSecessioYn(userID);
                            if (rowsAffected > 0) {
                                // 탈퇴 성공
                                JOptionPane.showMessageDialog(DeleteDailog.this, "회원 탈퇴 성공", "탈퇴",
                                        JOptionPane.INFORMATION_MESSAGE);
                                dispose();
                                UserLogged.setUserId("");
                                UserLogged.setAdmin(false);
                                MainFrame.checkLog();
                                MainFrame.toMain();

                            } else {
                                // 탈퇴 실패
                                JOptionPane.showMessageDialog(DeleteDailog.this, "회원 탈퇴 실패", "탈퇴",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            // 반납한 책이 있는 경우
                            JOptionPane.showMessageDialog(DeleteDailog.this, "대출한 책이 있습니다. 대출 확인을 해주세요.",
                                    "탈퇴 실패", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(DeleteDailog.this, "오류 발생: " + ex.getMessage(), "오류",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // 내부 패널에 deleteButton 추가
        add(deleteButton);
    }
}
