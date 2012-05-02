package models;

import play.mvc.*; 
import java.util.*;
import javax.persistence.*;
import play.db.jpa.*;

@Entity
public class JournalConfiguration extends Model {
 
	//Defaults
    public String journalName = "Example Journal";
    public String urlToLatexTemplate;
    public String urlToDocTemplate;
	@Lob
    public String journalGoals = "Journal Goals to be defined";
	@Lob
	public String shortDescription;
	@Lob
	public String guidelines;
    
	public JournalConfiguration(String journalName, String urlToLatexTemplate, String urlToDocTemplate, String journalGoals, String shortDescription, String guidelines) {
		this.journalName = journalName;
	    this.urlToLatexTemplate = urlToLatexTemplate;
	    this.urlToDocTemplate = urlToDocTemplate;
	    this.journalGoals = journalGoals;
		this.shortDescription = shortDescription;
		this.guidelines = guidelines;
	} 
 
}