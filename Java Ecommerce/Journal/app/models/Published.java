package models;

import java.util.Date;
 
import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;
 
@Entity
public class Published extends Model {
	
	//will need authorised by
   
    @OneToOne
    public Revision revision;
    public User publish_by_editor;
   
    @ManyToOne
    public Edition edition;
    
    public Published(Revision revision, Edition edition, User editor){
        this.revision = revision;
	this.edition = edition;
        this.publish_by_editor = editor;
    }
   
    
    public Revision getRevision() {
	return Revision.findById(revision.id);		
    }
}