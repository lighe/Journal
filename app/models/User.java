package models;

import java.util.Date;
import java.util.List;
 
import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;    

@Entity
public class User extends Model {

	@Column(name="email",length=30) 
    public String email; 
    public String name;
	@Column(name="password",length=30) 
    public String password;
    public boolean author;
    public boolean editor;
    public boolean reviewer;
    @ManyToMany
	List<Affiliation> affiliationList;
    
   //Empty constructor
    public User(){  
    	this.email = "";
    	this.name = "";
    	this.password = "";
        this.author = true;
        this.editor = false;
        this.reviewer = true;  	
    }
    public User(String email, String password, String name, boolean author, boolean editor, boolean reviewer) {
        this.email = email;
    	this.name = name;
        this.password = password;
        this.author = author;
        this.editor = editor;
        this.reviewer = reviewer;
    }
    /**
     * Default constructor giving users author and reviewer privaleges
     * @param email
     * @param name
     * @param password 
     */
    public User(String email, String name, String password) {
        this.email = email;
    	this.name = name;
        this.password = password;
        this.author = true;
        this.editor = false;
        this.reviewer = true;
    }
    
    public void addAffiliation(Affiliation affiliation){
    	if(affiliationList == null){
    		ArrayList<Affiliation> affArray = new ArrayList<Affiliation>();
    		affArray.add(affiliation);
    		this.affiliationList =  affArray;
    	} else {
    		affiliationList.add(affiliation);
    	}
    }
    
}