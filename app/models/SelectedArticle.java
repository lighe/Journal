package models;

import java.util.Date;
 
import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;
 
@Entity
public class SelectedArticle extends Model {
	
	@ManyToOne
	public Articles article;
	
	//0 selected, 1 downloaded, 2 submitted, 3 accepted
	public int status;
	
	public Date date;
	
	@ManyToOne
    public Users user;
		 
	public SelectedArticle(Articles article, int status, Date date, Users user) {
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
	
	public static List<SelectedArticle> getSelectedArticles(Users user) {
		return SelectedArticle.find("user = ? order by status asc", user).fetch();
	}
	
	public static void remove(Long selectedArticleId) {
		SelectedArticle selectedArticle = SelectedArticle.findById(selectedArticleId);
		selectedArticle.delete();
	}
}

