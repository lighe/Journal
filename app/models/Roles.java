
package models;

import java.util.Date;
 
import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;
 
@Entity
public class Roles extends Model {
 
   public String role; 
   
    public Roles( String role) {
        this.role = role;
    }
 
}