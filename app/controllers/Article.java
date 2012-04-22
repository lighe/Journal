package controllers;

import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.List;

import org.apache.commons.mail.EmailException;

import models.Articles;
import models.Emailer;
import models.File_managment;
import models.Revisions;
import models.Users;
import play.*;
import play.data.Upload;
import play.data.validation.Required;
import play.mvc.*;

public class Article extends Controller {

	@Before
    static void setConnectedUser() {
        Security.setConnectedUser();
    }
	
    //TODO change error messages to plays flash messages
	
	/**
	 * renders the index.html page
	 */
    public static void index() {
    	render(Articles.all());
    }
       
    /**
     * For adding new articles.  If no params are given it renders and empty form, if title and article params are given it adds
     * the new article
     * @param title Title of the article 
     * @param tags any associated key words (optional)
     * @param authors any additional authors to give credit too (optional)
     * @param discription Article abstract
     * @param article the file containing the article
     */
    public static void newArticle(@Required String title, String tags, String authors,@Required String discription,@Required Upload article){

    	//TODO - tags and authors, need to be able to parse them and split them up
    	
    	//get the uploaded file parts and check a title is given
    	List<Upload> uploads  = (List<Upload>) request.current().args.get("__UPLOADS");
    	String responce = "";
    	if(title == null || uploads.isEmpty()){
    		responce = "Please chouse a title and upload youre article";
    	} else {
        	if(File_managment.upload(uploads, "public\\files\\unpublished\\")){
        		int articleID = (int) (Articles.count()+1);
        		Date date = new Date();
        		String url = "public\\files\\unpublished\\" + uploads.get(0).getFileName();
        		
        		//TODO add user, either by crating one on the fly or by taking it from session data
        		Users user = new Users((int) (Users.count()+1), "SomeEmail", "xxx");
          		
        		Articles art = new Articles(articleID, user , title);  
        		Revisions rev = new Revisions((int) (Revisions.count()+1), art, date, discription, 1, url);   
        		//Save the article and the revision
        		rev.save();
        		art.save();
        		
        		responce = title + " was sucessfully uploaded";
        	}else{
        		responce = "There was an issue uploading youre article, please try again later.";
        	}
    	}
		render("article/new.html", responce);
    }
      
    //TODO - basic html file already made
    /**
     * Edits articles
     * @param article the article to edit
     */
    public static void edit(String article){
    	//new rev but get original article
		render();
    }
    
    //TODO - basic html file already made, 
    /**
     * deletes articles
     * @param article the path to the article to delete
     */
    public static void delete(String article){
		File_managment.delete(article);
		//TODO - get and delete the article
		render();
    }
 
}
