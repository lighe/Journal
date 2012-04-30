package models;

import models.*;
import java.util.Date;
 
import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;
 
@Entity
public class Volume extends Model {
 
    public String name;   
    public Date date; 

    @Column(name="published_by_editor",length=1000) 
    public User published_by_editor;
			
    public Volume(String name, Date date, User editor) {
        this.name = name;
	this.date = date;
        this.published_by_editor = editor;
    }
 
    /**
     * returns a list of editions published in this volume
     * @return 
     */
    public List<Edition> getEditions() {
        return Edition.find("byVolume", this).fetch();		
    }
}