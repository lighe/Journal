package models;
import play.db.jpa.*;

public class ReaderComment extends Model {
    
    public String emailAddress;
    public String message;
    
    public ReaderComment (String emailAddress, String message){
        this.emailAddress = emailAddress;
        this.message= message;
    }
    
}