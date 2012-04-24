package models;

import java.util.Date;
 
import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;
 
@Entity
public class Edition extends Model {
 
    public String name;   
	public String date; //change this back to date
	
	@OneToMany
	public List<Published> articles;
	
	@ManyToOne
	public Volume volume;
    
    //@ManyToOne
    //public Published author; //explain this? --alex
	
    
    public Edition(String name, String date, Volume volume) {
        this.name = name;
		this.date = date;
		this.articles = new ArrayList<Published>();
		this.volume = volume;
    }
	
	
	public List<Published> getPublished() {
		return Published.find("byEdition", this).fetch();		
	}
 
}