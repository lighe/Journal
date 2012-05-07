package controllers;

import models.*;
import play.*;
import play.data.Upload;
import play.data.validation.Required;
import play.db.jpa.GenericModel.JPAQuery;
import play.mvc.*;

import java.util.*;
import java.text.*;
import java.util.logging.Level;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.commons.mail.EmailException;


@With({Secure.class, Security.class, ApplicationController.class})
public class RevisionController extends Controller {
	
	public static void add(Long articleId) {
		//if no articleId present redirect
		//if(validation.required(articleId).error != null) {
			//index(); //show error
		//}
		
		Article article = Article.findById(articleId);
		Revision latestRev = article.getLatestRevision(article);
		List<Review> reviews = Review.find("byRevision", latestRev).fetch(); 
		
		render(reviews, article);
	}
	
	public static void save(Long articleId, List<Long> reviewCommentIds, List<String> revisionComments, File article) {		
		
		validation.required(articleId).message("Please don't type addresses directly, load this page from within the user interface.");
		validation.required(article).message("Please upload a revision PDF");
		
		if(!validation.hasErrors()) {
		
			//get the uploaded file parts and check a title is given
			List<Upload> uploads  = (List<Upload>) request.current().args.get("__UPLOADS");
			
			Article art = Article.findById(articleId);
			
			if(art.user.id == Security.getConnectedUser().id) {
				Revision latestRev = art.getLatestRevision(art);
				Revision rev = new Revision(art, new Date(), latestRev.revision_number+1, " ");  		
			
				String urlPrefix = "public/files/articles/";
				String urlSufix = art.title.trim()+String.valueOf(rev.revision_number).trim()+".pdf";
				rev.pdf_url = urlPrefix + urlSufix;
				String fileName = uploads.get(0).getFileName();
				if(FileManagment.isPDF(fileName)){
					if(FileManagment.upload(uploads, urlPrefix, urlSufix)){	        	
						//Save the revision
						rev.save();
						flash.success("Revison added!");
						//if all is sucessfull, show revision
					 } else {
						 validation.addError(null, "There was an issue uploading your pdf, please try again later.");
					 }
				} else {
					 validation.addError(null, "File was not a PDF.  Please make sure the file is a PDF");
				}
				
				if(!validation.hasErrors()) {
					//add the revision comments
					for(int i=0; i<revisionComments.size(); i++) {
						ReviewComment reviewComment = ReviewComment.findById(reviewCommentIds.get(i));
						RevisionComment revisionComment = new RevisionComment(new Date(), revisionComments.get(i), rev, reviewComment);
						revisionComment.save();	
					}	
					
					show(rev.id);
				}
				else {
					validation.keep();
					add(articleId);	
				}
			}
			else {
				validation.addError(null, "You can only add revisions to your own articles");	
				validation.keep();
				add(articleId);	
			}
		}
		else {
			validation.keep();
			add(articleId);
		}
	}

	public static void show(Long revisionId) {
	
		Revision revision = Revision.findById(revisionId);
		
		int noOfSelected = SelectedArticle.find("user = ? and article = ? and status != -1", Security.getConnectedUser(), revision.article).fetch().size();
		if(Security.getConnectedUser().id==revision.user.id || noOfSelected !=0 ) {
			Revision previousRevision = revision.article.getPreviousRevision(revision);
			
			if(previousRevision!=null) {
				List<Review> reviews = Review.find("byRevision", previousRevision).fetch();
				render(revision, reviews);	
			}
			
			render(revision);
		}
		else {
			ErrorController.notFound();	
		}
	}
	
	public static void editorRejectRevision(Long revisionId){
		if (Security.isEditor()){ 
			Revision revision = Revision.findById(revisionId);
			revision.setAsRejected();	
			
			try {
				Emailer.sendEmailTo(revision.article.user.email, Security.getConnectedUser().email, "Your revision has been rejected.", "Revision rejected");
			} catch (EmailException ex) {
				validation.addError(null, "Email failed to send, please try again later");
			} 
			
			flash.success("Revision Rejected.");
			ControlPanelController.activity();	
		}
	}
	
	public static void acceptRevision(Long revisionId) {
			Revision revision = Revision.findById(revisionId);
			int noOfSelected = SelectedArticle.find("user = ? and article = ? and status != -1", Security.getConnectedUser(), revision.article).fetch().size();
			if(noOfSelected > 0) {
				
			}
			else {
				ErrorController.notFound();	
			}
	}
}
