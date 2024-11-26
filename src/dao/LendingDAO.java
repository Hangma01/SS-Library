package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dao.DBUtil;
import lending.Lending;

public class LendingDAO { // 240531 15:00

	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	int cnt;
	ArrayList<Lending> rendList = null;

	// �븞�슚以�
	public ArrayList<Lending> select() throws SQLException {
		Lending rendingInfo = null;

		conn = DBUtil.getConnection();

		String sql = "select * from LENDING_TABLE where BOOK_RETURN_DATE = TO_DATE('11111111','YYYY-MM-DD')";

		ps = conn.prepareStatement(sql);

		rs = ps.executeQuery();

		rendList = new ArrayList<Lending>();
		while (rs.next()) {
			rendingInfo = new Lending(rs.getInt("lending_no"), rs.getString("user_id"), rs.getString("book_code"),
					rs.getTimestamp("book_lending_date").toLocalDateTime(),
					rs.getTimestamp("book_return_date").toLocalDateTime(),
					rs.getTimestamp("book_return_expected_date").toLocalDateTime(), rs.getInt("book_overdue_COUNT"),
					rs.getString("book_extend_yn"));
//			System.out.println(rendingInfo);
			rendList.add(rendingInfo);
		}

		rs.close();
		ps.close();
		conn.close();

		return rendList;
	}

	public int lentCount(String userId) throws SQLException {
		conn = DBUtil.getConnection();

		String sql = "select Count(*) from LENDING_TABLE where " + " USER_ID = ? "
				+ " and BOOK_RETURN_DATE = TO_DATE('11111111','YYYY-MM-DD')";

		ps = conn.prepareStatement(sql);

		ps.setString(1, userId);

		rs = ps.executeQuery();

		int count = 0;
		if (rs.next()) {
			count = rs.getInt(1);
		}

		rs.close();
		ps.close();
		conn.close();

		return count;
	}

	public int lentOverDueCount(String userId) throws SQLException {
		conn = DBUtil.getConnection();

		String sql = "select Count(*) from lending_table where " + " user_id = ? "
				+ " AND (((BOOK_RETURN_DATE = TO_DATE('11111111','YYYY-MM-DD')) AND (sysdate - BOOK_RETURN_EXPECTED_DATE > 0)) OR  (BOOK_RETURN_DATE+BOOK_OVERDUE_COUNT > sysdate))";

		ps = conn.prepareStatement(sql);

		ps.setString(1, userId);

		rs = ps.executeQuery();

		int count = 0;
		if (rs.next()) {
			count = rs.getInt(1);
		}

		rs.close();
		ps.close();
		conn.close();

		return count;
	}

	public LocalDateTime lendBook(String sBookCode, String userId) throws SQLException {
		
		LocalDateTime result = null;
		conn = DBUtil.getConnection();
		
		String sql = "insert into lending_table "
//					+ " (USER_ID,BOOK_CODE,BOOK_LENDING_DATE,BOOK_RETURN_DATE,BOOK_RETURN_EXPECTED_DATE,BOOK_OVERDUE_COUNT,BOOK_EXTEND_YN) "
					+ " values (lending_seq.NEXTVAL,? , ?, sysdate, TO_DATE('11111111', 'YYYY-MM-DD'), sysdate+7, 0, 'N')";
		
		ps = conn.prepareStatement(sql);
		
		ps.setString(1, userId);
		ps.setString(2, sBookCode);
		
		int res = ps.executeUpdate();
		
		if(res > 0) {
			
			sql = "select book_return_expected_date from lending_table where user_id = ? AND book_code = ?";
			
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, userId);
			ps.setString(2, sBookCode);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				result = rs.getTimestamp("book_return_expected_date").toLocalDateTime();
			}
		}
		
		rs.close();
		ps.close();
		conn.close();
		
		return result;
	}

	// �쑀�젣�슧
	public ArrayList<Lending> BOOK_RETURN(String userId) throws SQLException {
		Lending rendingInfo = null;
		conn = DBUtil.getConnection();
		String sql = "select * from lending_table where user_id = ?";
		ps = conn.prepareStatement(sql);
		ps.setString(1, userId);
		rs = ps.executeQuery();
		
		ArrayList<Lending> rendList = new ArrayList<Lending>();
		
		while (rs.next()) {
			rendingInfo = new Lending(rs.getInt("lending_no"), rs.getString("user_id"), rs.getString("book_code"),
					rs.getTimestamp("book_lending_date").toLocalDateTime(),
					rs.getTimestamp("book_return_date").toLocalDateTime(),
					rs.getTimestamp("book_return_expected_date").toLocalDateTime(), rs.getInt("book_overdue_count"),
					rs.getString("book_extend_yn"));
			rendList.add(rendingInfo);
		}
		rs.close();
		ps.close();
		conn.close();
		
		return rendList;
	}

//	public boolean returnBook(String book_code) {
//		try {
//			conn = DBUtil.getConnection();
//			String sql = "UPDATE lending_table SET book_return_date = ? WHERE book_code = ?";
//			ps = conn.prepareStatement(sql);
//			ps.setString(1, "1111-11-11");
//			ps.setString(2, book_code);
//			int rowsAffected = ps.executeUpdate();
//			return rowsAffected > 0;
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return false;
//		} finally {
//			try {
//				if (ps != null)
//					ps.close();
//				if (conn != null)
//					conn.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//	}


public static boolean returnBook1(String bookCode) {
	    Connection conn = null;
	    PreparedStatement ps = null;
	    try {
	        conn = DBUtil.getConnection(); // DB 연결을 가져옴
	        String sql = "UPDATE LENDING_TABLE SET BOOK_RETURN_DATE = SYSDATE WHERE BOOK_RETURN_DATE = TO_DATE('1111-11-11', 'YYYY-MM-DD') AND BOOK_CODE = ?";
	        ps = conn.prepareStatement(sql);
	        ps.setString(1, bookCode); // 책 코드를 바인딩
	        int rowsAffected = ps.executeUpdate(); // 업데이트 수행
	        return rowsAffected > 0; // 업데이트가 성공적으로 이루어졌는지 여부 반환
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false; // 실패 시 false 반환
	    } finally {
	        // 사용한 리소스 해제
	        try {
	            if (ps != null) ps.close();
	            if (conn != null) conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}

	public boolean updateReturnDate(String book_code) {
		try {
			conn = DBUtil.getConnection();
			String sql = "UPDATE lending_table SET book_return_date = ? WHERE book_code = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, "1111-11-11");
			ps.setString(2, book_code);
			int rowsAffected = ps.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public ArrayList<Lending> All() throws SQLException {
		Lending lendingInfo = null;
		conn = DBUtil.getConnection();

		String sql = "SELECT * FROM LENDING_TABLE";

		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		rendList = new ArrayList<Lending>();
		while (rs.next()) {
			lendingInfo = new Lending(rs.getInt("lending_no"), rs.getString("user_id"), rs.getString("book_code"),
					rs.getTimestamp("book_lending_date").toLocalDateTime(),
					rs.getTimestamp("book_return_date").toLocalDateTime(),
					rs.getTimestamp("book_return_expected_date").toLocalDateTime(), rs.getInt("book_overdue_COUNT"),
					rs.getString("book_extend_yn"));
			rendList.add(lendingInfo);
		}
		rs.close();
		ps.close();
		conn.close();
		return rendList;
	}
	//--------------------- - 도운 추가
	public ArrayList<Lending> SelectAll() throws SQLException {
		Lending Lending = null;
		
		conn = DBUtil.getConnection();
		
		String sql = "select * from lending_table" ;

		
		ps = conn.prepareStatement(sql);
		

		
		rs = ps.executeQuery();
		
		
		rendList = new ArrayList<Lending>();
		while(rs.next()) {
			Lending = new Lending(
					rs.getInt("lending_no"),
					rs.getString("user_id"),
					rs.getString("book_code"),
					rs.getTimestamp("book_lending_date").toLocalDateTime(),
					rs.getTimestamp("book_return_date").toLocalDateTime(),
					rs.getTimestamp("book_return_expected_date").toLocalDateTime(),
					rs.getInt("book_overdue_count"),
					rs.getString("book_extend_yn")
					);
			
			rendList.add(Lending);
		}
		System.out.println("전체 테이블 출력");

		
		rs.close();
		ps.close();
		conn.close();
		
		return rendList;
	}
	
	public ArrayList<Lending> EditLending(Lending Lending) throws SQLException {
		conn = DBUtil.getConnection();
		
		String sql = "UPDATE LENDING_TABLE SET USER_ID = ?, BOOK_CODE = ?, BOOK_LENDING_DATE = ?, BOOK_RETURN_DATE = ?, BOOK_RETURN_EXPECTED_DATE = ?, BOOK_OVERDUE_COUNT = ?,"
				+ " BOOK_EXTEND_YN = ? where LENDING_NO = ?" ;
		ps = conn.prepareStatement(sql);
		ps.setString(1, Lending.getUser_id());
		ps.setString(2, Lending.getBook_code());
		ps.setTimestamp(3, Timestamp.valueOf(Lending.getBook_lending_date()));
		ps.setTimestamp(4, Timestamp.valueOf(Lending.getBook_return_date()));
		ps.setTimestamp(5, Timestamp.valueOf(Lending.getBook_return_expected_date()));
		ps.setInt(6, Lending.getBook_overdue_count());
		ps.setString(7, Lending.getBook_extend_yn());
		ps.setInt(8, Lending.getRending_no());
		
		

		cnt = ps.executeUpdate();
		System.out.println("수정 완료");
		return SelectAll();
	}
	public  ArrayList<Lending> DelLending(int lending_no) throws SQLException {
		conn = DBUtil.getConnection();
		
		String sql = "delete from Lending_table where lending_no = ?"; 
		
		ps = conn.prepareStatement(sql);
		ps.setInt(1, lending_no);
		

		cnt = ps.executeUpdate();
		System.out.println("삭제 완료");
		return SelectAll();
	}

	public ArrayList<Lending> insertLending(Lending Lending) throws SQLException {
		conn = DBUtil.getConnection();
		
		String sql = "insert into lending_table values (?,?,?,?,?,?,?,?)"; 
		
		ps = conn.prepareStatement(sql);
		ps.setString(1, Lending.getUser_id());
		ps.setString(2, Lending.getBook_code());
		ps.setTimestamp(3, Timestamp.valueOf(Lending.getBook_lending_date()));
		ps.setTimestamp(4, Timestamp.valueOf(Lending.getBook_return_date()));
		ps.setTimestamp(5, Timestamp.valueOf(Lending.getBook_return_expected_date()));
		ps.setInt(6, Lending.getBook_overdue_count());
		ps.setString(7, Lending.getBook_extend_yn());
		ps.setInt(8, Lending.getRending_no());
		cnt = ps.executeUpdate();
		System.out.println("추가 완료");
		
		return SelectAll();
	
	}
	//----------------
	
	public static Map<String, String> getBookImages() {
	    Map<String, String> bookImages = new HashMap<>();

	    String sql = "SELECT BOOK_CODE, BOOK_IMG FROM BOOK_TABLE";

	    try (Connection conn = DBUtil.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {

	        while (rs.next()) {
	            String bookCode = rs.getString("BOOK_CODE");
	            String bookImg = rs.getString("BOOK_IMG");
	            bookImages.put(bookCode, "./img/" + bookImg); // 책 코드를 키로 사용하여 이미지 경로 설정
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return bookImages;
	}
	
	public boolean updateExtendDate(String book_code) {
	    try {
	        conn = DBUtil.getConnection();
	        // SQL 쿼리: 현재 대출 중이고 연장 여부가 'N'인 경우 반납 예정 일자를 7일 연장하고 연장 여부를 'Y'로 업데이트
	        String sql = "UPDATE lending_table " +
	                     "SET book_return_expected_date = book_return_expected_date + 7, book_extend_yn = 'Y' " +
	                     "WHERE book_code = ? AND book_return_date = TO_DATE('1111-11-11', 'YYYY-MM-DD') AND book_extend_yn = 'N'";
	        ps = conn.prepareStatement(sql);
	        ps.setString(1, book_code);
	        int rowsAffected = ps.executeUpdate();
	        return rowsAffected > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    } finally {
	        try {
	            if (ps != null) ps.close();
	            if (conn != null) conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    
	}
//	public int calculateOverdueDays(String book_code, String userId) {
//		int result = 0;
//	    try {
//	        Connection conn = DBUtil.getConnection(); // DBUtil.getConnection() 호출
//	        String sql = "SELECT COUNT(*) FROM LENDING_TABLE WHERE user_id = ? AND BOOK_CODE = ? AND BOOK_RETURN_EXPECTED_DATE < sysdate AND book_return_date = TO_DATE('1111-11-11', 'YYYY-MM-DD')";
//	        PreparedStatement ps = conn.prepareStatement(sql);
//	        ps.setString(1, userId);
//	        ps.setString(2, book_code);
//	        ResultSet rs = ps.executeQuery();
//
//	        
//	        if (rs.next()) {
//	        	result = rs.getInt(1);
//	        }
//	        
//	        rs.close();
//	        ps.close();
//	        conn.close();
//	        
//	        
//	    } catch (SQLException e) {
//	        e.printStackTrace();
//	    }
//	    return result;
//	}
	
//	public int calculateOverdueDaysCount(String book_code, String userId) {
//		int result = 0;
//	    try {
//	        Connection conn = DBUtil.getConnection(); // DBUtil.getConnection() 호출
//	        String sql = "SELECT TRUNC(sysdate - BOOK_RETURN_EXPECTED_DATE) FROM LENDING_TABLE WHERE user_id = ? AND BOOK_CODE = ? AND book_return_date = TO_DATE('1111-11-11', 'YYYY-MM-DD')";
//	        PreparedStatement ps = conn.prepareStatement(sql);
//	        ps.setString(1, userId);
//	        ps.setString(2, book_code);
//	        ResultSet rs = ps.executeQuery();
//
//	        
//	        if (rs.next()) {
//	        	int overdueCount = rs.getInt(1);
//	        	
//	        	if(overdueCount > 0) {
//	        		result = overdueCount;
//	        	}
//	        }
//	        
//	        rs.close();
//	        ps.close();
//	        conn.close();
//	        
//	    } catch (SQLException e) {
//	        e.printStackTrace();
//	    }
//	    return result;
//	}

	// 연체 일수
	public void calup(String book_code, long count) {
	    try {
	        Connection conn = DBUtil.getConnection(); // DBUtil.getConnection() 호출
	        String sql = "UPDATE LENDING_TABLE SET BOOK_OVERDUE_COUNT = ? WHERE BOOK_CODE = ?";
	        PreparedStatement ps = conn.prepareStatement(sql);
	        ps.setLong(1, count);
	        ps.setString(2, book_code);
	        int res = ps.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	
	

    public boolean updateOverdueDays(String book_code, int overdueDays) {
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false); // 수동 커밋 모드로 설정
            System.out.println("연체일수 업데이트 시도: 책 코드 = " + book_code + ", 연체일수 = " + overdueDays);
            String sql = "UPDATE LENDING_TABLE SET BOOK_OVERDUE_COUNT = ? WHERE BOOK_CODE = ? AND book_return_date = TO_DATE('1111-11-11', 'YYYY-MM-DD')";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, overdueDays);
            ps.setString(2, book_code);
            int rowsAffected = ps.executeUpdate();
            System.out.println("업데이트된 행 수: " + rowsAffected);
            conn.commit(); // 수동 커밋
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (conn != null) conn.rollback(); // 오류 발생 시 롤백
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            return false;
        } finally {
            try {
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
    }
    public String getBookTitle(String bookCode) {
        String bookTitle = null;
        try {
            conn = DBUtil.getConnection(); 
            String sql = "SELECT BOOK_TITLE FROM BOOK_TABLE WHERE BOOK_CODE = ?"; // 책 코드에 해당하는 책 제목 조회 쿼리
            ps = conn.prepareStatement(sql);
            ps.setString(1, bookCode); // 책 코드 바인딩
            rs = ps.executeQuery(); 
            if (rs.next()) {
                bookTitle = rs.getString("BOOK_TITLE"); // 결과에서 책 제목 추출
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return bookTitle;
    }

//	public int calculateOverdueDays2(String book_code, String userId, int rending_no) {
//		int result = 0;
//	    try {
//	        Connection conn = DBUtil.getConnection(); // DBUtil.getConnection() 호출
//	        String sql = "SELECT COUNT(*) FROM LENDING_TABLE "
//	        		+ "WHERE user_id = ? AND BOOK_CODE = ? AND LENDING_NO = ? "
//	        		+ "AND book_return_date+BOOK_OVERDUE_COUNT > sysdate ";
//	        PreparedStatement ps = conn.prepareStatement(sql);
//	        ps.setString(1, userId);
//	        ps.setString(2, book_code);
//	        ps.setInt(3, rending_no);
//	        ResultSet rs = ps.executeQuery();
//
//	        
//	        if (rs.next()) {
//	        	result = rs.getInt(1);
//	        }
//	    } catch (SQLException e) {
//	        e.printStackTrace();
//	    }
//	    return result;
//	}



}
