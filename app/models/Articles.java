package models;

import java.util.Date;
 
import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;
 
@Entity
public class Articles extends Model {

    public int article_ID;
    public String title;
    
    @ManyToOne
    public Users user_ID;
    
    public Articles(int article_ID, Users user_ID, String title){
        this.article_ID = article_ID;
        this.user_ID = user_ID;
        this.title = title;
       
    }
 
}


