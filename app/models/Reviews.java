package models;

import java.util.Date;
 
import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;
 
@Entity
public class Reviews extends Model {

    public String score;      
    
    @ManyToOne
    public Users user_ID;
    
    
    @ManyToOne
    public Revisions revision_ID;
    
    public Reviews(String score, Users user_ID, Revisions revision_ID) {
        this.score = score;
        this.user_ID = user_ID;
        this.revision_ID = revision_ID;
        
    }
 
}

