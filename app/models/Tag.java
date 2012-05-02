package models;
 
import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;
 
@Entity
public class Tag extends Model {
 
    public String title;

	@ManyToMany(mappedBy="tags")
    //@Column(name="article",length=1000) 
	List<Article> articles = new ArrayList<Article>();
	    
    public Tag(Article article, String title) {
		this.title = title;
    }
 
}