package models;

import java.util.Date;
 
import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;
 
@Entity
public class Published extends Model {
	
	//will need authorised by
   
    @OneToOne
    public Articles article;
   
	@ManyToOne
    public Edition edition;
    
    public Published(Articles article, Edition edition){
        this.article = article;
		this.edition = edition;
    }
   
    public Articles getArticle() {
		return Articles.findById(this.id);		
	}
}

/*
public class Published extends Model {

    public int published_ID;  
    public int authorised_By;      
    public boolean published;    
    
    @OneToOne
    public Articles article_ID;
    
    //@ManyToOne
   // public Users user_ID;
   // this needs to replace authorsied by 
    
    @OneToOne
    public Volumes volume_ID;
    
    public Published(int published_ID, Articles article_ID, int authorised_By, Volumes volume_ID, boolean published) {
        this.published_ID = published_ID;
        this.article_ID = article_ID;
        this.authorised_By = authorised_By;
        this.volume_ID = volume_ID;
        this.published = published;
        }
    
 
}

*/