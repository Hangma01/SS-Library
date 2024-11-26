package lending;

import java.time.LocalDateTime;

public class Lending { //240531 15:00

	private int rending_no;
	private String user_id;
	private String book_code;
	 private String book_title;
	private LocalDateTime book_lending_date;
	private LocalDateTime book_return_date;
	private LocalDateTime book_return_expected_date;
	private int book_overdue_count;
	private String book_extend_yn;
	
	public Lending(int rending_no, String user_id, String book_code, LocalDateTime book_lending_date,
			LocalDateTime book_return_date, LocalDateTime book_return_expected_date, int book_overdue_count,
			String book_extend_yn) {

		this.rending_no = rending_no;
		this.user_id = user_id;
		this.book_code = book_code;
		this.book_title = book_title;
		this.book_lending_date = book_lending_date;
		this.book_return_date = book_return_date;
		this.book_return_expected_date = book_return_expected_date;
		this.book_overdue_count = book_overdue_count;
		this.book_extend_yn = book_extend_yn;
	}

	public int getRending_no() {
		return rending_no;
	}

	public void setRending_no(int rending_no) {
		this.rending_no = rending_no;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getBook_code() {
		return book_code;
	}

	public void setBook_code(String book_code) {
		this.book_code = book_code;
	}

	public LocalDateTime getBook_lending_date() {
		return book_lending_date;
	}

	public void setBook_lending_date(LocalDateTime book_lending_date) {
		this.book_lending_date = book_lending_date;
	}

	public LocalDateTime getBook_return_date() {
		return book_return_date;
	}

	public void setBook_return_date(LocalDateTime book_return_date) {
		this.book_return_date = book_return_date;
	}

	public LocalDateTime getBook_return_expected_date() {
		return book_return_expected_date;
	}

	public void setBook_return_expected_date(LocalDateTime book_return_expected_date) {
		this.book_return_expected_date = book_return_expected_date;
	}

	public int getBook_overdue_count() {
		return book_overdue_count;
	}

	public void setBook_overdue_count(int book_overdue_count) {
		this.book_overdue_count = book_overdue_count;
	}

	public String getBook_extend_yn() {
		return book_extend_yn;
	}

	public void setBook_extend_yn(String book_extend_yn) {
		this.book_extend_yn = book_extend_yn;
	}

	@Override
	public String toString() {
		return "Lending [rending_no=" + rending_no + ", user_id=" + user_id + ", book_code=" + book_code
				+ ", book_lending_date=" + book_lending_date + ", book_return_date=" + book_return_date
				+ ", book_return_expected_date=" + book_return_expected_date + ", book_overdue_count=" + book_overdue_count
				+ ", book_extend_yn=" + book_extend_yn + "]";
	}
	

	
}

