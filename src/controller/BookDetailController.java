package controller;

import java.sql.SQLException;

import book.BookDetail;
import dao.BookDetailDAO;

public class BookDetailController { //240531 15:00

	private BookDetailDAO bookDetailDao = new BookDetailDAO();
	
	public BookDetail select(String sBookCode) throws SQLException {
		
		return bookDetailDao.select(sBookCode);
	}

}
