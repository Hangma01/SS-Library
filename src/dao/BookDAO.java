package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import dao.DBUtil;
import book.*;
import lending.Lending;

public class BookDAO { // 240601 10:00

	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	ArrayList<Book> bookList = null;
	ArrayList<Lending> rendList = null;
	ArrayList<insertBook> insertBookList = null;
	int cnt;
	public ArrayList<Book> Search(String searchText, String searchType) throws SQLException {
		Book book = null;

		conn = DBUtil.getConnection();

		String sql = "select * from book_table " + "where replace(" + searchType + ", ' ')" + " like '%" + searchText
				+ "%' order by book_title, book_author";

		ps = conn.prepareStatement(sql);

		rs = ps.executeQuery();

		bookList = new ArrayList<Book>();
		while (rs.next()) {
			book = new Book(rs.getString("book_Code"), rs.getString("book_Title"), rs.getString("book_Type"),
					rs.getString("book_Author"), rs.getString("book_Img"), rs.getString("book_Publisher"),
					rs.getTimestamp("book_Insert_Date").toLocalDateTime(),
					rs.getTimestamp("book_Publish_Date").toLocalDateTime(), rs.getString("book_ClaimingSymbol"));
//			System.out.println("1111" + book);
			bookList.add(book);
		}

		sql = "select * from rending_date";

		rs.close();
		ps.close();
		conn.close();

		return bookList;
	}

	public int[] searchMatchCount(String searchText, String searchType) throws SQLException {

		conn = DBUtil.getConnection();

		String sql = "select count(*) from book_table " + "where replace(" + searchType + ", ' ')" + " like '%"
				+ searchText + "%' group by book_Author, book_title order by book_title, book_author";

		ps = conn.prepareStatement(sql);

		rs = ps.executeQuery();
		int bookMatchTotalCount = 0;
		while (rs.next()) {
			bookMatchTotalCount += rs.getInt(1);

		}
		System.out.println("bookMatchTotalCount" + bookMatchTotalCount);

		rs = ps.executeQuery();

		int[] bookMatchTitleCount = new int[bookMatchTotalCount];
		int count = 0;

		while (rs.next()) {
			bookMatchTitleCount[count] = rs.getInt(1);

			count++;
		}

		rs.close();
		ps.close();
		conn.close();

		return bookMatchTitleCount;
	}

	public ArrayList<Book> DetailSearch(String search) throws SQLException {

		Book book = null;

		conn = DBUtil.getConnection();

		String sql = "select * from book_table where " + search + " order by book_title, book_author";
//		System.out.println(sql);
		ps = conn.prepareStatement(sql);

		rs = ps.executeQuery();

		bookList = new ArrayList<Book>();
		while (rs.next()) {
			book = new Book(rs.getString("book_Code"), rs.getString("book_Title"), rs.getString("book_Type"),
					rs.getString("book_Author"), rs.getString("book_Img"), rs.getString("book_Publisher"),
					rs.getTimestamp("book_Insert_Date").toLocalDateTime(),
					rs.getTimestamp("book_Publish_Date").toLocalDateTime(), rs.getString("book_ClaimingSymbol"));
//			System.out.println("1111" + book);
			bookList.add(book);
		}

		sql = "select * from rending_date";

		rs.close();
		ps.close();
		conn.close();

		return bookList;
	}

	public int[] detailSearchMatchCount(String search) throws SQLException {
		conn = DBUtil.getConnection();

		String sql = "select Count(*) from book_table where " + search
				+ " group by book_Author, book_title order by book_title, book_author";
		ps = conn.prepareStatement(sql);

		rs = ps.executeQuery();
		int bookMatchTotalCount = 0;
		while (rs.next()) {
			bookMatchTotalCount += rs.getInt(1);

		}

		rs = ps.executeQuery();

		int[] bookMatchTitleCount = new int[bookMatchTotalCount];
		int count = 0;

		while (rs.next()) {
			bookMatchTitleCount[count] = rs.getInt(1);

			count++;
		}

		rs.close();
		ps.close();
		conn.close();

		return bookMatchTitleCount;
	}

	public Book select(String sBookCode) throws SQLException {

		Book book = null;

		conn = DBUtil.getConnection();

		String sql = "select * from book_table " + "where book_code = " + sBookCode;

		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();

		if (rs.next()) {
			book = new Book(rs.getString("book_Code"), rs.getString("book_Title"), rs.getString("book_Type"),
					rs.getString("book_Author"), rs.getString("book_Img"), rs.getString("book_Publisher"),
					rs.getTimestamp("book_Insert_Date").toLocalDateTime(),
					rs.getTimestamp("book_Publish_Date").toLocalDateTime(), rs.getString("book_ClaimingSymbol"));
		}

		rs.close();
		ps.close();
		conn.close();

		return book;

	}

//	---------------------------------------------------정일수정 0605
	public ArrayList<Book> recmdBest5() throws Exception {

		String sql = " select base.book_title, base.book_author, base.book_publish_date, base.counter, plus.book_img "
				+ " from (select * from (select book_title, book_author, book_publish_date, count(lending_no) as counter "
				+ " from lending_table l join book_table b on ( l.book_code = b. book_code ) "
				+ " group by book_title, book_author, book_publish_date  order by counter desc, book_title) where rownum <=5) base "
				+ " left join (select book_title, book_author, book_publish_date, MIN(book_img) book_img "
				+ " from book_table group by book_title, book_author, book_publish_date) plus "
				+ " on (base.book_title = plus.book_title) order by base.counter desc ";

		conn = DBUtil.getConnection();
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();

		bookList = new ArrayList<Book>();
		while (rs.next()) {
			Book temp = new Book();
			temp.setBook_Title(rs.getString("book_title"));
			temp.setBook_Author(rs.getString("book_author"));
			temp.setBook_Img(rs.getString("book_img"));
//				rs.getInt("counter") //대출 횟수
			bookList.add(temp);
		}
		rs.close();
		ps.close();
		conn.close();

		return bookList;
	}// recmdBest5()

	public ArrayList<Book> recmdNewest5() throws Exception {

		String sql = " select *from(select book_title, book_author, book_publish_date, min(book_img) as book_img from book_table "
				+ " group by book_title, book_author, book_publish_date order by book_publish_date desc) where rownum <=5 ";

		conn = DBUtil.getConnection();
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();

		bookList = new ArrayList<Book>();

		while (rs.next()) {
			Book temp = new Book();
			temp.setBook_Title(rs.getString("book_title"));
			temp.setBook_Author(rs.getString("book_author"));
			temp.setBook_Img(rs.getString("book_img"));
//				rs.getString("book_publish_date") //출판일
			bookList.add(temp);
		}
		rs.close();
		ps.close();
		conn.close();

		return bookList;
	}// recmdNewst5()

//	---------------------------------------------------
	// --------------------------------------------------- 도운 추가 0604
	public  ArrayList<Book> SelectAll() throws SQLException {
		Book Book = null;
		
		conn = DBUtil.getConnection();
		
		String sql = "select * from book_table" ;

		
		ps = conn.prepareStatement(sql);
		

		
		rs = ps.executeQuery();
		
		
		bookList = new ArrayList<Book>();
		while(rs.next()) {
			Book = new Book(
					rs.getString("book_code"),
					rs.getString("book_title"),
					rs.getString("book_type"),
					rs.getString("book_Author"),
					rs.getString("book_Img"),
					rs.getString("book_Publisher"),
					rs.getTimestamp("book_Insert_Date").toLocalDateTime(),
					rs.getTimestamp("book_Publish_Date").toLocalDateTime(),
					rs.getString("book_ClaimingSymbol")
					);
			
			bookList.add(Book);
		}
		System.out.println("전체 테이블 출력");

		
		rs.close();
		ps.close();
		conn.close();
		
		return bookList;
	}
	
	public  ArrayList<Book> EditBook(Book Book) throws SQLException {
		conn = DBUtil.getConnection();
		
		String sql = "UPDATE book_table SET Book_Title = ?, Book_Type = ?,Book_Author = ?,Book_Img = ?,Book_Publisher = ?,Book_Insert_Date = ?,"
				+ " Book_Publish_Date = ?,Book_ClaimingSymbol = ?"
				+ " where book_code = ?" ;

		ps = conn.prepareStatement(sql);
		ps.setString(1, Book.getBook_Title());
		ps.setString(2, Book.getBook_Type());
		ps.setString(3, Book.getBook_Author());
		ps.setString(4, Book.getBook_Img());
		ps.setString(5, Book.getBook_Publisher());
		ps.setTimestamp(6, Timestamp.valueOf(Book.getBook_Insert_Date()));
		ps.setTimestamp(7, Timestamp.valueOf(Book.getBook_Publish_Date()));
		ps.setString(8, Book.getBook_ClaimingSymbol());
		ps.setString(9, Book.getBook_Code());
	
		

		cnt = ps.executeUpdate();
		System.out.println("수정 완료");
		return SelectAll();
	}
	public ArrayList<Book> DelBook(String book_code) throws SQLException {
		conn = DBUtil.getConnection();
		String sql = "delete from book_detail_table where book_code = ?"; 
		
		ps = conn.prepareStatement(sql);
		ps.setString(1, book_code);
		

		cnt = ps.executeUpdate();
		
		sql = "delete from book_table where book_code = ?"; 
		
		ps = conn.prepareStatement(sql);
		ps.setString(1, book_code);
		

		cnt = ps.executeUpdate();
		System.out.println("삭제 완료");
		return SelectAll();
	}

	public ArrayList<insertBook> insertBook(insertBook insertBook) throws SQLException {
	conn = DBUtil.getConnection();
		
		String sql = "insert into book_table values (book_seq.NEXTVAL,?,?,?,?,?,?,?,?)"; 
		
		ps = conn.prepareStatement(sql);

		ps.setString(1, insertBook.getBook_Title());
		ps.setString(2, insertBook.getBook_Type());
		ps.setString(3, insertBook.getBook_Author());
		ps.setString(4, insertBook.getBook_Img());
		ps.setString(5, insertBook.getBook_Publisher());
		ps.setTimestamp(6, Timestamp.valueOf(insertBook.getBook_Insert_Date()));
		ps.setTimestamp(7, Timestamp.valueOf(insertBook.getBook_Publish_Date()));
		ps.setString(8, insertBook.getBook_ClaimingSymbol());

		cnt = ps.executeUpdate();
		System.out.println("추가 완료");
		
		return SelectAll2();
	
	}
	public  ArrayList<insertBook> SelectAll2() throws SQLException {
		insertBook insertBook = null;
		
		conn = DBUtil.getConnection();
		
		String sql = "select * from book_table" ;

		
		ps = conn.prepareStatement(sql);
		

		
		rs = ps.executeQuery();
		
		
		insertBookList = new ArrayList<insertBook>();
		while(rs.next()) {
			insertBook = new insertBook(
					rs.getString("book_title"),
					rs.getString("book_type"),
					rs.getString("book_Author"),
					rs.getString("book_Img"),
					rs.getString("book_Publisher"),
					rs.getTimestamp("book_Insert_Date").toLocalDateTime(),
					rs.getTimestamp("book_Publish_Date").toLocalDateTime(),
					rs.getString("book_ClaimingSymbol")
					);
			
			insertBookList.add(insertBook);
		}
		System.out.println("전체 테이블 출력");

		
		rs.close();
		ps.close();
		conn.close();
		
		return insertBookList;
		}
}

