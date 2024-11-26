package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {
	public static Connection getConnection() {

		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String url = "jdbc:oracle:thin:@192.168.219.104:1521:xe";
			String user = "KH";
			String password = "KH";

			conn = DriverManager.getConnection(url, user, password);
//			conn.setAutoCommit(false);
			//AutoCommit을 끄면 update 후 conn.commit(); 매번 넣어줘야 함
			
			
			if (conn != null) {
//				System.out.println(conn);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
}
