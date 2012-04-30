package models;

import java.util.Date;
 
import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;
 
@Entity
public class UserRole extends Model {

    @Column(name="user_ID",length=1000) 
    public User user_ID;
    
    @Column(name="role_ID",length=1000) 
    public Role role_ID;
   
    
    public UserRole(User user_ID, Role role_ID) {
        this.user_ID = user_ID;
        this.role_ID = role_ID;
    }
 
}