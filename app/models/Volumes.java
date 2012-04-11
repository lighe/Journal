package models;

import java.util.Date;
 
import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;
 
@Entity
public class Volumes extends Model {
 
    public int volume_ID;
    public Date name;   
    
    @ManyToOne
    public Published author;
    
    public Volumes(int volume_ID, Date name) {
        this.volume_ID = volume_ID;
        this.name = name;
    }
 
}