package models;

import java.util.Date;
 
import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;    

@Entity
public class User extends Model {
 
    public String email;
    public String password;
    public boolean author;
    public boolean editor;
    public boolean reviewer;
    
   
    
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