package models;

import models.*;
import java.util.Date;
 
import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;
 
@Entity
public class Volume extends Model {
 
    public String name;   
	public String date; //change this back to date
    
    //@ManyToOne
    //public Published author; //explain this? --alex
			
    public Volume(String name, String date) {
        this.name = name;
		this.date = date;
    }
 
 	public List<Edition> getEditions() {
		return Edition.find("byVolume", this).fetch();		
	}
}