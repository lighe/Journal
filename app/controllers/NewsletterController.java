package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.List;

import org.apache.commons.mail.EmailException;

import models.Emailer;
import models.FileManagment;
import models.User;
import play.*;
import play.data.Upload;
import play.data.validation.Required;
import play.mvc.*;

@With({Secure.class, ApplicationController.class})
public class NewsletterController extends Controller {

    @Before
    static void setConnectedUser() {
        Security.setConnectedUser();
    }
	
	/**
	 * renders the index page
	 */
    public static void index() {
    	String filesTable = buildTable();
    	render("newsletter/index.html", filesTable);
    }
    
    /**
     * uploads the newsletter file and then lets the user know
     * @param file the newsletter to upload
     */
    public static void upload(File newsletter) {
    	//get the uploaded file parts
		List<Upload> uploads  = (List<Upload>) request.current().args.get("__UPLOADS");
		if(uploads != null){
    		//save file
			String destinationPrefix = "public/files/newsletters/";
			FileManagment.upload(uploads, destinationPrefix, uploads.get(0).getFileName());
			index(); //re-render index
    	} else {
    		index(); //if no upload found, send back to upload page
    	} 
    }
    
    /**
     * deletes selected newsletters and re-renders the index
     * @param newsletter String representing the newsletter files path
     */
    public static void delete(@Required String newsletter){
    	FileManagment.delete(newsletter);
		index();
    }

    /**
     * Email all users a specified file
     * @param newsletter represents the file path of the newsletter to email to all users
     */
    public static void sendEmail(@Required String newsletter){
    	String fileDestination = newsletter;
		File newFile = new File(fileDestination);
		for(long x = 1; x < User.count(); x++){
			User user = User.findById(x);
			User current = Security.getConnectedUser();
			try {
				Emailer.sendNewsletterTo(user.email, current.email, newFile);
			} catch (EmailException e) {
				e.printStackTrace();
			}
		}
    }
    
    //Returns a list of all files uploaded to the newsletter directory (
    private static String[][] getAvailableNewsletters(){
    	//Directory path here
    	String path = "public\\files\\newsletters\\";
    	 
    	File folder = new File(path);
    	File[] listOfFiles = folder.listFiles();
        System.out.println(folder.listFiles());
        String[][] files = null;
        if(listOfFiles != null){
            files = new String[listOfFiles.length+1][2];
    	
            for (int i = 0; i < listOfFiles.length; i++){
                if (listOfFiles[i].isFile()){
                       files[i][0] = listOfFiles[i].getName();
                       String uri = listOfFiles[i].getPath();
                       files[i][1] = uri.replaceAll("\\\\", "/");
                }
           }
        }
    	return files;
    }    
    
    //Returns a String that contains a html table that neatly presents the files available for use as newsletters
    private static String buildTable(){
    	String files[][] = getAvailableNewsletters();
        String filesTable = "";
        if(files != null){
		filesTable = "<div id='fileTable' name='fileTable'> \n<Table>\n";
		for(int x = 0; x < files.length-1; x++ ){
			filesTable += "<tr><td width=20px><center><input type='radio' name="+files[x][0]+" value="+files[x][1]+" onchange='newSelection(this);' /></center></td><td><a href='" + files[x][1] + "'> " + files[x][0] + "</a></td>\n";
			filesTable += "</tr>\n";
		}
		filesTable += "</table>\n</div>\n";
        } else {
            filesTable = "<div id='fileTable' class='alert alert-info'><strong><i class='icon-info-sign'></i> No newsletter found</strong></div>";
        }
	return filesTable; 	
    }
	
	public static void subscribe(String email, String name) {
		
		validation.required(email).message("Please give us an email address!");
		validation.email(email).message("Please give us a valid email address!");
				
		if (validation.hasErrors()) {
			params.flash(); // add http parameters to the flash scope
			validation.keep();
		}	
		else {
			flash.success("Thank you! You have been subscribed to our newsletter!");
		}
		ApplicationController.index();
	}
}
