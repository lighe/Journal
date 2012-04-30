package models;

import java.util.Date;
 
import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;
 
@Entity
public class ReviewComment extends Model {
  
    public String comment;     
	
    public Date date;  
	    
    @Column(name="review",length=1000) 
    public Review review;
    
    public ReviewComment(Date date, String comment, Review review) {
        this.review = review;
        this.date = date;
        this.comment = comment;
    }
 
}
