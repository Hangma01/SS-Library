package custom;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class CustomJTextFieldLimit extends PlainDocument{ //240531 15:00
	
	private int limit;

	public CustomJTextFieldLimit(int limit) {
		super();
		this.limit = limit;
	}
	
	
	public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException{
		
		if(str == null) {
			return;
		}
		
		if(getLength() + str.length() <= limit) {
			super.insertString(offset, str, attr);
		}
	}

}
