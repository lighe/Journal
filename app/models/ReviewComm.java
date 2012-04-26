package models;
 
import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;
 
@Entity
public class ReviewComm extends Model {
  
    public String comment;     
		    
    @ManyToOne
    public Review review;
    
    public ReviewComm(String comment, Review review) {
        this.review = review;
        this.comment = comment;
    }
 
}
