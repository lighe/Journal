package models;

import java.util.Date;
 
import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;
 
@Entity
public class UserRole extends Model {

    @ManyToOne
    public User user_ID;
    
    @ManyToOne
    public Role role_ID;
   
    
    public UserRole(User user_ID, Role role_ID) {
        this.user_ID = user_ID;
        this.role_ID = role_ID;
    }
 
}