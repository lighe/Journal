package models;

import java.util.Date;
 
import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;
 
@Entity
public class Revision extends Model {

    public Date date;
    public int revision_number;
    public String pdf_url; 
    public boolean rejectedByEditor = false;
	
	@ManyToOne
    //@Column(name="article",length=1000) 
    public Article article;
	
    public Revision(Article article, Date date, int revision_number, String url_for_pdf) {
        this.article = article;
        this.date = date;
        this.revision_number = revision_number;
        this.pdf_url = url_for_pdf;
    }
	
	public List<Review> getReviews() {
		return Review.find("byRevision", this).fetch();
	}
	
	public void setAsRejected() {
		this.rejectedByEditor = true;	
	}
}