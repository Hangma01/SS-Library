package custom;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JTextField;

public class CustomHintTextField extends JTextField {  //240531 15:00
	   

	   
	   public CustomHintTextField(final String hint) {  
	   
	     setText(hint);  
	  
	  
	    setForeground(Color.BLACK);  
	
	   
	     this.addFocusListener(new FocusAdapter() {  
	   
	       @Override  
	       public void focusGained(FocusEvent e) {  
	         if (getText().equals(hint)) {  
	           setText("");  
	           setForeground(Color.BLACK); 
	         } else {  
	           setText(getText());  
	            
	         }  
	       }  
	   
	       @Override  
	       public void focusLost(FocusEvent e) {  
	         if (getText().equals(hint)|| getText().length()==0) {  
	           setText(hint);  
	           setForeground(Color.BLACK);  
	         } else {  
	           setText(getText());  
	         
	           setForeground(Color.BLACK);  
	         }  
	       }  
	     });  
	   
	   }  
	 }  
