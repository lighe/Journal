package models;

import java.util.Date;
 
import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;
 
@Entity
public class User_Roles extends Model {

    @ManyToOne
    public Users user_ID;
    
    @ManyToOne
    public Roles role_ID;
   
    
    public User_Roles(Users user_ID, Roles role_ID) {
        this.user_ID = user_ID;
        this.role_ID = role_ID;
    }
 
}