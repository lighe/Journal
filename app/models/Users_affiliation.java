package models;

import java.util.Date;
 
import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;
 
@Entity
public class Users_affiliation extends Model {

         
  @ManyToOne
  public Users user_ID;
  
  @ManyToOne
  public Affiliation affiliation_ID;
    
  public Users_affiliation(Affiliation affiliation_ID, Users user_ID) {
        this.affiliation_ID = affiliation_ID;
        this.user_ID = user_ID;
    }
 
}