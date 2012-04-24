package models;

import java.util.Date;
 
import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;
 
@Entity
public class Articles extends Model {

    public String title;
    public String pdf;
    public boolean published;
    
    @Lob
    public String summary;
    
    @ManyToOne
    public Users user;
    
    @ManyToMany(mappedBy="articles") 
    public List<Tag> tags;
 
    public Articles (Users user, String title, List<Tag> tags) {
        this(user, false , title, "", tags);
    }
            
    public Articles(Users user, boolean published, String title, String summary, List<Tag> tags){
        this.user = user;
        this.published = published;
        this.title = title;
        this.summary = summary;
	this.tags = tags;
    }
 
}


