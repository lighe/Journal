package models;

import java.util.Date;
 
import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;
 
@Entity
public class SelectedArticle extends Model {
	
	@OneToOne
	//@Column(name="article",length=1000) 
	public Article article;
	
	//0 selected, 1 downloaded, 2 submitted
	public int status;
	
	public Date date;
	
	@OneToOne
	//@Column(name="user",length=1000) 
    public User user;
		 
	public SelectedArticle(Article article, Date date, User user) {
		this.article = article;
		this.date = date;
		this.user = user;
		this.status = 0;
	}
	
	public SelectedArticle(Article article, int status, Date date, User user) {
		this.article = article;
		this.status = status;
		this.date = date;
		this.user = user;
	} 
	
	public String getReadableStatus() {
		switch(status) {
			case 0: return "Article Selected";	
			case 1: return "Article Downloaded";
			case 2: return "Review Submitted";
			case 3: return "Review Accepted";
			default: return "Error";
		}
	}
	
	public static List<SelectedArticle> getSelectedArticles(User user) {
		return SelectedArticle.find("user = ? order by status asc", user).fetch();
	}
	
	public static void remove(Long selectedArticleId) {
		SelectedArticle selectedArticle = SelectedArticle.findById(selectedArticleId);
		selectedArticle.delete();
	}
	
	public void setAsDownloaded() {
		this.status = 1;	
	}
}

