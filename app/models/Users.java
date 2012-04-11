package models;

import java.util.Date;
 
import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;    

@Entity
public class Users extends Model {
 
    public int user_ID;
    public String email;
    public String password;
   
    
    public Users(int user_ID, String email, String password) {
        this.user_ID = user_ID;
        this.email = email;
        this.password = password;
    }
 
}