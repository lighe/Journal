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
	
	public static void save(Long articleId, List<String> reviewCommentIds, List<String> revisionComments, File article) {		
		
		//get the uploaded file parts and check a title is given
    	List<Upload> uploads  = (List<Upload>) request.current().args.get("__UPLOADS");
		
		Article art = Article.findById(articleId);
		Revision latestRev = art.getLatestRevision(art);
		Revision rev = new Revision(art, new Date(), latestRev.revision_number, " ");  
		 
		String urlPrefix = "public/files/articles/";
		String urlSufix = art.title.trim()+String.valueOf(rev.revision_number).trim()+".pdf";
		rev.pdf_url = urlPrefix + urlSufix;
		String fileName = uploads.get(0).getFileName();
		if(FileManagment.isPDF(fileName)){
			if(FileManagment.upload(uploads, urlPrefix, urlSufix)){	        	
				//Save the revision
				rev.save();
				flash.success("Revison added");
				//if all is sucessfull, show revision
				render("");
			 } else {
				 validation.addError(null, "There was an issue uploading your pdf, please try again later.");
			 }
		} else {
			 validation.addError(null, "File was not a PDF.  Please make sure the file is a PDF");
		}
		
		//add comments
	}

	public static void show(Long reviewId) {
		
	}
}
