package models;

import java.util.Date;
 
import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;    

@Entity
public class Users extends Model {
 
    public String email;
    public String password;
   
    
    public Users(int user_ID, String email, String password) {
        this.email = email;
        this.password = password;
    }
 
}