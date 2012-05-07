package models;

import java.util.Date;
 
import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;
 
@Entity
public class RevisionComment extends Model {
  
    public String comment;     
	
    public Date date;  
	  
	@ManyToOne 
    public Revision revision;
	
	@OneToOne
	public ReviewComment reviewComment;
    
    public RevisionComment(Date date, String comment, Revision revision, ReviewComment reviewComment) {
        this.date = date;
        this.comment = comment;
		this.revision = revision;
		this.reviewComment = reviewComment;
    } 
 
}
