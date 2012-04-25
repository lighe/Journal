package models;

import java.util.Date;
 
import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;
 
@Entity
public class Volumes extends Model {
 
    public Date name;   
    
    @ManyToOne
    public Published author;
    
    public Volumes( Date name) {
       this.name = name;
    }
 
}