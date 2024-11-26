package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import dao.UserDAO;
import user.User;

public class UserController { // 240531 16:30정일

	private static UserDAO dao = new UserDAO();

	public boolean join(User user) throws Exception {

		return dao.saveUserData(user);
	}

	public User login(String USER_ID, String USER_PW) throws Exception {

		// return타입 boolean -> User //변경 0531정일
		return dao.login(USER_ID, USER_PW);
	}

	// --------------------------------------------------도운 추가 0604
	public static ArrayList<User> select() throws SQLException {
		return dao.SelectAll();
	}

	public static ArrayList<User> EditUser(User User) throws SQLException {
		return dao.EditUser(User);
	}

	public static ArrayList<User> DelUser(String user_id) throws SQLException {
		return dao.DelUser(user_id);
	}
}
