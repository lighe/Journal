
package models;

import java.util.Date;
 
import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;
 
@Entity
public class Roles extends Model {
 
    public int role_ID;
    public String role; 
   
    public Roles(int role_ID, String role) {
        this.role_ID = role_ID;
        this.role = role;
    }
 
}