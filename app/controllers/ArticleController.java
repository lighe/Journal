package controllers;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.commons.mail.EmailException;

import models.*;
import play.*;
import play.data.Upload;
import play.data.validation.Required;
import play.db.jpa.GenericModel.JPAQuery;
import play.mvc.*;

@With({ApplicationController.class, Security.class})
public class ArticleController extends Controller {

	//Removed to allow readers to submit articles without the need to login
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
        render("ArticleController/show.html", revision, article);
    }
    
    public static void newArticleA(){
 		render("ArticleController/new.html");
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
    public static void newArticle(String title, String name, String tags, String[] authors, String[] authorsAffiliation, String[] authorNames, String discription, File article, String email, String affiliation, String password){

		params.flash();

    	//TODO - tags and authors, need to be able to parse them and split them up
    	List<Tag> tagsList = null;
    	//get the uploaded file parts and check a title is given
    	List<Upload> uploads  = (List<Upload>) request.current().args.get("__UPLOADS");
    	
    	User user = new User();
    	/*
    	 * Force minimum of title, abstract and article
    	 */
    	if(title.isEmpty()||uploads == null||discription.isEmpty()){ //Force minimum amount of data to be filled in
    		validation.addError(null, "Please fill in atleast the title and description fields and select a PDF to upload");
    	}
    	/*
    	 * If user connected, set that as the user
    	 */
    	else if(Security.isConnected()){
    		user = Security.getConnectedUser();
    	}
    	/*If no email given, no name given and no user connected,
			then return error
		 */ 
    	else if(!Security.isConnected() && (email.isEmpty() || name.isEmpty())){
    		validation.addError(null, "You must provide an email address and youre name");
    	}
    	/*If email and name given but no password,
			then check if user is already registered - if so return error
			else create user and email password to account    	
    	 */
    	else if((!email.isEmpty()) && (!name.isEmpty()) && password.isEmpty()) {
    		List<User> userList = User.find("email", email).fetch();
    		if(userList.isEmpty()){
	    		String pass = generatePassword();
	    		user = new User(email, name, pass);
	    		JournalConfiguration jc  = JournalConfiguration.all().first();
	    		String message = "Welcome to " + jc.journalName + ".  Your account has been created and is ready for you to use, youre password is '"+pass+"' (Without quotes).  You may change it from youre user control panel once you have loged on.";
	    		try {
	    			//Get current primary editor
	    			User sender = User.find("editor", true).first();
					Emailer.sendEmailTo(email, sender.email, message, "Account created");
					//once everything is successful, save the user
					user.save();
				} catch (EmailException e) {
			    	validation.addError(null, "There was an issue emailing your email account, are you sure it is correct?");
				}
    		} else {
		    	validation.addError(null, "Email address taken");
    		}
    	}
    	/*If email given and password,
			then validate - return error if not valid
			else use that user
    	 */
    	else if ((!email.isEmpty()) && (!password.isEmpty())){
    		if(Security.authenticate(email, password)){
    			user = User.find("email", Security.connected()).first();
    		} else {
		    	validation.addError(null, "Your login details were incorrect, please try again.");
    		}
    	}
    	//If no errors so far, try and save the article and create article/revision
	     if(!validation.hasErrors()) {
	    	 
	        Date date = new Date();

	   		ArrayList<Contributor> conList = new ArrayList<Contributor>();
	        //Brake down the additional authors into a list
	        if(authors != null){
			   	for(int x=0;x<authors.length;x++){
			   		ArrayList<Affiliation> affiliations = new ArrayList<Affiliation>();
			   		if(authorsAffiliation[x] != null){
				   		String authAffiliation = authorsAffiliation[x];
				   		while(authAffiliation.contains(",")){
				   			Affiliation aff = new Affiliation(authAffiliation.substring(0,authAffiliation.indexOf(",")));
				   			System.out.println(authors[x] + " Affiliation: " + aff.affiliation_name);
				   			aff.save();
				   			affiliations.add(aff);
				   			authAffiliation = authAffiliation.substring(authAffiliation.indexOf(",")+1, authAffiliation.length());
			        	}
				   		Affiliation aff = new Affiliation(authAffiliation);
				   		aff.save();
				   		affiliations.add(aff);
			   		}
			   		Contributor con = new Contributor(authors[x], authorNames[x], affiliations);
			   		con.save();
			   		conList.add(con);
			   	}
	        }
	        
	        ArrayList<String> authorArray = new ArrayList<String>();
	        if(authors != null){
	        	for(int x = 0; x<authors.length; x++){
	        		authorArray.add(authors[x]);
	        	}
	        }
	        Article art = new models.Article(user , false, title, discription); 
	        art.addContributors(conList);
			
			String[] tagsArray = tags.split(",");
			
	        ArrayList<Tag> tagsFinalArray = new ArrayList<Tag>();
			int size = 10;
			if(size > tagsArray.length) size = tagsArray.length;
	        for(int x = 0; x < size; x++){
	        	Tag tag = new Tag(art, tagsArray[x]);
				tag.save();
	        	tagsFinalArray.add(tag);
	        }
	        art.addTags(tagsFinalArray);
	        
	        //Author affiliations
	        ArrayList<Affiliation> authAffilationsList = new ArrayList<Affiliation>();
	        if(affiliation != null){
		   		while(affiliation.contains(",")){
		   			Affiliation aff = new Affiliation(affiliation.substring(0,affiliation.indexOf(",")));
		   			System.out.println("Auther Affiliation: "+aff.affiliation_name);
		   			aff.save();
			        user.addAffiliation(aff);
			        user.save();
		   			authAffilationsList.add(aff);
		   			affiliation = affiliation.substring(affiliation.indexOf(",")+1, affiliation.length());
	        	}
		   		Affiliation aff = new Affiliation(affiliation);
		   		aff.save();
		        user.addAffiliation(aff);
		        user.save();
		   		authAffilationsList.add(aff);
	   		}
	        art.addAffiliations(authAffilationsList);
			
	        Revision rev = new Revision(art, date, 1, " ");   
	        String urlPrefix = "public/files/articles/";
	        String urlSufix = art.title.trim()+String.valueOf(rev.revision_number).trim()+".pdf";
	        rev.pdf_url = urlPrefix + urlSufix;
	        String fileName = uploads.get(0).getFileName();
	        if(FileManagment.isPDF(fileName)){
		    	if(FileManagment.upload(uploads, urlPrefix, urlSufix)){	        	
			        //Save the article and the revision
			        art.save();
			        rev.save();
				    flash.success("Article added.  Thanks for your contribution.");
				    //if all is sucessfull, show the article
				    render("articleController/new.html");
		    	 } else {
		    		 validation.addError(null, "There was an issue uploading your article, please try again later.");
		    	 }
	        } else {
	        	 validation.addError(null, "File was not a PDF.  Please make sure the file is a PDF");
	        }
	     }
	     
	     if(validation.hasErrors()){
	 		render("ArticleController/new.html");
	     }
    }
    
    private static String generatePassword(){
    	String[] alpha = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","0","1","2","3","4","5","6","7","8","9"};
        Random randomGenerator = new Random();
        String password = "";
    	for(int x=0;x<8;x++){
    	   int ran = randomGenerator.nextInt(alpha.length);
    	   password += alpha[ran];
    	}
    	return password;
    }
      
    //TODO - basic html file already made, But do we actually need this function?
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
	
	public static void download(@Required Long id, @Required int revisionNumber) {
				
		if (validation.hasErrors()) ErrorController.notFound(); 
		
		//Get the article in question
		Article article = Article.findById(id);
		
		//If article doesn't exist show error
		if(article==null) {		
			ErrorController.notFound(); //expand not published to allow download for reviewers, editors, etc
		}
		else {
			//If the user is authorised
			User user = Security.getConnectedUser();
			if(user.editor || user.author || user.reviewer) {
				//All users except editors have to be committed to article after download
			
				List<SelectedArticle> selectedArticles = SelectedArticle.find("byArticleAndUser", article, user).fetch();
				if((selectedArticles==null||selectedArticles.size() == 0)&&!user.editor) ReviewController.notFound(); //more specific
				else selectedArticles.get(0).setAsDownloaded();	
			
				
				//Get article revisions
				List<Revision> revisions = Revision.find("article_ID", article).fetch();
				if(revisions==null || revisions.isEmpty()) ErrorController.notFound();
				
				//Check if revision number is valid		
				if(revisionNumber >= revisions.size()) ErrorController.notFound();
				
				
				Revision revision;
				//If revision < 0 get last revision else get required revision 
				if(revisionNumber<0) revision = revisions.get(revisions.size()-1);
				else revision = revisions.get(revisionNumber);
				
				//Render file to browser				
				File f = new java.io.File(revision.pdf_url); 
				renderBinary(f, article.title + ".pdf"); 
				
			}
		}	
	}
	
}
 