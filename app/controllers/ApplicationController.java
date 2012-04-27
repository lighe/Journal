package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

@With(Secure.class)
public class ApplicationController extends Controller {
	
	private static Edition selectedEdition; //this is populated in getBrowseData()
           
	@Before
        static void setConnectedUser() {
            Security.setConnectedUser();
        }
	
	@Before
	static void getBrowseData() {
		BrowseController.getBrowseData();
		selectedEdition = BrowseController.selectedEdition;
	}
	
   public static void index() {
        List<Volume> volumes = Volume.find(
            "order by ID desc"
        ).fetch(5); //this repeats db values if not enough entries in db. Solutions?
		String user = Security.connected();
		Volume selectedVolume = volumes.get(0);
		List<Edition> editions = selectedVolume.getEditions();
		Edition selectedEdition = editions.get(0);
		List<Published> publishedArticles = selectedEdition.getPublished();
        render(volumes, editions, publishedArticles);
    }
    
   
      
}
