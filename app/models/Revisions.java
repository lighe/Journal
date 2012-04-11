package models;

import java.util.Date;
 
import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;
 
@Entity
public class Revisions extends Model {

    public int revision_ID;
    public Date date;
    public String article_abstract;
    public String revision_number;
    public String url; 
    
    @ManyToOne
    public Articles article_ID;
            
    public Revisions(int revision_ID, Articles article_ID, Date date,  String article_abstract, String revision_number, String url) {
        this.revision_ID = revision_ID;
        this.article_ID = article_ID;
        this.date = date;
        this.article_abstract =article_abstract;
        this.revision_number = revision_number;
        this.url = url;
    }
 
}