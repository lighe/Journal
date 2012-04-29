package models;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class JournalConfiguration extends Model {
 
	//Defaults
    public String journalName = "Example Journal";
    public String urlToDocTemplate;
    public String urlToPDFTemplate;
    public String journalGoals = "Journal Goals to be defined";
    
	public JournalConfiguration(String journalName, String urlToDocTemplate, String urlToPDFTemplate, String journalGoals) {
		this.journalName = journalName;
	    this.urlToDocTemplate = urlToDocTemplate;
	    this.urlToPDFTemplate = urlToPDFTemplate;
	    this.journalGoals = journalGoals;
	 } 
 
}