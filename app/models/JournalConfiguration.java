package models;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class JournalConfiguration extends Model {
 
	//Defaults
    public String journalName = "Example Journal";
    public String urlToLatexTemplate;
    public String urlToPDFTemplate;
    public String journalGoals = "Journal Goals to be defined";
    
	public JournalConfiguration(String journalName, String urlToLatexTemplate, String urlToPDFTemplate, String journalGoals) {
		this.journalName = journalName;
	    this.urlToLatexTemplate = urlToLatexTemplate;
	    this.urlToPDFTemplate = urlToPDFTemplate;
	    this.journalGoals = journalGoals;
	 } 
 
}