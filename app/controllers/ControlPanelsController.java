package controllers;

import java.util.List;

import models.*;
import play.*;
import play.data.Upload;
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
			 validation.addError(null, "Please validate your resignation by typing in your password.");
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
				 validation.addError(null, "Your password was incorrect.");
			 }
		 }
	     if(!validation.hasErrors()) {
	    	 flash.success("Your resignation has been accepted.");
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
	 
	 public static void listUsers(){
		 List<User> users = User.find("editor", false).fetch();
		 render("controlPanels/listUsers.html", users);
	 }
	 
	 public static void promoteToEditor(@Required Long user_id){
		       User user = User.findById(user_id);
		       user.editor = true;
		       user.save();
		       flash.success(user.email + " premoted");
		       listUsers();
	 }
	 
	    /**
	     * uploads the newsletter file and then lets the user know
	     * @param file the newsletter to upload
	     */
	    public static void upload(@Required Upload file) {
	    	//get the uploaded file parts
			List<Upload> uploads  = (List<Upload>) request.current().args.get("__UPLOADS");
			if(uploads != null){
	    		//save file
				String fileName = uploads.get(0).getFileName();
				
				String destinationPrefix = "public/files/templates/";
				if(FileManagment.isPDF(fileName)){
					if(FileManagment.upload(uploads, destinationPrefix, "template.pdf")){
						JournalConfiguration jc = JournalConfiguration.all().first();
						jc.urlToPDFTemplate = destinationPrefix + "template.pdf";
						 jc.save();
					 } else {
						 validation.addError(null, "There was an error uploading youre file");
					 }
				} else if (FileManagment.isLaTEX(fileName)){
					if(FileManagment.upload(uploads, destinationPrefix, "template.tex")){
						JournalConfiguration jc = JournalConfiguration.all().first();
						jc.urlToLatexTemplate = destinationPrefix + "template.tex";
						jc.save();
					 } else {
						 validation.addError(null, "There was an error uploading your file");
					 }
				} else {
					 validation.addError(null, "Please ensure the template is either a PDF (.pdf), or a Latex file (.tex)");
				}
	    	} else {
				validation.addError(null, "Please ensure the template is either a PDF (.pdf), or a Latex file (.tex)");
	    	} 
			

		     if(!validation.hasErrors()) {
		    	 flash.success("Template uploaded");
		     }

			 JournalConfiguration jc = JournalConfiguration.all().first();
	    	 render("controlPanels/journalPanel.html", jc);	 
	    }
	 
	 
	
}
