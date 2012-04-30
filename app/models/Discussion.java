package models;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import play.db.jpa.Model;
 
@Entity
public class Discussion extends Model {

    public Date comment_Date;    
    public String comment;     
    
    @Column(name="reviewA",length=1000) 
    public Review reviewA;
    @Column(name="reviewB",length=1000) 
    public Review reviewB;
    
    /*TODO save in controller, see artcles controller
    public Discussion addDiscussion(Review reviewA, Review reviewB){
        Discussion newdiscussion = new Discussion(reviewA, reviewB, comment).save();
        this.comment.add(newdiscussion);
        this.save();
        return this;
    }*/
    
    public Discussion(Review reviewA, Review reviewB, String comment) {
        this.reviewA = reviewA;
        this.reviewB = reviewB;
        this.comment_Date = new Date();
        this.comment = comment; 
    }
 
}
