package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import play.db.jpa.Model;

@Entity
public class Contributor extends Model  {

	String email; 
    @ManyToMany
	List<Affiliation> affiliationList;
	
	public Contributor(String email, ArrayList<Affiliation> affiliationList) {
		this.email = email;
		this.affiliationList = affiliationList;
	}

}
