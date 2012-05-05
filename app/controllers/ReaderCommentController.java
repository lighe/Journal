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
        JournalConfiguration jc = JournalConfiguration.all().first();
        
        if(emailAddress == null && message == null){
            render ("ReaderCommentController/index.html", jc);
        
        } else {
            if(emailAddress == null || message == null){
                validation.addError(null, "Need both an email and a message");
            } else {
                List<User> editorsArray = User.find("editor", true).fetch();
                for (int x=0; x<editorsArray.size(); x++ ){
                    User user = editorsArray.get(x);
                    try {
                        Emailer.sendEmailTo(user.email, Security.getConnectedUser().email,  message,  "Reader Comment");
                    } catch (EmailException ex) {
                        validation.addError(null, "Email failed to send, please try again later");
                    }

                }

            }
            if(!validation.hasErrors()){
                flash.success("Thank you, your comment has been sent to an editor");
            }
        }
        
        render ("ReaderCommentController/index.html", jc);
        
    }
    
 }
    