package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import book.BookDetail;


public class BookDetailDAO { //240531 15:00
	
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	public BookDetail select(String sBookCode) throws SQLException {
		BookDetail bookDetail = null;
		
		conn = DBUtil.getConnection();
		
		String sql = "select * from book_detail_table "
					+ "where book_code = " 
					+ sBookCode;
		
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		
		if(rs.next()) {
			bookDetail = new BookDetail(rs.getString("book_Code"), 
					rs.getString("book_Introduce"),
					rs.getString("book_AuthorIntroduce"),
					rs.getString("book_Review")
					);
		}
	
		
		rs.close();
		ps.close();
		conn.close();
		
		return bookDetail;
	}

}
