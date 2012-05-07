package models;

import java.util.Date;
 
import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;

 
@Entity
public class Discussion extends Model {

    public Date commentDate;   
	@Lob 
    public String comment;     
    
	@ManyToOne
    public Review review;
	
	@ManyToOne
    public User user;
    
    
    /*TODO save in controller, see artcles controller
    public Discussion addDiscussion(Review reviewA, Review reviewB){
        Discussion newdiscussion = new Discussion(reviewA, reviewB, comment).save();
        this.comment.add(newdiscussion);
        this.save();
        return this;
    }*/
    
    public Discussion(Review review, String comment, User user) {
        this.review = review;
        this.commentDate = new Date();
        this.comment = comment; 
        this.user = user;
    } 
    
    public static List<User> getUserList(Revision rev){
        List <Review> reviews =  Review.find("revision", rev).fetch();
        ArrayList<User> users = new ArrayList<User>();
        for(int x=0; x < reviews.size(); x++){
            User user = reviews.get(x).user;
            users.add(user);
        }
        return users;
        
    }
}
