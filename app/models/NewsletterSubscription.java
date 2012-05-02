package models;

import java.util.Date;
 
import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;    

@Entity
public class NewsletterSubscription extends Model {

    public String email;
    public String name;
    
    public NewsletterSubscription(String email, String name){  
    	this.email = email;
		this.name = name;
    } 
}