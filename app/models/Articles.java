package models;

import java.util.Date;
 
import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;
 
@Entity
public class Articles extends Model {

    public String title;
	public String pdf;
	@Lob
	public String summary;
    
    @ManyToOne
    public Users user;
	
	@ManyToMany(mappedBy="articles") 
	public List<Tag> tags;
    
	public Articles (Users user, String title, List<Tag> tags) {
		this(user, title, "", tags);
	}
	
    public Articles(Users user, String title, String summary, List<Tag> tags){
        this.user = user;
        this.title = title;
        this.summary = summary;
		this.tags = tags;
    }
	
	public String getShortSummary() {
		int length = 150;
		if (summary != null && summary.length() > length) {
	  		return summary.substring(0, length).trim() + "...";
		}
 		return summary;
	}
}


