package user;

public class User { //240531 15:00

	private String USER_ID;
	private String USER_PW;
	private String USER_NAME;
	private String USER_SEX;
	private String USER_PHONE;
	private String USER_ADRESS;
	private String USER_EMAIL;
	private String MANAGER_YN;
	private String USER_SECESSIO_YN;

	public User() {
		super();
	}

	public String getUSER_ID() {
		return USER_ID;
	}

	public void setUSER_ID(String uSER_ID) {
		USER_ID = uSER_ID;
	}

	public String getUSER_PW() {
		return USER_PW;
	}

	public void setUSER_PW(String uSER_PW) {
		USER_PW = uSER_PW;
	}

	public String getUSER_NAME() {
		return USER_NAME;
	}

	public void setUSER_NAME(String uSER_NAME) {
		USER_NAME = uSER_NAME;
	}

	public String getUSER_SEX() {
		return USER_SEX;
	}

	public void setUSER_SEX(String uSER_SEX) {
		USER_SEX = uSER_SEX;
	}

	public String getUSER_PHONE() {
		return USER_PHONE;
	}

	public void setUSER_PHONE(String uSER_PHONE) {
		USER_PHONE = uSER_PHONE;
	}

	public String getUSER_ADRESS() {
		return USER_ADRESS;
	}

	public void setUSER_ADRESS(String uSER_ADRESS) {
		USER_ADRESS = uSER_ADRESS;
	}

	public String getUSER_EMAIL() {
		return USER_EMAIL;
	}

	public void setUSER_EMAIL(String uSER_EMAIL) {
		USER_EMAIL = uSER_EMAIL;
	}

	public String getMANAGER_YN() {
		return MANAGER_YN;
	}

	public void setMANAGER_YN(String mANAGER_YN) {
		MANAGER_YN = mANAGER_YN;
	}

	public String getUSER_SECESSIO_YN() {
		return USER_SECESSIO_YN;
	}

	public void setUSER_SECESSIO_YN(String uSER_SECESSIO_YN) {
		USER_SECESSIO_YN = uSER_SECESSIO_YN;
	}

	@Override
	public String toString() {
		return "User [USER_ID=" + USER_ID + ", USER_PW=" + USER_PW + ", USER_NAME=" + USER_NAME + ", USER_SEX="
				+ USER_SEX + ", USER_PHONE=" + USER_PHONE + ", USER_ADRESS=" + USER_ADRESS + ", USER_EMAIL="
				+ USER_EMAIL + ", MANAGER_YN=" + MANAGER_YN + ", USER_SECESSIO_YN=" + USER_SECESSIO_YN + "]";
	}

// 회원정보 수정
	public User(String uSER_ID, String uSER_PW, String uSER_NAME, String uSER_PHONE, String uSER_ADRESS, String uSER_EMAIL) {
		super();
		USER_ID = uSER_ID;
		USER_PW = uSER_PW;
		USER_NAME = uSER_NAME;
		USER_PHONE = uSER_PHONE;
		USER_ADRESS = uSER_ADRESS;
		USER_EMAIL = uSER_EMAIL;
	}

	public User(String uSER_ID, String uSER_PW, String uSER_NAME, String uSER_SEX, String uSER_PHONE,
			String uSER_ADRESS, String uSER_EMAIL) {
		super();
		USER_ID = uSER_ID;
		USER_PW = uSER_PW;
		USER_NAME = uSER_NAME;
		USER_SEX = uSER_SEX;
		USER_PHONE = uSER_PHONE;
		USER_ADRESS = uSER_ADRESS;
		USER_EMAIL = uSER_EMAIL;
	}

	public User(String uSER_ID, String uSER_PW, String uSER_NAME, String uSER_SEX, String uSER_PHONE,
			String uSER_ADRESS, String uSER_EMAIL, String mANAGER_YN, String uSER_SECESSIO_YN) {
		super();
		USER_ID = uSER_ID;
		USER_PW = uSER_PW;
		USER_NAME = uSER_NAME;
		USER_SEX = uSER_SEX;
		USER_PHONE = uSER_PHONE;
		USER_ADRESS = uSER_ADRESS;
		USER_EMAIL = uSER_EMAIL;
		MANAGER_YN = mANAGER_YN;
		USER_SECESSIO_YN = uSER_SECESSIO_YN;
	}

	public User(String userID, String password) {
		// TODO Auto-generated constructor stub
	}

}