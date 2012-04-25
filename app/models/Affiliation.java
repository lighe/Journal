package models;

import java.util.Date;
 
import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;
 
@Entity
public class Affiliation extends Model {
    
    public String affiliation_name; 
    
    public Affiliation(String affiliation_name) {
        this.affiliation_name = affiliation_name;
    }
 
}