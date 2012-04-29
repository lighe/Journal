package models;
 
import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;
 
@Entity
public class Tag extends Model {
 
    public String title;
	
	@ManyToMany
	public List<Article> articles;
	
	//public Author author;
    
    public Tag(List<Article> articles, String title) {
    	this.articles = articles;
		this.title = title;
    }
 
}