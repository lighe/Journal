package controllers;
import java.util.List;

import play.mvc.Before;
import models.*;
 
public class Security extends Secure.Security {
    		
	@Before
    static void setConnectedUser() {
        if(Security.isConnected()) {
            User user = User.find("email", Security.connected()).first();
            renderArgs.put("user", user);
        }
    }
	
    static boolean authenticate(String username, String password) {
        User user = User.find("byEmail", username).first();
        return user != null && user.password.equals(password);
    }   
    
    //returns true if user is an author
    public static boolean isAuthor() {
        User user = User.find("email", Security.connected()).first();
    	return user.author;
    }
    
    //returns true if user is a reviewer
    public static boolean isReviewer() {
        User user = User.find("email", Security.connected()).first();
    	return user.reviewer;
    }
    
    //returns true if user is an editor
    public static boolean isEditor() {
        User user = User.find("email", Security.connected()).first();
    	return user.editor;
    }
    
    public static User getConnectedUser() {
	return User.find("email", Security.connected()).first();	
    }

}