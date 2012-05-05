package controllers;

import java.util.List;

import models.*;
import play.*;
import play.data.Upload;
import play.data.validation.Required;
import play.mvc.*;

@With({Secure.class, Security.class, ApplicationController.class})
public class ControlPanelController extends  Controller {
	
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
	 
	 public static void updateJournal(String name, String goals, String shortDescription){
		 JournalConfiguration jc = JournalConfiguration.all().first();
		 if(name.isEmpty() || goals.isEmpty()){
			 validation.addError(null, "Please fill in all fields");
		 } else {
			 jc.journalName = name;
			 jc.journalGoals = goals;
			 jc.shortDescription = shortDescription;
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
		if(!validation.hasErrors()) {
			User user = User.findById(user_id);
			user.editor = true;
			user.save();
			flash.success(user.email + " promoted");
		}
		listUsers();
	 }
	 
	    /**
	     * uploads the newsletter file and then lets the user know
	     * @param file the newsletter to upload
	     */
	    public static void upload(Upload file, String guidelines) {
			
			JournalConfiguration jc = JournalConfiguration.all().first();
						
			jc.guidelines = guidelines;
					
	    	//get the uploaded file parts
			List<Upload> uploads  = (List<Upload>) request.current().args.get("__UPLOADS");
			if(uploads != null){
	    		//save file
				String fileName = uploads.get(0).getFileName();
				
				String destinationPrefix = "public/files/templates/";
				if(FileManagment.isDoc(fileName)){
  					String ext=fileName.substring(fileName.lastIndexOf(".")+1,fileName.length());
					System.out.println(ext);
					if(FileManagment.upload(uploads, destinationPrefix, "template."+ext)){
						jc.urlToDocTemplate = destinationPrefix + "template."+ext;
					 } else {
						 validation.addError(null, "There was an error uploading youre file");
					 }
				} else if (FileManagment.isLaTEX(fileName)){
					if(FileManagment.upload(uploads, destinationPrefix, "template.tex")){
						jc.urlToLatexTemplate = destinationPrefix + "template.tex";
					 } else {
						 validation.addError(null, "There was an error uploading your file");
					 }
				} else {
					 validation.addError(null, "Please ensure the template is either a Doc (.doc or .docx), or a Latex file (.tex)");
				}
	    	} else {
				validation.addError(null, "Please ensure the template is either a Doc (.doc or .docx), or a Latex file (.tex)");
	    	} 

		     if(!validation.hasErrors()) {
		    	 flash.success("Template uploaded");
		     }
			
			 jc.save();
	    	 render("controlPanels/journalPanel.html", jc);	 
	    }

	public static void activity() {
		List<Review> reviews = Review.find("rejectedByEditor is ? order by id desc", false).fetch();
		List<SelectedArticle> selectedArticles = SelectedArticle.find("status != ? and status !=? order by status desc", -1, 100).fetch();
		render("ControlPanels/activity.html", reviews, selectedArticles);	
	} 
	
}
