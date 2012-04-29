package controllers;

import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.List;

import org.apache.commons.mail.EmailException;

import models.*;
import play.*;
import play.data.Upload;
import play.data.validation.Required;
import play.db.jpa.GenericModel.JPAQuery;
import play.mvc.*;

public class ArticleController extends Controller {

	//@Before
    //static void setConnectedUser() {
    //    Security.setConnectedUser();
    //}

	/**
	 * renders the index.html page
	 */
    public static void index() {
    	User user = Security.getConnectedUser();
    	List<Article> articles = Article.find("user", user).fetch();
    	render(articles);
    }

    public static void show(Long id) {
    	Article article = Article.findById(id);
		Revision latestRev = article.getLatestRevision(article);
		if(latestRev.revision_number > 0){
	        int previous_revision_number = latestRev.revision_number -1; 
		    render(latestRev, article, previous_revision_number);
		} else {
	            render(latestRev, article);
		}
    }
        
    public static void showRevision(long id, int revision_num) {
    	Article article = models.Article.findById(id);
    	Revision revision = Revision.find("revision_number", revision_num).first();
        render("articleController/show.html", revision, article);
    }
    
    public static void newArticleA(){
 		render("articleController/new.html");
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
    public static void newArticle(String title, String tags, String authors, String discription,Upload article, String email, String password){

    	//TODO - tags and authors, need to be able to parse them and split them up
    	List<Tag> tagsList = null;
    	//get the uploaded file parts and check a title is given
    	List<Upload> uploads  = (List<Upload>) request.current().args.get("__UPLOADS");
    	
    	User user = null;
    	if(email.isEmpty()||password.isEmpty()){
    		if(Security.isConnected()){
    			user = Security.getConnectedUser();
    		} else {
   			 	validation.addError(null, "Please provide login details");
    		}
    	} else {
    		if(Security.authenticate(email, password)){
    			user = Security.getConnectedUser();
    		} else {
   			 	validation.addError(null, "Please provide login details");
    		}
    	}
    	
    	 if(title.isEmpty()||uploads.isEmpty()){
			 validation.addError(null, "Please fill in atleast the title field and selects a file to upload");
		 } 
	     if(!validation.hasErrors()) {

	        Date date = new Date();

	        Article art = new models.Article(user , false, title, discription, tagsList); 
	        Revision rev = new Revision(art, date, 1, "");   
	        String url = "\\public\\files\\articles\\" + rev.id.toString();
	        rev.pdf_url = url;
	    	if(FileManagment.upload(uploads, "\\public\\files\\articles\\", rev.id.toString())){		        	
		        //Save the article and the revision
		        rev.save();
		        art.save();
			    flash.success("Article added");

			    showOwnArticle(art.id);
	    	 } else {
	    		 validation.addError(null, "There was an issue uploading youre article, please try again later.");
	    	 }
	     }
	     
	     if(validation.hasErrors()){
	 		render("articleController/new.html");
	     }
    }
      
    //TODO - basic html file already made, 
    /**
     * deletes articles
     * @param id the id of the article to delete
     * @param article the path to the article to delete
     */
    public static void delete(String id, String article){
    	int article_ID = Integer.getInteger(id);
    	models.Article.delete("article_ID", article_ID);
		FileManagment.delete(article);
		render();
    }
    
    public static void showOwnArticle(Long id) {
        User user = Security.getConnectedUser();
        Article art = Article.findById(id);
            if(art.user ==  user){
                Article article = Article.findById(id);
                Revision latestRev = article.getLatestRevision(article);
                if(latestRev.revision_number > 0){
                    int previous_revision_number = latestRev.revision_number -1; 
                    render(latestRev, article, previous_revision_number);
                } else {
                    render(latestRev, article);
                }
            }
                         
    }
}
 