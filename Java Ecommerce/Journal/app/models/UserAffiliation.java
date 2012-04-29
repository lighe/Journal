package models;

import java.util.Date;
 
import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;
 
@Entity
public class UserAffiliation extends Model {

         
  @ManyToOne
  public User user_ID;
  
  @ManyToOne
  public Affiliation affiliation_ID;
    
  public UserAffiliation(Affiliation affiliation_ID, User user_ID) {
        this.affiliation_ID = affiliation_ID;
        this.user_ID = user_ID;
    }
 
}