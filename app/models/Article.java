package models;
 
import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;
 
@Entity
public class Article extends Model {
 
    public String title;
	public String pdf;
	
	@OneToMany(mappedBy="article", cascade=CascadeType.ALL) //NOTE: check that this doesn't delete shared tags
	public List<Tag> tags;
	
	//public Author author;
    
    public Article(String title, String pdf) {
        this.title = title;
        this.pdf = pdf;
    }
 
}