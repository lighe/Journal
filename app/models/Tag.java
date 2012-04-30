package models;
 
import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;
 
@Entity
public class Tag extends Model {
 
    public String title;

    @Column(name="article",length=1000) 
	public Article article;
	
	//public Author author;
    
    public Tag(Article article, String title) {
    	this.article = article;
		this.title = title;
    }
 
}