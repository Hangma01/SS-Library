package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.DBUtil;
import user.User;

public class UserDAO { // 240531 16:30정일

	private static Connection conn;
	private static PreparedStatement pt;
	private static ResultSet rs;

	int cnt;
	ArrayList<User> userList = null;

	public boolean saveUserData(User user) throws Exception {

		System.out.println("UserDAO안에 join() 실행");
		System.out.println(user);
		conn = DBUtil.getConnection();

		String sql = "insert into USER_TABLE ";
		sql += " values(?,?,?,?,?,?,?,?,?)";

		pt = conn.prepareStatement(sql);
		pt.setString(1, user.getUSER_ID());
		pt.setString(2, user.getUSER_PW());
		pt.setString(3, user.getUSER_NAME());
		pt.setString(4, user.getUSER_SEX());
		pt.setString(5, user.getUSER_PHONE());
		pt.setString(6, user.getUSER_ADRESS());
		pt.setString(7, user.getUSER_EMAIL());
		pt.setString(8, user.getMANAGER_YN());
		pt.setString(9, user.getUSER_SECESSIO_YN());
		int result = pt.executeUpdate();

		pt.close();
		conn.close();

		return result > 0 ? true : false;
	}

	public User login(String userId, String userPw) throws Exception {
		conn = DBUtil.getConnection();
		User temp = new User();// 0531 정일 추가
		temp.setUSER_ID("");// 0531 정일 추가
		temp.setMANAGER_YN("n");// 0531 정일 추가
		temp.setUSER_SECESSIO_YN("n");

		String sql = "SELECT * FROM USER_TABLE WHERE USER_ID = ? AND USER_PW = ?";
		pt = conn.prepareStatement(sql);
		pt.setString(1, userId);
		pt.setString(2, userPw);

		rs = pt.executeQuery();
		boolean isValidUser = rs.next();

		if (isValidUser) {// 0531 정일 추가
			temp.setUSER_ID(rs.getString("user_id"));// 0531 정일 추가
			temp.setUSER_PW(rs.getString("user_pw"));// 0531 정일 추가
			temp.setMANAGER_YN(rs.getString("manager_yn"));// 0531 정일 추가
			temp.setUSER_SECESSIO_YN(rs.getString("USER_SECESSIO_YN"));
		} // 0531 정일 추가

		rs.close();
		pt.close();
		conn.close();

		return temp; // return타입 boolen -> User //변경 0531정일
	}

	public static int update(User user) throws Exception {
		Connection conn = null;
		PreparedStatement pt = null;
		int result = 0;

		try {
			conn = DBUtil.getConnection();

			String sql = "UPDATE USER_TABLE ";
			sql += "SET USER_PW = ?, USER_NAME = ?, USER_PHONE = ?, USER_ADRESS = ?, USER_EMAIL = ?, ";
			sql += " MANAGER_YN = ?, USER_SECESSIO_YN = ? ";
			sql += "WHERE USER_ID = ?";

			pt = conn.prepareStatement(sql);

			pt.setString(1, user.getUSER_PW());
			pt.setString(2, user.getUSER_NAME());
			pt.setString(3, user.getUSER_PHONE());
			pt.setString(4, user.getUSER_ADRESS());
			pt.setString(5, user.getUSER_EMAIL());
			pt.setString(6, user.getMANAGER_YN());
			pt.setString(7, user.getUSER_SECESSIO_YN());
			pt.setString(8, user.getUSER_ID());

			result = pt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pt != null)
				pt.close();
			if (conn != null)
				conn.close();
		}

		return result;
	}

	public static int reviseUser(User user) throws Exception {
		Connection conn = null;
		PreparedStatement pt = null;
		int result = 0;

		try {
			conn = DBUtil.getConnection();

			String sql = "UPDATE USER_TABLE ";
			sql += "SET USER_PW = ?, USER_NAME = ?, USER_PHONE = ?, USER_ADRESS = ?, USER_EMAIL = ? ";
			sql += "WHERE USER_ID = ?";

			pt = conn.prepareStatement(sql);

			pt.setString(1, user.getUSER_PW());
			pt.setString(2, user.getUSER_NAME());
			pt.setString(3, user.getUSER_PHONE());
			pt.setString(4, user.getUSER_ADRESS());
			pt.setString(5, user.getUSER_EMAIL());
			pt.setString(6, user.getUSER_ID());

			result = pt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pt != null)
				pt.close();
			if (conn != null)
				conn.close();
		}

		return result;
	}

	// 유저 정보 전체 저장
	public static User getUserById(String userId) throws SQLException {

		Connection conn = null;
		PreparedStatement pt = null;
		ResultSet rs = null;
		User user = null;

		try {
			conn = DBUtil.getConnection();

			String sql = "select * FROM user_table WHERE USER_ID = ?";

			pt = conn.prepareStatement(sql);

			pt.setString(1, userId);

			rs = pt.executeQuery();

			if (rs.next()) {
				String USER_ID = rs.getString("USER_ID");
				String USER_PW = rs.getString("USER_PW");
				String USER_NAME = rs.getString("USER_NAME");
				String USER_SEX = rs.getString("USER_SEX");
				String USER_PHONE = rs.getString("USER_PHONE");
				String USER_ADDRESS = rs.getString("USER_ADRESS");
				String USER_EMAIL = rs.getString("USER_EMAIL");
				String USER_MANAGER_YN = rs.getString("MANAGER_YN");
				String USER_SECESSIO_YN = rs.getString("USER_SECESSIO_YN");

				user = new User(USER_ID, USER_PW, USER_NAME, USER_SEX, USER_PHONE, USER_ADDRESS, USER_EMAIL,
						USER_MANAGER_YN, USER_SECESSIO_YN);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pt != null)
				pt.close();
			if (conn != null)
				conn.close();
		}

		return user;

	}

	public static int delete(User userID) {
		conn = DBUtil.getConnection();
		int result = 0;

		try {

			String sql = "delete from USER_TABLE ";
			sql += " where USER_ID = ?";

			pt = conn.prepareStatement(sql);

			pt.setString(1, userID.getUSER_ID());

			result = pt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// 아이디 찾기 메서드
	public String findID(String name, String phone, String password) throws Exception {
		Connection conn = DBUtil.getConnection();
		String sql = "SELECT USER_ID, USER_SECESSIO_YN FROM USER_TABLE WHERE USER_NAME = ? AND USER_PHONE = ? AND USER_PW = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, name);
		pstmt.setString(2, phone);
		pstmt.setString(3, password);

		ResultSet rs = pstmt.executeQuery();

		if (rs.next()) {
			String userId = rs.getString("USER_ID");
			String userSecessioYn = rs.getString("USER_SECESSIO_YN");

			if ("Y".equalsIgnoreCase(userSecessioYn)) {
				return "탈퇴한 회원입니다";
			} else {
				return userId;
			}
		}
		return null; // 일치하는 아이디가 없음
	}

	public String findPW(String id, String name, String phone) throws Exception {
		Connection conn = DBUtil.getConnection();
		String sql = "SELECT USER_PW, USER_SECESSIO_YN FROM USER_TABLE WHERE USER_ID = ? AND USER_NAME = ? AND USER_PHONE = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, id);
		pstmt.setString(2, name);
		pstmt.setString(3, phone);
		ResultSet rs = pstmt.executeQuery();

		if (rs.next()) {
			if (rs.getString("USER_SECESSIO_YN").equalsIgnoreCase("Y")) {
				return "탈퇴한회원";
			}
			return rs.getString("USER_PW");
		}
		return null; // 일치하는 비밀번호가 없음
	}

	public boolean isUserIdDuplicate(String userId) throws Exception {
		conn = DBUtil.getConnection();

		String sql = "SELECT COUNT(*) FROM USER_TABLE WHERE USER_ID = ?";
		pt = conn.prepareStatement(sql);
		pt.setString(1, userId);

		rs = pt.executeQuery();
		rs.next();
		int count = rs.getInt(1);

		rs.close();
		pt.close();
		conn.close();

		// COUNT(*)의 결과가 1 이상이면 중복된 아이디가 있음
		return count > 0;
	}

	public boolean isPhoneNumberDuplicate(String phoneNumber) throws Exception {
		conn = DBUtil.getConnection();

		String sql = "SELECT COUNT(*) FROM USER_TABLE WHERE USER_PHONE = ?";
		pt = conn.prepareStatement(sql);
		pt.setString(1, phoneNumber);

		rs = pt.executeQuery();
		rs.next();
		int count = rs.getInt(1);

		rs.close();
		pt.close();
		conn.close();

		// COUNT(*)의 결과가 1 이상이면 중복된 휴대번호가 있음
		return count > 0;
	}

	public boolean isEmailDuplicate(String email) throws Exception {
		conn = DBUtil.getConnection();

		String sql = "SELECT COUNT(*) FROM USER_TABLE WHERE USER_EMAIL = ?";
		pt = conn.prepareStatement(sql);
		pt.setString(1, email);

		rs = pt.executeQuery();
		rs.next();
		int count = rs.getInt(1);

		rs.close();
		pt.close();
		conn.close();

		// COUNT(*)의 결과가 1 이상이면 중복된 이메일이 있음
		return count > 0;
	}

	public ArrayList<User> SelectAll() throws SQLException {
		User user = null;

		conn = DBUtil.getConnection();

		String sql = "select * from user_table";

		pt = conn.prepareStatement(sql);

		rs = pt.executeQuery();

		userList = new ArrayList<User>();
		while (rs.next()) {
			user = new User(rs.getString("user_id"), rs.getString("user_pw"), rs.getString("user_name"),
					rs.getString("user_sex"), rs.getString("user_phone"), rs.getString("user_adress"),
					rs.getString("user_email"), rs.getString("manager_yn"), rs.getString("user_secessio_yn"));

			userList.add(user);
		}
		System.out.println("전체 테이블 출력");

		rs.close();
		pt.close();
		conn.close();

		return userList;
	}

	public ArrayList<User> EditUser(User User) throws SQLException {
		conn = DBUtil.getConnection();

		String sql = "UPDATE user_table SET user_pw = ?, user_name = ?,user_sex = ?,user_phone = ?,user_adress = ?,user_email = ?,manager_yn = ?,USER_SECESSIO_YN = ? where user_id = ?";
		System.out.println(User.getUSER_ID() + " " + User.getUSER_PW() + " " + User.getUSER_NAME() + " "
				+ User.getUSER_SEX() + " " + User.getUSER_PHONE() + " " + User.getUSER_ADRESS() + " "
				+ User.getUSER_EMAIL() + " " + User.getMANAGER_YN() + " " + User.getUSER_SECESSIO_YN());

		pt = conn.prepareStatement(sql);
		pt.setString(1, User.getUSER_PW());
		pt.setString(2, User.getUSER_NAME());
		pt.setString(3, User.getUSER_SEX());
		pt.setString(4, User.getUSER_PHONE());
		pt.setString(5, User.getUSER_ADRESS());
		pt.setString(6, User.getUSER_EMAIL());
		pt.setString(7, User.getMANAGER_YN());
		pt.setString(8, User.getUSER_SECESSIO_YN());
		pt.setString(9, User.getUSER_ID());

		cnt = pt.executeUpdate();
		System.out.println("수정 완료");
		return SelectAll();
	}

	public ArrayList<User> DelUser(String user_id) throws SQLException {
		conn = DBUtil.getConnection();

		String sql = "delete from lending_table where user_id= ?";

		pt = conn.prepareStatement(sql);
		pt.setString(1, user_id);

		cnt = pt.executeUpdate();

		sql = "delete from user_table where user_id= ?";

		pt = conn.prepareStatement(sql);
		pt.setString(1, user_id);

		cnt = pt.executeUpdate();
		System.out.println("삭제 완료");
		return SelectAll();
	}

	public static boolean hasReturnedBooks(String userId) throws Exception {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		boolean hasReturnedBooks = false;

		try {
			conn = DBUtil.getConnection();
			String sql = "SELECT * FROM lending_table WHERE user_id = ? AND book_return_date = TO_DATE('1111-11-11', 'YYYY-MM-DD')";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, userId);
			rs = stmt.executeQuery();

			// 반납한 책이 있는지 확인
			if (rs.next()) {
				hasReturnedBooks = true;
			}
		} finally {
			// 연결 및 리소스 정리
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}

		return hasReturnedBooks;
	}

	public static int updateUserSecessioYn(String userId) throws Exception {
		Connection conn = null;
		PreparedStatement stmt = null;
		int rowsAffected = 0;

		try {
			conn = DBUtil.getConnection();
			String sql = "UPDATE user_table SET user_secessio_yn = 'Y' WHERE user_id = ? AND user_secessio_yn = 'N'";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, userId);
			rowsAffected = stmt.executeUpdate();
		} finally {
			// 연결 및 리소스 정리
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}

		return rowsAffected;
	}

	public static boolean validatePassword(String userId, String password) throws Exception {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		boolean isValid = false;

		try {
			conn = DBUtil.getConnection();
			String sql = "SELECT * FROM user_table WHERE user_ID = ? AND user_PW = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, userId);
			stmt.setString(2, password);
			rs = stmt.executeQuery();

			if (rs.next()) {
				isValid = true;
			}
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}

		return isValid;
	}
}
