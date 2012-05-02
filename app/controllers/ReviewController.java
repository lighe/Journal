package controllers;

import play.*;
import play.mvc.*;
import play.data.validation.*;

import java.util.*;
import java.text.*;

import models.*;

@With(Secure.class)
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
	
	public static void remove(Long selectedArticleId ) {
		SelectedArticle.remove(selectedArticleId);
		index();	
	}
	
	public static void add(Long articleId) {
		//if no articleId present redirect
		if(validation.required(articleId).error != null) {
			index();
		}
		render(articleId);
	}
	
	public static void save(Long articleId, int judgment, String smallErrors, int expertise, List<String> criticism) {
		
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
          	//validation.keep();
			render("ReviewController/add.html", articleId, criticism);
		}
		
		Revision rev = Revision.findById(articleId);
		Date today = new Date();
		
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		String todayString = df.format(today);
                		
		Review review = new Review(rev, todayString, judgment, smallErrors, expertise, Security.getConnectedUser());
		review.save();
		//review.addComment(criticism);
		flash.success("Review added successfully. You can edit it within 7 days.");
		index();
		
		
	}

}
