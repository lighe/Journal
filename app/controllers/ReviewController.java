package controllers;

import play.*;
import play.mvc.*;
import play.data.validation.*;

import java.util.*;
import java.text.*;

import models.*;

@With({Secure.class, Security.class, ApplicationController.class})
public class ReviewController extends Controller {
	
	@Before
    static void setConnectedUser() {
        Security.setConnectedUser();
    }

    public static void index() {
    
		User user = Security.getConnectedUser();
		
		List<SelectedArticle> selectedArticles = SelectedArticle.getSelectedArticles(user);
		List<Review> reviews = Review.getReviews(user);
		
        render(selectedArticles, reviews);
    } 
	
	public static void removeSelected(Long selectedArticleId ) {
		SelectedArticle.remove(selectedArticleId);
		index();	
	}
	
	public static void addSelected(Long selectedArticleId) {
		Article article = Article.findById(selectedArticleId);
		if(SelectedArticle.count("user = ?", Security.getConnectedUser()) <3) {
			SelectedArticle selected = new SelectedArticle(article, new Date(), Security.getConnectedUser());
			selected.save();
			flash.success("Added successfully");
			PublishedController.unpublishedShow();	
		}
		else {
			validation.addError(null, "You have already selected 3 articles for review. Please review these before selecting more.");
			validation.keep();
			PublishedController.unpublishedShow();	
		}
	}
	
	public static void add(Long articleId) {
		//if no articleId present redirect
		if(validation.required(articleId).error != null) {
			index();
		}
		render(articleId);
	}
	
	public static void edit(Long articleId) {
		
	}
	
	public static void save(Long articleId, int judgment, String smallErrors, int expertise, List<String> criticism, String summary) {
		
		//if no articleId present redirect
		if(validation.required(articleId).error != null) {
			index();
		}
		
		//set validation rules
		validation.required(expertise).message("Please tell us your expertise level");
		validation.range(expertise,1, 3).message("Valid expertise level required");
		validation.required(judgment).message("Please give an overall judgment");
		validation.range(judgment, 1, 4).message("Valid overall judgment required");
		
		if (validation.hasErrors()) {
			params.flash(); // add http parameters to the flash scope
			render("ReviewController/add.html", articleId, criticism);
		}
		
		Article article = Article.findById(articleId);
		Revision rev = article.getLatestRevision(article);

		Date today = new Date();
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		String todayString = df.format(today);
          
		//if edit or add      		
		Review review = new Review(rev, todayString, judgment, smallErrors, expertise, Security.getConnectedUser(), summary);
		
		//save review
		review.save();
		
		//if edit delete all reviewComments:
		/*for(ReviewComment r : ReviewComment.<ReviewComment>find("byReview", review).fetch()) {
			r.delete();
		}*/
		
		for(int i=0; i<criticism.size(); i++) {
			ReviewComment comment = new ReviewComment(today, criticism.get(i), review);
			comment.save();	
		}
		
		//review.addComment(criticism);
		flash.success("Review added successfully. You can edit it within 7 days.");
		
		index();
		
		
	}

	public static void show(Long reviewId) {
		Review review = Review.findById(reviewId);
		String reviewScore = Review.scoreToHuman(review.score);
		String expertise = Review.expertiseToHuman(review.authorExpertiseLevel);
		render(review, reviewScore, expertise);
	}
}
