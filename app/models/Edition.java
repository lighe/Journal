package models;

import java.util.Date;
 
import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;
 
@Entity
public class Edition extends Model {
 
    public String name;   
    public Date date; //change this back to date
		
    @Column(name="volume",length=1000) 
    public Volume volume;

    @Column(name="published_by_editor",length=1000) 
    public User published_by_editor; 
	
    
    public Edition(String name, Date date, Volume volume, User editor) {
        this.name = name;
	this.date = date;
	this.volume = volume;
        this.published_by_editor = editor;
    }
	
    /**
     * Returns a list of published articles in this volume
     * @return 
     */
    public List<Published> getPublished() {
	return Published.find("byEdition", this).fetch();		
    }
 
}