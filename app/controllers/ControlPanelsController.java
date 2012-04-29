package controllers;

import java.util.List;

import models.*;
import play.*;
import play.data.validation.Required;
import play.mvc.Before;
import play.mvc.Controller;

public class ControlPanelsController extends  Controller {

	@Before
    static void setConnectedUser() {
        Security.setConnectedUser();
    }
	
	 public static void index() {
		User user  = Security.getConnectedUser();
		JournalConfiguration jc  = JournalConfiguration.all().first();

	    render("controlPanels/index.html", user, jc);
	}
	
	 public static void changePassword(String password1, String password2){

		 if(password1.isEmpty()||password2.isEmpty()){
			 validation.addError(null, "Please fill both fields");
		 } else {
			 User user = Security.getConnectedUser();
			 if(password1.contentEquals(password2)){
		       user.password = password1;
		       user.save();
		     } else {
			     validation.addError(null, "Passwords did not match");
		     }
		 }
	    
	     if(!validation.hasErrors()) {
	    	 flash.success("Password Updated");
	     }
    	 render("controlPanels/index.html");	 
	 }
	 
	 public static void resignEditor(String password){
		 if(password.isEmpty()){
			 validation.addError(null, "Please validate your resignation by typing in youre password.");
		 } else {
			 User user = Security.getConnectedUser();
			 if(password.contentEquals(user.password)){
				 List<User> userList = User.find("editor", false).fetch();
				 if(userList.size()>1){
					 user.editor = false;
					 user.save();
				 } else {
					 validation.addError(null, "You are the only editor at this time and therefore can not resign");
				 }
			 } else {
				 validation.addError(null, "Youre password was incorrect.");
			 }
		 }
	     if(!validation.hasErrors()) {
	    	 flash.success("Youre resignation has been accepted.");
	     }
    	 render("controlPanels/index.html");	 
	 }
	 
	 public static void journalControlPanel(){
		 JournalConfiguration jc = JournalConfiguration.all().first();
    	 render("controlPanels/journalPanel.html", jc);	 
	 }
	 
	 public static void updateJournal(String name, String goals){
		 JournalConfiguration jc = JournalConfiguration.all().first();
		 if(name.isEmpty() || goals.isEmpty()){
			 validation.addError(null, "Please fill in both the Journal name and goals fields");
		 } else {
			 jc.journalName = name;
			 jc.journalGoals = goals;
			 jc.save();
		 }
	     if(!validation.hasErrors()) {
	    	 flash.success("Journal Updated");
	     }
    	 render("controlPanels/journalPanel.html", jc);	 
	 }

	 public static void updateUser(@Required Long user_id, String email, String password, boolean author, boolean editor, boolean reviewer){
		       User user = User.findById(user_id);
		       user.email = email;
		       user.password = password;
		       user.author = author;
		       user.editor = editor;
		       user.reviewer = reviewer;
		       user.save();
		 		 
	 }
	 
	 
	
}
