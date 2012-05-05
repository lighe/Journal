package models;

import java.util.Date;
 
import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;

//@ElasticSearchable 
@Entity
public class Article extends Model {

    public String title;
    public boolean published;
    
    @Lob
    public String summary;

	@ManyToOne
    //@Column(name="user",length=1000) 
    public User user;

    //String as some may not have user accounts
    @OneToMany
    public List<Affiliation> affiliations;
    
    //String as some may not have user accounts
    @Column(name="contributors",length=1000) 
    public ArrayList<Contributor> contributors;

    //@Column(name="tags",length=1000) 
    @ManyToMany
	public List<Tag> tags;
	//public ArrayList<Tag> tags;
 
    public Article (User user, String title, String summary) {
        this(user, false , title, summary);
    }
            
    public Article(User user, boolean published, String title, String summary){
        this.user = user;
        this.published = published;
        this.title = title;
        this.summary = summary;
    }
    
    public void addTags(List<Tag> tags ){ //ArrayList<Tag> tags){
		System.out.println(tags);
    	this.tags = tags;
    }
    
    public void addContributors(ArrayList<Contributor> contributors){
    	this.contributors = contributors;
    }
    public void addAffiliations(ArrayList<Affiliation> affiliations){
    	this.affiliations = affiliations;
    }
	
	public String getShortSummary() {
		int length = 150;
		if (summary != null && summary.length() > length) {
	  		return summary.substring(0, length).trim() + "...";
		}
 		return summary;
	}
    
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


