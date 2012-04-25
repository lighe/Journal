package models;

import java.util.Date;
 
import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;
 
@Entity
public class Revisions extends Model {

    public Date date;
    public String article_abstract;
    public int revision_number;
    public String url; 
    
    @ManyToOne
    public Articles article_ID;
            
    public Revisions(Articles article_ID, Date date, String article_abstract, int revision_number, String url) {
        this.article_ID = article_ID;
        this.date = date;
        this.article_abstract =article_abstract;
        this.revision_number = revision_number;
        this.url = url;
    }
 
}