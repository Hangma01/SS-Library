package page;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import custom.CustomColor;
import custom.CustomFont;
import dao.LendingDAO;
import lending.Lending;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class BOOK_RETURNPage extends JPanel {
    CustomFont pageFont = new CustomFont();
    LendingDAO lendingDao = new LendingDAO();
    private JPanel jpBody;
    private JTable bookTable;
    private DefaultTableModel tableModel;
    private ArrayList<Lending> bookReturnData;
    private String userId;
    private CustomColor customColor = new CustomColor();

    public BOOK_RETURNPage(String userId) {

        setLayout(null);
        setPreferredSize(new Dimension(1400, 900));
        
        this.userId = userId;

        JPanel jpMain = new JPanel();
        jpMain.setLayout(null);
        jpMain.setBackground(Color.white);
        jpMain.setBounds(0, 0, 1400, 900);
        add(jpMain);

        jpBody = new JPanel();
        jpBody.setBounds(0, 100, 1000, 450);
        jpBody.setBackground(Color.white);
        jpBody.setLayout(new BorderLayout());
        jpMain.add(jpBody);

        String[] columnNames = {"도서 제목", "대여일", "반납일", "반납예정일", "연체일수", "연장여부", "대출 상태"};
        tableModel = new DefaultTableModel(columnNames, 0);
        bookTable = new JTable(tableModel);
        bookTable.setFont(pageFont.r13);
        bookTable.setRowHeight(30);
        JTableHeader tableHeader = bookTable.getTableHeader();
        tableHeader.setFont(pageFont.r13);

        // 커스텀 셀 렌더러 설정
        bookTable.setDefaultRenderer(Object.class, new CustomTableCellRenderer());

        JScrollPane scrollPane = new JScrollPane(bookTable);
        jpBody.add(scrollPane, BorderLayout.CENTER);

        loadBookReturnData(userId);

        JButton returnButton = new JButton("선택 도서 반납");
        JButton extendButton = new JButton("선택 도서 연장");
        extendButton.setEnabled(false); // 기본적으로 비활성화
        returnButton.setFont(pageFont.r25);
        extendButton.setFont(pageFont.r25);

        bookTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedIndex = bookTable.getSelectedRow();
                if (selectedIndex != -1) {
                    Lending selectedLending = bookReturnData.get(selectedIndex);
                    // 선택된 항목에 따라 반납 버튼 활성화/비활성화
                    boolean canReturn = selectedLending != null
                            && selectedLending.getBook_return_date().equals(LocalDate.of(1111, 11, 11).atStartOfDay());
                    returnButton.setEnabled(canReturn);
                    // 선택된 항목에 따라 연장 버튼 활성화/비활성화
                    boolean canExtend = selectedLending != null
                            && selectedLending.getBook_return_date().equals(LocalDate.of(1111, 11, 11).atStartOfDay())
                            && selectedLending.getBook_extend_yn().equals("N");
                    extendButton.setEnabled(canExtend);
                }
            }
        });

        returnButton.setBounds(200, 25, 300, 50);
        returnButton.setEnabled(false); // 선택된 대출 항목이 없을 때 비활성화
        jpMain.add(returnButton);

        extendButton.setBounds(550, 25, 300, 50);
        jpMain.add(extendButton);

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 반납 버튼이 클릭되었을 때 실행될 코드
                int selectedIndex = bookTable.getSelectedRow();
                if (selectedIndex != -1) {
                    Lending selectedLending = bookReturnData.get(selectedIndex);
                    returnBook(selectedLending, userId);
                    // returnBook 메서드 실행 후에 returnBook1 메서드 실행
                    boolean success = LendingDAO.returnBook1(selectedLending.getBook_code());
                    if (success) {
                        // 성공적으로 반납되면 대출목록을 새로고침
                        loadBookReturnData(userId);
                        // 반납된 행을 초록색으로 표시
                        bookTable.setSelectionBackground(Color.GREEN);
                        // 선택 해제
                        bookTable.clearSelection();
                    } else {
                        JOptionPane.showMessageDialog(null, "도서 반납에 실패했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "반납할 도서를 선택해주세요.", "경고", JOptionPane.WARNING_MESSAGE);
                }
            }
        });


        extendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = bookTable.getSelectedRow();
                if (selectedIndex != -1) {
                    Lending selectedLending = bookReturnData.get(selectedIndex);
                    extendBook(selectedLending, userId);
                    // 성공적으로 반납되면 대출목록을 새로고침
                    loadBookReturnData(userId);

                    // 선택 해제
                    bookTable.clearSelection();
                } else {
                    JOptionPane.showMessageDialog(BOOK_RETURNPage.this, "연장할 책을 선택해주세요.", "경고",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    private void loadBookReturnData(String userId) {
        tableModel.setRowCount(0); // 테이블 데이터 초기화
        bookTable.getColumnModel().getColumn(0).setPreferredWidth(400);
        try {
            LendingDAO lendingDao = new LendingDAO();
            // 해당 유저가 빌린 도서만 가져오도록 수정
            bookReturnData = lendingDao.BOOK_RETURN(userId);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            for (Lending lending : bookReturnData) {
                String bookTitle = lendingDao.getBookTitle(lending.getBook_code()); // 도서 코드에 해당하는 책 제목 조회
                // 연체일수를 LendingDAO에서 가져오기
                //int overdueDays = lendingDao.calculateOverdueDays(lending.getBook_code());
                Object[] rowData = {
                        bookTitle, // 책 제목으로 변경
                        lending.getBook_lending_date().format(formatter),
                        lending.getBook_return_date().equals(LocalDate.of(1111, 11, 11).atStartOfDay()) ? "" : lending.getBook_return_date().format(formatter),
                        lending.getBook_return_expected_date().format(formatter),
                        //overdueDays, // calculateOverdueDays 호출하여 연체일수 가져오기
                        lending.getBook_overdue_count(),
                        lending.getBook_extend_yn(),
                        getStatusLabel(lending)
                };
                
                tableModel.addRow(rowData);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void returnBook(Lending selectedLending, String userId) {
        LendingDAO lendingDao = new LendingDAO();
        if (selectedLending.getBook_return_date().equals(LocalDate.of(1111, 11, 11).atStartOfDay())) {
            // 이미 계산된 연체일수를 가져와 사용
            //int overdueDays = lendingDao.calculateOverdueDaysCount(selectedLending.getBook_code(),userId);
            
        	LocalDateTime ldtNowDate = LocalDateTime.now();
     		String nowDate = ldtNowDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
     		
        	LocalDateTime ldtReturnExpeDate = selectedLending.getBook_return_expected_date();
     		String returnExpeDate = ldtReturnExpeDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        	
     		
     		int overdueDays = dateDif(nowDate,returnExpeDate);

     		if (overdueDays < 0) {
     			overdueDays = 0;
     		}
            
            //System.out.println("계산된 연체일수: " + overdueDays); // 디버깅 로그 추가

            // 연체일수를 데이터베이스에 업데이트
            boolean successUpdateOverdueDays = lendingDao.updateOverdueDays(selectedLending.getBook_code(), overdueDays);
            

            if (successUpdateOverdueDays) { 
            	// 반납일자를 업데이트
//            	boolean successUpdateReturnDate = lendingDao.updateReturnDate(selectedLending.getBook_code());
//                if (successUpdateReturnDate) {
//                    JOptionPane.showMessageDialog(this, "반납 완료!", "반납 완료", JOptionPane.INFORMATION_MESSAGE);
//                    // 리스트를 다시 불러와서 새로고침
//                    loadBookReturnData(userId);
//                } else {
//                    JOptionPane.showMessageDialog(this, "반납 일자 업데이트에 실패하였습니다.", "오류", JOptionPane.ERROR_MESSAGE);
//                }
            }else {
                JOptionPane.showMessageDialog(null, "연체일수 업데이트에 실패하였습니다.", "오류", JOptionPane.ERROR_MESSAGE);
            } 
        } else {
            JOptionPane.showMessageDialog(null, "이미 반납된 도서입니다.", "오류", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void extendBook(Lending selectedLending, String userId) {
        LendingDAO lendingDao = new LendingDAO();

        if (selectedLending.getBook_return_date().equals(LocalDate.of(1111, 11, 11).atStartOfDay())
                && selectedLending.getBook_extend_yn().equals("N")) {
            boolean success = lendingDao.updateExtendDate(selectedLending.getBook_code());

            if (success) {
                JOptionPane.showMessageDialog(null, "연장 완료!", "연장 완료", JOptionPane.INFORMATION_MESSAGE);
                // 리스트를 다시 불러와서 새로고침
                loadBookReturnData(userId);
            } else {
                JOptionPane.showMessageDialog(null, "연장 업데이트에 실패하였습니다.", "오류", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "이미 연장된 도서이거나 대출 중인 도서가 아닙니다.", "오류", JOptionPane.ERROR_MESSAGE);
        }
    }

 // 대출 상태를 문자열로 반환하는 메서드
    private String getStatusLabel(Lending lending) {
    	
    	LocalDateTime ldtNowDate = LocalDateTime.now();
 		String nowDate = ldtNowDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    	
        if (lending.getBook_return_date().equals(LocalDate.of(1111, 11, 11).atStartOfDay())) {
        	
        	LocalDateTime ldtReturnExpeDate = lending.getBook_return_expected_date();
     		String returnExpeDate = ldtReturnExpeDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        	
     		
     		int overdueDays = dateDif(nowDate,returnExpeDate);
 //          int overdueDays = lendingDao.calculateOverdueDays(lending.getBook_code(),userId);
            if (overdueDays > 0) {
                return "연체";
            } else {
                return "대출 중";
            }
        } else {
        	
        	LocalDateTime ldtReturnDate = lending.getBook_return_date();
     		String returnDate = ldtReturnDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        	
     		String overdueDate = null;
			try {
				overdueDate = AddDate(returnDate, 0, 0, lending.getBook_overdue_count());
			} catch (Exception e) {
				e.printStackTrace();
			}
     		
     		int overdueDays = dateDif(overdueDate,nowDate);
        	 //int overdueDays = lendingDao.calculateOverdueDays2(lending.getBook_code(),userId, lending.getRending_no());
             if (overdueDays > 0) {
                 return "반납(패널티)";
             }
             
        	 return "반납";
        }
    }


    private int dateDif(String start, String end) {
    	SimpleDateFormat strFormat = new SimpleDateFormat("yyyyMMdd");
        try {

           Date startDate = strFormat.parse(start);
           Date endDate = strFormat.parse(end);

           int dif = (int) ((startDate.getTime() - endDate.getTime()) / (24 * 60 * 60 * 1000));
           return dif;

        } catch (Exception e) {
           e.printStackTrace();
        }

        return -100;
     }
    
    private String AddDate(String strDate, int year, int month, int day) throws Exception {

        SimpleDateFormat dtFormat = new SimpleDateFormat("yyyyMMdd");

        Calendar cal = Calendar.getInstance();

        Date dt = dtFormat.parse(strDate);

        cal.setTime(dt);

        cal.add(Calendar.YEAR, year);
        cal.add(Calendar.MONTH, month);
        cal.add(Calendar.DATE, day);

        return dtFormat.format(cal.getTime());
     }



    // 커스텀 테이블 셀 렌더러 클래스
    private class CustomTableCellRenderer extends DefaultTableCellRenderer {
        private Color nonReturnColor = Color.YELLOW;
        private Color onTimeReturnColor = Color.GREEN;
        private Color overdueColor = Color.RED;
        private Color returnOverdueColor = customColor.reddishOrange;

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            Lending lending = bookReturnData.get(row);
            if (lending != null) {
            	String getStatus = getStatusLabel(lending);
                if (getStatus.equals("대출 중")) {
                    setBackground(nonReturnColor);
                } else if (getStatus.equals("반납")) {
                    setBackground(onTimeReturnColor);
                } else if (getStatus.equals("연체")) {
                    setBackground(overdueColor);
                } else if (getStatus.equals("반납(패널티)")) {
                	setBackground(returnOverdueColor);
                } else {
                    setBackground(table.getBackground());
                }
            }

            return this;
        }
    }
}
