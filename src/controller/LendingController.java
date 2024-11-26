package controller;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import dao.LendingDAO;
import lending.Lending;
import user.UserLogged;

public class LendingController { //240531 15:00

	private static LendingDAO lendingDao = new LendingDAO();
	
	public static ArrayList<Lending> select() throws SQLException {
		
		return lendingDao.select();
	}

	public boolean lentCount(String userId) throws SQLException {
		
		boolean result = false;
		final int maxLendCount = 3;
		
		if(lendingDao.lentCount(userId) < maxLendCount) {
			result = true;
		}
		
		return result;
	}

	public boolean lentOverDueCount(String userId) throws SQLException {
		
		boolean result = false;
		final int fixOverDutCount = 0;
	
		if(lendingDao.lentOverDueCount(userId) == fixOverDutCount) {
			result = true;
		}
		
		return result;
	}

	public String lendBook(String sBookCode) throws SQLException {
	
		String sRetrunExpeDate = null;
		
		String userId = UserLogged.getUserId();
		
		LocalDateTime returnExpeDate = lendingDao.lendBook(sBookCode, userId);
		
		if(returnExpeDate != null) {
			sRetrunExpeDate = returnExpeDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		}
		
		
		return sRetrunExpeDate;

	}
// -----------------------------------------------------------도운 추가 0604
	public static ArrayList<Lending> selectAll() throws SQLException {
		return lendingDao.SelectAll();
	}
	public static ArrayList<Lending> EditLending(Lending Lending) throws SQLException {
		return lendingDao.EditLending(Lending);
	}
	public static  ArrayList<Lending> DelLending(int lending_no) throws SQLException{
		return lendingDao.DelLending(lending_no);
	}
	public static  ArrayList<Lending> insertLending(Lending insertLending) throws SQLException {
		return lendingDao.insertLending(insertLending);

	}
}
