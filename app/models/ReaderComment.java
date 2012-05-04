package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import play.db.jpa.Model;
 

@Entity
public class ReaderComment extends Model {
    
    public String emailAddress;
    public String message;
    
    public ReaderComment (String emailAddress, String message){
        this.emailAddress = emailAddress;
        this.message= message;
    }
    
}