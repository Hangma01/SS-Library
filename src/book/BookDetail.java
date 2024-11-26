package book;

public class BookDetail { //240531 15:00
   private String book_Code;
   private String book_Introduce;   
   private String book_Authorintroduce; 
   private String book_Review;
   
   public BookDetail() {

   }
   
   public BookDetail(String book_Code, String book_Introduce, String book_Authorintroduce, String book_Review) {
      this.book_Code = book_Code;
      this.book_Introduce = book_Introduce;
      this.book_Authorintroduce = book_Authorintroduce;
      this.book_Review = book_Review;
   }
   
   public String getBook_Code() {
      return book_Code;
   }
   
   public void setBook_Code(String book_Code) {
      this.book_Code = book_Code;
   }
   
   public String getBook_Introduce() {
      return book_Introduce;
   }
   
   public void setBook_Introduce(String book_Introduce) {
      this.book_Introduce = book_Introduce;
   }
   
   public String getBook_Authorintroduce() {
      return book_Authorintroduce;
   }
   
   public void setBook_AuthorintroduceE(String book_Authorintroduce) {
      this.book_Authorintroduce = book_Authorintroduce;
   }
   
   public String getBook_Review() {
      return book_Review;
   }
   
   public void setBook_Review(String book_Review) {
      this.book_Review = book_Review;
   }

	@Override
	public String toString() {
		return "BookDetail [book_Code=" + book_Code + ", book_Introduce=" + book_Introduce + ", book_Authorintroduce="
				+ book_Authorintroduce + ", book_Review=" + book_Review + "]";
	}
   
      
}