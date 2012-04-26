package models;

import java.util.Date;
 
import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;
 
@Entity
public class Review_comments extends Model {
  
    public String comment;     
	
	public Date date;  
	    
    @ManyToOne
    public Review review;
    
    public Review_comments(Date date, String comment, Review review) {
        this.review = review;
        this.date = date;
        this.comment = comment;
    }
 
}
