package models;

import java.util.Date;
 
import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;
 
@Entity
public class Review extends Model {
	
	@ManyToOne
	public Revision revision;
	
	public String date; /* For some reason "date" type doesn't work; maybe it's because of yml */
	
	public int score;
	
	public String smallErrors;
	
	public int authorExpertiseLevel; 
	
	public boolean rejectedByEditor;
	
	@Lob
	public String summary;
	
	@ManyToOne
    public User user;
	
	@OneToMany
	public List<ReviewComment> reviewComments;
		 
	public Review(Revision revision, String date, int score, String smallErrors, int authorExpertiseLevel, User user, String summary) {
		this.revision = revision;
		this.date = date;
		this.score = score;
		this.smallErrors = smallErrors;
		this.authorExpertiseLevel = authorExpertiseLevel;	
		this.user = user;
		this.rejectedByEditor = false;
		this.summary = summary;
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
	
	public static String scoreToHuman(int i) {
		if(i==1) return "Detractor - I strongly condemn the paper as badly flawed or worthless";
		if(i==2) return "Indifferent - I would not care if the paper were rejected";
		if(i==3) return "Favourable - I would not object if the paper were published";
		if(i==4) return "Champion - I strongly advocate this paper";	
		return "";
	}
	
	public static String expertiseToHuman(int i) {
		if(i==1) return "Expert";
		if(i==2) return "Knowledgeable";
		if(i==3) return "Outsider";
		return "";	
	}
	
	public List<ReviewComment> getReviewComments() {
		return ReviewComment.find("byReview", this).fetch();		
    }
    
	public void reject() {
		this.rejectedByEditor = true;	
		this.save();
	}
 
}

