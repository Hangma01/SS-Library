package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JTextField;

import book.*;
import dao.BookDAO;
import lending.Lending;

public class BookController { // 240601 10:00

	private static BookDAO bookDao = new BookDAO();

	public Book select(String sBookCode) throws SQLException {

		return bookDao.select(sBookCode);
	}

	public ArrayList<Book> Search(String searchText, String searchType) throws SQLException {

		boolean blankCheck = searchText.isBlank();

		if (!blankCheck) {
			searchText = removeTextEmpty(searchText);
		}

		return bookDao.Search(searchText, searchType);

	}

	public int[] searchMatchCount(String searchText, String searchType) throws SQLException {

		searchText = removeTextEmpty(searchText);

		return bookDao.searchMatchCount(searchText, searchType);
	}

	public ArrayList<Book> DeatilSearch(String[] searchHeader, String[] searchText) throws SQLException {

		return bookDao.DetailSearch(DetailSearchText(searchHeader, searchText));
	}

	public int[] detailSearchMatchCount(String[] searchHeader, String[] searchText) throws SQLException {

		return bookDao.detailSearchMatchCount(DetailSearchText(searchHeader, searchText));
	}


// 20240606 안효준 수정
	public String DetailSearchText(String[] searchHeader, String[] searchText) {
		String pbDate = "book_pbDate";
		String Title = "book_title";
		String search = "";
		int HeaderLen = searchHeader.length;

		for (int i = 0; i < HeaderLen; i++) {

//			if(searchHeader[i].equals(Title)) {
//				
//				searchText[i] = removeTextEmpty(searchText[i]);
//				
//				search += (" replace(" + searchHeader[i] + ", ' ')" + " LIKE '%" + searchText[i] + "%' ");
//			}else if(searchHeader[i].equals(pbDate)) {
//				search += ("TO_CHAR(BOOK_PUBLISH_DATE,'YYYY') between '" + searchText[i].substring(0,4) + "' and '" + searchText[i].substring(4,8) + "' ");
//			}
//			else {
//				search += (searchHeader[i] + " = '" + searchText[i] + "' ");
//			}



			
			if (searchHeader[i].equals(pbDate)) {

				search += ("TO_CHAR(BOOK_PUBLISH_DATE,'YYYY') between '" + searchText[i].substring(0, 4) + "' and '"
						+ searchText[i].substring(4, 8) + "' ");
			} else {
				
				boolean blankCheck = searchText[i].isBlank();

				if (!blankCheck) {
					searchText[i] = removeTextEmpty(searchText[i]);
				}


				search += (" replace(" + searchHeader[i] + ", ' ')" + " LIKE '%" + searchText[i] + "%' ");
			}

			if (i < (HeaderLen - 1)) {

				search += "AND ";
			}
		}

		return search;
	}

	public String removeTextEmpty(String text) {

		String reviseText = text.replace(" ", "").trim();
		System.out.println(reviseText);

		return reviseText;
	}

//	----------------------------------------------------정일추가0601
	public ArrayList<Book> recmdBest5() throws Exception {
		return bookDao.recmdBest5();
	}

	public ArrayList<Book> recmdNewest5() throws Exception {
		return bookDao.recmdNewest5();
	}

//	---------------------------------------------------- 0604 도운
	public static ArrayList<Book> select() throws SQLException {
		return bookDao.SelectAll();
	}

	public static ArrayList<Book> EditBook(Book Book) throws SQLException {
		return bookDao.EditBook(Book);
	}

	public static ArrayList<Book> DelBook(String book_code) throws SQLException {
		return bookDao.DelBook(book_code);
	}

	public static ArrayList<insertBook> insertBook(insertBook insertBook) throws SQLException {
		return bookDao.insertBook(insertBook);

	}
}
