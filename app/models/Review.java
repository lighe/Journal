package models;

import java.util.Date;
 
import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;
 
@Entity
public class Review extends Model {
	
	@Column(name="revision",length=1000) 
	public Revision revision;
	
	public String date; /* For some reason "date" type doesn't work; maybe it's because of yml */
	
	public int score;
	
	public String smallErrors;
	
	public int authorExpertiseLevel; 
	
	public boolean acceptedByEditor;
	
	@ManyToOne
    public User user;
		 
	public Review(Revision revision, String date, int score, String smallErrors, int authorExpertiseLevel, User user) {
		this.revision = revision;
		this.date = date;
		this.score = score;
		this.smallErrors = smallErrors;
		this.authorExpertiseLevel = authorExpertiseLevel;	
		this.user = user;
		this.acceptedByEditor = false;
	}
  
  	public static List<Review> getReviews(User user) {
		return Review.find("byUser", user).fetch();	//add order by status
	}
	
	public boolean isEditable() {
		Date today = new Date();	
		Date reviewDate = new Date(this.date);
		return (today.getTime() - reviewDate.getTime())/86400000 <= 7;	
	}
	

	public void addComment(String comment) {
		
		ReviewComment reviewComment = new ReviewComment(new Date(), comment, this);
		reviewComment.save();	
		
	}

    
	/* I'm not sure we need this
    @ManyToOne
    public Revisions revision_ID;
	*/
    
	/*
    public Reviews(int review_ID, String score, Users user_ID, Revisions revision_ID) {
        this.reviews_ID = review_ID;
        this.score = score;
        this.user_ID = user_ID;
        this.revision_ID = revision_ID;
        
    }*/
 
}

