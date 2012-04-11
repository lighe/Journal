package models;

import java.util.Date;
 
import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;
 
@Entity
public class Review_comments extends Model {

    public Date review_Date;    
    public String comment;     
    
    @ManyToOne
    public Users user_ID;
    
    @ManyToOne
    public Reviews review_ID;
    
    
    public Review_comments(Reviews review_ID, Users user_ID, Date review_Date, String comment) {
        this.review_ID = review_ID;
        this.user_ID = user_ID;
        this.review_Date = review_Date;
        this.comment = comment;
        
    }
 
}
