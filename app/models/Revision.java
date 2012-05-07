package models;

import java.util.Date;
 
import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;

import controllers.*;
 
@Entity
public class Revision extends Model {

    public Date date;
    public int revision_number;
    public String pdf_url; 
    public boolean rejectedByEditor = false;
	
	@ManyToOne
    //@Column(name="article",length=1000) 
    public Article article;
	
	@ManyToOne
	public User user;
	
    public Revision(Article article, Date date, int revision_number, String url_for_pdf) {
        this.article = article;
        this.date = date;
        this.revision_number = revision_number;
        this.pdf_url = url_for_pdf;
		this.user = Security.getConnectedUser();
    }
	
	public List<Review> getReviews() {
		return Review.find("byRevision", this).fetch();
	}
	
	public void setAsRejected() {
		this.rejectedByEditor = true;	
	}
	
	public boolean isLatestRevision() {
		Revision revision = Revision.find("rejectedByEditor = false and article = ? order by revision_number desc", article).first();
		if (this.id == revision.id) return true;
		return false;	
	}
}