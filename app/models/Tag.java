package models;
 
import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;
 
@Entity
public class Tag extends Model {
 
    public String title;
	
	@ManyToMany
	public List<Articles> articles;
	
	//public Author author;
    
    public Tag(List<Articles> articles, String title) {
    	this.articles = articles;
		this.title = title;
    }
 
}