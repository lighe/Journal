package models;

import java.util.Date;
 
import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;
 
@Entity
public class Notification extends Model {

    public int sender_ID;
    public int reciever_ID;
    public String message;
    
  /*  @ManyToOne    
    public Users user_ID;
    
    @ManyToOne    
    public Users user_ID;
    * change these so they are sender and reciever rspectively and change text bellow and above
    */        
    
    public Notification(int sender_ID, int reciever_ID, String message){
        this.sender_ID = sender_ID;
        this.reciever_ID = reciever_ID;
        this.message = message;
       
    }
 
}


