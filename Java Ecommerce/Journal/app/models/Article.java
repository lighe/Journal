package models;

import java.util.Date;
 
import java.util.*;
import java.util.List;
import javax.persistence.*;
 
import play.db.jpa.*;
 
@Entity
public class Article extends Model {

    public String title;
    public boolean published;
    
    @Lob
    public String summary;
    
    public User user;
    //public List<User> contributors;
    
    @ManyToMany(mappedBy="articles") 
    public List<Tag> tags;
 
    public Article (User user, String title, List<Tag> tags) {
        this(user, false , title, "", tags);
    }
            
    public Article(User user, boolean published, String title, String summary, List<Tag> tags){
        this.user = user;
        this.published = published;
        this.title = title;
        this.summary = summary;
	this.tags = tags;
    }
	
	public String getShortSummary() {
		int length = 150;
		if (summary != null && summary.length() > length) {
	  		return summary.substring(0, length).trim() + "...";
		}
 		return summary;
	}
        /*
        //Untested
        public void addContributor(User contributor){
            this.contributors.add(contributor);
        }
        //Untested
        public void removeContributor(User contributor){
            this.contributors.remove(contributor);
        }
        //Untested
        public List<User> getContributor(){
            return contributors;
        }
        */
         public Revision getLatestRevision(Article article){
		List<Revision> revisions = Revision.find("article_ID", article).fetch();

		int highestRevision = 0;
		for(int x = 0; x < revisions.size(); x++){
			Revision revision = revisions.get(x);
			if(revision.revision_number > highestRevision){
				highestRevision = revision.revision_number;
			}
		}
		Revision latestRev = Revision.find("revision_number", highestRevision).first();
    	return latestRev;
    }
}


