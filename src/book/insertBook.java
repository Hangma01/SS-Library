package book;

import java.time.LocalDateTime;

public class insertBook {
	private String book_Title;					// 제목
	private String book_Type;					// 장르
	private String book_Author;					// 저자
	private String book_Img;					// 이미지
	private String book_Publisher;  			// 출판사
	private LocalDateTime book_Insert_Date;		// 도서 등록일
	private LocalDateTime book_Publish_Date;	// 출판일
	private String book_ClaimingSymbol;		// 청구 기호 100(타입).202401(출판 년/월).1000001(도서코드)
	
	public insertBook(String book_Title, String book_Type, String book_Author, String book_Img, String book_Publisher,
			LocalDateTime book_Insert_Date, LocalDateTime book_Publish_Date, String book_ClaimingSymbol) {
		
		this.book_Title = book_Title;
		this.book_Type = book_Type;
		this.book_Author = book_Author;
		this.book_Img = book_Img;
		this.book_Publisher = book_Publisher;
		this.book_Insert_Date = book_Insert_Date;
		this.book_Publish_Date = book_Publish_Date;
		this.book_ClaimingSymbol = book_ClaimingSymbol;
	}
	public String getBook_Title() {
		return book_Title;
	}
	public void setBook_Title(String book_Title) {
		this.book_Title = book_Title;
	}
	public String getBook_Type() {
		return book_Type;
	}
	public void setBook_Type(String book_Type) {
		this.book_Type = book_Type;
	}
	public String getBook_Author() {
		return book_Author;
	}
	public void setBook_Author(String book_Author) {
		this.book_Author = book_Author;
	}
	public String getBook_Img() {
		return book_Img;
	}
	public void setBook_Img(String book_Img) {
		this.book_Img = book_Img;
	}
	public String getBook_Publisher() {
		return book_Publisher;
	}
	public void setBook_Publisher(String book_Publisher) {
		this.book_Publisher = book_Publisher;
	}
	public LocalDateTime getBook_Insert_Date() {
		return book_Insert_Date;
	}
	public void setBook_Insert_Date(LocalDateTime book_Insert_Date) {
		this.book_Insert_Date = book_Insert_Date;
	}
	public LocalDateTime getBook_Publish_Date() {
		return book_Publish_Date;
	}
	public void setBook_Publish_Date(LocalDateTime book_Publish_Date) {
		this.book_Publish_Date = book_Publish_Date;
	}
	public String getBook_ClaimingSymbol() {
		return book_ClaimingSymbol;
	}
	public void setBook_ClaimingSymbol(String book_ClaimingSymbol) {
		this.book_ClaimingSymbol = book_ClaimingSymbol;
	}
	@Override
	public String toString() {
		return "insertBook [book_Title=" + book_Title + ", book_Type=" + book_Type + ", book_Author=" + book_Author
				+ ", book_Img=" + book_Img + ", book_Publisher=" + book_Publisher + ", book_Insert_Date="
				+ book_Insert_Date + ", book_Publish_Date=" + book_Publish_Date + ", book_ClaimingSymbol="
				+ book_ClaimingSymbol + "]";
	}
	
	
}
