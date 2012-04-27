
package models;

import java.util.Date;
 
import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;
 
@Entity
public class Role extends Model {
 
   public String role; 
   
    public Role( String role) {
        this.role = role;
    }
 
}