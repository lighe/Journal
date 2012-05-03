package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import play.db.jpa.Model;
 
@Entity
public class Discussion extends Model {

    public Date commentDate;    
    public String comment;     
    
    public Revision revision;
    public User user;
    
    
    /*TODO save in controller, see artcles controller
    public Discussion addDiscussion(Review reviewA, Review reviewB){
        Discussion newdiscussion = new Discussion(reviewA, reviewB, comment).save();
        this.comment.add(newdiscussion);
        this.save();
        return this;
    }*/
    
    public Discussion(Revision revision, String comment, User user) {
        this.revision = revision;
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
