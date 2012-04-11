package models;

import java.util.Date;
 
import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;
 
@Entity
public class Affiliation extends Model {
    
    public int affiliation_ID;
    public String affiliation_name; 
    
    public Affiliation(int affiliation_ID, String affiliation_name) {
        this.affiliation_ID = affiliation_ID;
        this.affiliation_name = affiliation_name;
    }
 
}