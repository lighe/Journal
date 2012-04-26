package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

@With(Secure.class)
public class ReviewController extends Controller {
	
	@Before
    static void setConnectedUser() {
        Security.setConnectedUser();
    }

    public static void index() {
    
		Users user = Security.getConnectedUser();
		
		List<SelectedArticle> selectedArticles = SelectedArticle.getSelectedArticles(user);
		List<Review> reviews = Review.getReviews(user);
		
        render(selectedArticles, reviews);
    }      
}
