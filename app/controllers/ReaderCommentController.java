package controllers;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.*;
import org.apache.commons.mail.EmailException;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;

public class ReaderCommentController extends Controller {

    public static void readerComments (String emailAddress, String message){        
		
		validation.required(emailAddress).message("Email address is required");
		validation.email(emailAddress).message("Please enter a valid email address");
		validation.required(message).message("Please enter a message");
       
	    if(!validation.hasErrors()) {
			List<User> editorsArray = User.find("editor", true).fetch();
			for (int x=0; x<editorsArray.size(); x++ ){
				User user = editorsArray.get(x);
				try {
					Emailer.sendEmailTo(user.email, emailAddress,  message,  "Reader Comment");
				} catch (EmailException ex) {
					validation.addError(null, "Email failed to send, please try again later");
				} 
			}
	    }    

		if(!validation.hasErrors()){
			flash.success("Thank you, your comment has been sent to an editor");
		}
        
        render ("ReaderCommentController/index.html");
        
    }
    
 }
    