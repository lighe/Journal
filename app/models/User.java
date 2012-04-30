package models;

import java.util.Date;
 
import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;    

@Entity
public class User extends Model {

	@Column(name="email",length=30) 
    public String email;
	@Column(name="password",length=30) 
    public String password;
    public boolean author;
    public boolean editor;
    public boolean reviewer;
    
   //Empty constructor
    public User(){  
    	this.email = "";
    	this.password = "";
        this.author = true;
        this.editor = false;
        this.reviewer = true;  	
    }
    public User(String email, String password, boolean author, boolean editor, boolean reviewer) {
        this.email = email;
        this.password = password;
        this.author = author;
        this.editor = editor;
        this.reviewer = reviewer;
    }
    /**
     * Default constructor giving users author and reviewer privaleges
     * @param email
     * @param password 
     */
    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.author = true;
        this.editor = false;
        this.reviewer = true;
    }
 
 
}