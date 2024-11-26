package user;

public class UserLogged { //240530 18:00
	
//	private static String userId;
	private static String userId="";
	private static boolean isAdmin;
	private static User user;

	public static String getUserId() {
		return userId;
	}
	public static void setUserId(String userId) {
		UserLogged.userId = userId;
	}
	public static boolean isAdmin() {
		return isAdmin;
	}
	public static void setAdmin(boolean isAdmin) {
		UserLogged.isAdmin = isAdmin;
	}
	
	public static User getUser() {
		return user;
	}
	public static void setUser(User user) {
		UserLogged.user = user;
	}
	
	
	
}
